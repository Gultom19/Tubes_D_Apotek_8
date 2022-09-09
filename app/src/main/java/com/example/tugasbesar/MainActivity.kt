package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var usernameView: TextInputEditText
    private lateinit var passwordView: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var btnSignUp : TextView
    lateinit var mBundle: Bundle
    lateinit var vUsername: String
    lateinit var vPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide()
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        mainLayout = findViewById(R.id.mainLayout)
//        getBundle()
//        setText()
        btnSignUp = findViewById(R.id.textBtnSignUp)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnSignUp.setOnClickListener {
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

            if (username == vUsername && password == vPassword) checkLogin = true
            if (!checkLogin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })
    }

    fun getBundle(){
        mBundle = intent.getBundleExtra("register")!!
        vUsername = mBundle.getString("username")!!
        vPassword = mBundle.getString("password")!!
    }

    fun setText(){
        usernameView = findViewById(R.id.etUsername)
        usernameView.setText(vUsername,TextView.BufferType.EDITABLE)
        passwordView = findViewById(R.id.etPassword)
        passwordView.setText(vPassword,TextView.BufferType.EDITABLE)
    }
}