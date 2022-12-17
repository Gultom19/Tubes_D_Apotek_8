package com.example.tugasbesar

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.*
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
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
    private var etTanggal: EditText? = null
//    private var etTelepon: EditText? = null

    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    private val calender = Calendar.getInstance()
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
        etTanggal = binding.etTanggalLahir
        etTanggal!!.setFocusable(false)
        etTanggal!!.setOnClickListener(View.OnClickListener{
            DatePickerDialog(this, this, calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show()
        })
//        etTelepon = binding.etTelepon
        layoutLoading = findViewById(R.id.layout_loading)
        btnRegister = binding.btnRegister

        queue = Volley.newRequestQueue(this)

        createNotificationChannel()
        btnRegister.setOnClickListener(View.OnClickListener{
            var checkRegister = false

            if (username.getEditText()?.getText().toString().isEmpty()) {
                username.setError("Username must be filled with text")
            }else{
                username.setError(null)
            }

            if (password.getEditText()?.getText().toString().isEmpty()) {
                password.setError("Password must be filled with text")
            }else{
                password.setError(null)
            }

            val inputRepeatPassword = repeatPassword.getEditText()?.getText().toString()
            val inputPassword = password.getEditText()?.getText().toString()
            if (repeatPassword.getEditText()?.getText().toString().isEmpty()) {
                repeatPassword.setError("Repeat Password must be filled with text")
            }else if(inputRepeatPassword != inputPassword) {
                repeatPassword.setError("Password do not match")
            }else{
                repeatPassword.setError(null)
            }

            val inputEmail = email.getEditText()?.getText().toString()
            if (email.getEditText()?.getText().toString().isEmpty()) {
                email.setError("Email must be filled with text")
            }else if(!isValidEmail(inputEmail)){
                email.setError("Format Email Invalid")
            }else{
                email.setError(null)
            }

            if (etTanggal!!.getText().isEmpty()) {
                tanggal.setError("Tanggal Lahir must be filled with text")
            }else{
                tanggal.setError(null)
            }

            if (telepon.getEditText()?.getText().toString().isEmpty()) {
                telepon.setError("Telepon must be filled with text")
            }else{
                telepon.setError(null)
            }

            if(username.getError() == null && password.getError() == null && repeatPassword.getError() == null && email.getError() == null && tanggal.getError() == null && telepon.getError() == null) checkRegister = true
            if (!checkRegister) return@OnClickListener
            else{


                register()

            }

        })

        textBtnSignIn.setOnClickListener {
            val moveLogin = Intent(this, MainActivity::class.java)
            startActivity(moveLogin)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.e("Calender","$year -- $month -- $dayOfMonth")
        calender.set(year, month, dayOfMonth)
        displayFormattedDate(calender.timeInMillis)
    }

    private fun displayFormattedDate(timestamp: Long){
        findViewById<TextInputEditText>(R.id.etTanggalLahir).setText(formatter.format(timestamp))
        Log.i("Formatting",timestamp.toString())
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

//                val returnIntent = Intent()
//                setResult(RESULT_OK, returnIntent)
//                finish()
                val moveMain = Intent(this@RegisterActivity, MainActivity::class.java)
                val mBundle = Bundle()
                mBundle.putString("username", username.getEditText()?.getText().toString())
                mBundle.putString("password", password.getEditText()?.getText().toString())
                moveMain.putExtra("register", mBundle)
                sendNotification()
                startActivity(moveMain)
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
//                 email.setError("Email must unique")
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

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}