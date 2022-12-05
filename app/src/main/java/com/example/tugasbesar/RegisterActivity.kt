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
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isEmpty
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesar.api.UserApi
import com.example.tugasbesar.databinding.ActivityRegisterBinding
import com.example.tugasbesar.models.User
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class RegisterActivity : AppCompatActivity() {
    private lateinit var username: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var repeatPassword: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var tanggal: TextInputLayout
    private lateinit var telepon: TextInputLayout

//    private var etUsername: EditText? = null
//    private var etPassword: EditText? = null
//    private var etRepeatPassword: EditText? = null
//    private var etEmail: EditText? = null
//    private var etTanggal: EditText? = null
//    private var etTelepon: EditText? = null

    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    private lateinit var btnRegister: Button
    private lateinit var binding: ActivityRegisterBinding

    private val CHANNEL_ID = "channel_notification"
    private val notificationId = 101


//    val db by lazy { UserDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setTitle("Register")
        username = binding.inputLayoutRegisUsername
        password = binding.inputLayoutRegisPassword
        repeatPassword = binding.inputLayoutRegisRepeatPassword
        email = binding.inputLayoutEmail
        tanggal = binding.inputLayoutTanggalLahir
        telepon = binding.inputLayoutTelepon

//        etUsername = binding.etRegisUsername
//        etPassword = binding.etRegisPassword
//        etRepeatPassword = binding.etRegisRepeatPassword
//        etEmail = binding.etEmail
//        etTanggal = binding.etTanggalLahir
//        etTelepon = binding.etTelepon
        layoutLoading = findViewById(R.id.layout_loading)
        btnRegister = binding.btnRegister

        queue = Volley.newRequestQueue(this)

        createNotificationChannel()
        btnRegister.setOnClickListener(View.OnClickListener{
            var checkRegister = false

            if (username.isEmpty()) {
                username.setError("Username must be filled with text")
                checkRegister = false
            }
            if (password.isEmpty()) {
                password.setError("Password must be filled with text")
                checkRegister = false
            }
            if (repeatPassword.isEmpty()) {
                repeatPassword.setError("Password must be filled with text")
                checkRegister = false
            }
            if (email.isEmpty()) {
                email.setError("Password must be filled with text")
                checkRegister = false
            }
            if (tanggal.isEmpty()) {
                tanggal.setError("Password must be filled with text")
                checkRegister = false
            }
            if (telepon.isEmpty()) {
                telepon.setError("Password must be filled with text")
                checkRegister = false
            }

//            if (password == repeatPassword) checkRegister = true
//
//            if (!checkRegister) return@OnClickListener
//            else{
//                val moveMain = Intent(this@RegisterActivity, MainActivity::class.java)
//                val mBundle = Bundle()
//                mBundle.putString("username", username.getEditText()?.getText().toString())
//                mBundle.putString("password", password.getEditText()?.getText().toString())
//                moveMain.putExtra("register", mBundle)

//                CoroutineScope(Dispatchers.IO).launch {
//                    db.userDao().addUser(
//                        User(
//                            0, username.getEditText()?.getText().toString(),
//                            password.getEditText()?.getText().toString(),
//                            email.getEditText()?.getText().toString(),
//                            tanggal.getEditText()?.getText().toString(),
//                            telepon.getEditText()?.getText().toString()
//                        )
//                    )
//                    finish()
//                }
                register()
                sendNotification()
//                startActivity(moveMain)
//            }

        })

        textBtnSignIn.setOnClickListener {
            val moveLogin = Intent(this, MainActivity::class.java)
            startActivity(moveLogin)
        }
    }

    private fun register() {
        setLoading(true)
        val user = User(
            username.getEditText()?.getText().toString(),
            password.getEditText()?.getText().toString(),
            email.getEditText()?.getText().toString(),
            tanggal.getEditText()?.getText().toString(),
            telepon.getEditText()?.getText().toString()
        )
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, UserApi.REGISTER, Response.Listener { response ->
                val gson = Gson()
                var user = gson.fromJson(response, User::class.java)

                if(user != null)
                    Toast.makeText(this@RegisterActivity, "Berhasil Register", Toast.LENGTH_SHORT).show()
//                if(user.username == null)
//                    Toast.makeText(this@RegisterActivity, "username null", Toast.LENGTH_SHORT).show()
//                if(user.password == null)
//                    Toast.makeText(this@RegisterActivity, "password null", Toast.LENGTH_SHORT).show()
//                if(user.email == null)
//                    Toast.makeText(this@RegisterActivity, "email null", Toast.LENGTH_SHORT).show()
//                if(user.tgglLahir == null)
//                    Toast.makeText(this@RegisterActivity, "tgglLahir null", Toast.LENGTH_SHORT).show()
//                if(user.telepon == null)
//                    Toast.makeText(this@RegisterActivity, "telepon null", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@RegisterActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(user)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        // Menambahkan request ke request queue
        queue!!.add(stringRequest)
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

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}