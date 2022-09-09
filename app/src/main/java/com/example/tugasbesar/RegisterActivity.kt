package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private lateinit var username: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var tanggal: TextInputLayout
    private lateinit var telepon: TextInputLayout
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.inputLayoutUsername)
        password = findViewById(R.id.inputLayoutPassword)
        email = findViewById(R.id.inputLayoutEmail)
        tanggal = findViewById(R.id.inputLayoutTanggalLahir)
        telepon = findViewById(R.id.inputLayoutTelepon)

        btnRegister.setOnClickListener{
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("username", username.toString())
            mBundle.putString("password", password.toString())
            mBundle.putString("email", email.toString())
            mBundle.putString("tanggal", tanggal.toString())
            mBundle.putString("telepon", telepon.toString())
            intent.putExtra("register", mBundle)

            startActivity(intent)
        }
    }
}