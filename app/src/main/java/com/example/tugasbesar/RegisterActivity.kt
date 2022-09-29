package com.example.tugasbesar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tugasbesar.databinding.ActivityRegisterBinding
import com.example.tugasbesar.room.User
import com.example.tugasbesar.room.UserDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {
    private lateinit var username: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var tanggal: TextInputLayout
    private lateinit var telepon: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var binding: ActivityRegisterBinding

    private val CHANNEL_ID = "channel_notification"
    private val notificationId = 101


    val db by lazy { UserDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        setTitle("Register")
        username = binding.inputLayoutRegisUsername
        password = binding.inputLayoutRegisPassword
        email = binding.inputLayoutEmail
        tanggal = binding.inputLayoutTanggalLahir
        telepon = binding.inputLayoutTelepon
        btnRegister = binding.btnRegister

        createNotificationChannel()
        btnRegister.setOnClickListener{
            val moveMain = Intent(this@RegisterActivity, MainActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("username", username.getEditText()?.getText().toString())
            mBundle.putString("password", password.getEditText()?.getText().toString())
            moveMain.putExtra("register", mBundle)

            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                    User(
                        0, username.getEditText()?.getText().toString(),
                        password.getEditText()?.getText().toString(),
                        email.getEditText()?.getText().toString(),
                        tanggal.getEditText()?.getText().toString(),
                        telepon.getEditText()?.getText().toString(),
                    )
                )
                finish()
            }
            sendNotification()
            startActivity(moveMain)
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", binding?.etRegisUsername?.text.toString())
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_app_registration_24)
            .setContentTitle("Register")
            .setContentText("Anda berhasil register !!")
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources,R.drawable.big_picture_notification))
            )
        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }
}