package com.example.tugasbesar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesar.api.UserApi
import com.example.tugasbesar.databinding.ActivityMainBinding
import com.example.tugasbesar.models.Auth
import com.example.tugasbesar.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.bouncycastle.cms.RecipientId.password
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    lateinit var mbunlde : Bundle
    lateinit var vUsername : String
    lateinit var vPassword : String
//    lateinit var checkUsername : String
//    lateinit var checkPassword : String

    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide();
        setTitle("User Login")

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        val textBtnSignUp : TextView = findViewById(R.id.textBtnSignUp)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        getBundle()
        setText()

        textBtnSignUp.setOnClickListener {
            val moveRegister = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                inputUsername.setError("Username must be filled with text")
                checkLogin = false
            }

            if (password.isEmpty()) {
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

//            runBlocking(){
//                val usernameDb = async {
//                    val Account: User? = db.userDao().getAccount(username, password)
//                    if (Account != null) {
//                        Account.username
//                    } else {
//                        null
//                    }
//                }
//                val passwordDb = async {
//                    val Account: User? = db.userDao().getAccount(username, password)
//                    Log.d("MainActivity","dbResponse: $Account")
//                    if (Account != null) {
//                        Account.password
//                    } else {
//                        null
//                    }
//                }
//                checkUsername = usernameDb.await().toString()
//                checkPassword = passwordDb.await().toString()
//            }

//            if (username == checkUsername && password == checkPassword) checkLogin = true
//
//            if(username != checkUsername) {
//                inputUsername.setError("The username you entered is incorrect")
//                checkLogin = false
//            }
//            if(password != checkPassword){
//                inputPassword.setError("The password you entered is incorrect")
//                checkLogin = false
//            }
            login()
//            if (!checkLogin) return@OnClickListener
//            else{
//
//            }
        })
    }

    private fun login() {
        setLoading(true)
        val user = Auth(
            username = inputUsername.getEditText()?.getText().toString(),
            password = inputPassword.getEditText()?.getText().toString()
        )
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, UserApi.LOGIN, Response.Listener { response ->
                val gson = Gson()
                var user = gson.fromJson(response, User::class.java)

                if(user != null)
                    Toast.makeText(this@MainActivity, "Berhasil Login", Toast.LENGTH_SHORT).show()
                val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(moveHome)
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@MainActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
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

    fun getBundle(){
        try{
            mbunlde = intent?.getBundleExtra("register")!!
            if(mbunlde != null){
                vUsername = mbunlde.getString("username")!!
                vPassword = mbunlde.getString("password")!!
            }else{

            }
        }catch (e: NullPointerException){
            vUsername = ""
            vPassword = ""
        }

    }

    fun setText() {
        etUsername = findViewById(R.id.etUsername)
        etUsername.setText(vUsername, TextView.BufferType.EDITABLE)
        etPassword = findViewById(R.id.etPassword)
        etPassword.setText(vPassword, TextView.BufferType.EDITABLE)
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
            layoutLoading!!.visibility = View.GONE
        }
    }
}