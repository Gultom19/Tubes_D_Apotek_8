package com.example.tugasbesar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.tugasbesar.admin.AdminActivity
import com.example.tugasbesar.api.UserApi
import com.example.tugasbesar.databinding.ActivityMainBinding
import com.example.tugasbesar.home.HomeActivity
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
    private val myPreference = "myPref"
    lateinit var mbundle : Bundle
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


            if (inputUsername.getEditText()?.getText().toString().isEmpty()) {
                inputUsername.setError("Username must be filled with text")
            }else{
                inputUsername.setError(null)
            }

            if (inputPassword.getEditText()?.getText().toString().isEmpty()) {
                inputPassword.setError("Password must be filled with text")
            }else{
                inputPassword.setError(null)
            }
            if(inputUsername.getError() == null && inputPassword.getError() == null) checkLogin = true
            if(inputUsername.getEditText()?.getText().toString() == "admin" && inputPassword.getEditText()?.getText().toString() == "admin"){
                val moveAdmin = Intent(this@MainActivity, AdminActivity::class.java)
                startActivity(moveAdmin)
            }else if(!checkLogin) {
                return@OnClickListener
            }else{
                login()
            }

//            if (!checkLogin) return@OnClickListener
//            else{
//
//            }
        })
    }

    private fun login() {
        setLoading(true)
        val user = Auth(
            inputUsername.getEditText()?.getText().toString(),
            inputPassword.getEditText()?.getText().toString()
        )
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, UserApi.LOGIN, Response.Listener { response ->
                val gson = Gson()
                var user = gson.fromJson(response, User::class.java)

                if(user != null)
                    Toast.makeText(this@MainActivity, "Berhasil Login", Toast.LENGTH_SHORT).show()
//                val sharedPreference =  getSharedPreferences(myPreference, Context.MODE_PRIVATE)
//                var editor = sharedPreference.edit()
//                editor.putString("username",user.username)
//                editor.commit()
                val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
                val mBundle = Bundle()
                mBundle.putString("username", inputUsername.getEditText()?.getText().toString())
                moveHome.putExtra("key", mBundle)
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
//                    inputUsername.setError("Username or Password invalid")
//                    inputPassword.setError("Username or Password invalid")
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
            mbundle = intent?.getBundleExtra("register")!!
            if(mbundle != null){
                vUsername = mbundle.getString("username")!!
                vPassword = mbundle.getString("password")!!
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