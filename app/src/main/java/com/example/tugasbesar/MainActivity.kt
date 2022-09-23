package com.example.tugasbesar

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tugasbesar.room.User
import com.example.tugasbesar.room.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException


class MainActivity : AppCompatActivity() {
//    val db by lazy { UserDB(this) }
//    private var userId: Int = 0

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    lateinit var mbunlde : Bundle
    lateinit var vUsername : String
    lateinit var vPassword : String
//    lateinit var checkUsername : String
//    lateinit var checkPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide();
        setTitle("User Login")

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        val textBtnSignIn : TextView = findViewById(R.id.textBtnSignUp)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        getBundle()
        setText()

        textBtnSignIn.setOnClickListener {
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

//            CoroutineScope(Dispatchers.IO).launch {
//                checkUsername = db.userDao().getUsername(username)
//                checkPassword = db.userDao().getPassword(password)
//                userId = db.userDao().getUserId(username)
//            }

            if (username == vUsername && password == vPassword) checkLogin = true
            if (!checkLogin) return@OnClickListener
            else{
                val moveHome = Intent(applicationContext, HomeActivity::class.java)
                startActivity(moveHome)
            }

        })
    }

//    override fun onStart() {
//        super.onStart()
//        loadData()
//    }
//
//    fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = db.userDao().getUser()
//            Log.d("MainActivity", "dbResponse: $user")
//        }
//    }

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

    fun setText(){
        etUsername = findViewById(R.id.etUsername)
        etUsername.setText(vUsername,TextView.BufferType.EDITABLE)
        etPassword = findViewById(R.id.etPassword)
        etPassword.setText(vPassword,TextView.BufferType.EDITABLE)
    }
}