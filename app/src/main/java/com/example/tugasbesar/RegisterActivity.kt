package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
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
        setTitle("Register")
        username = findViewById(R.id.inputLayoutRegisUsername)
        password = findViewById(R.id.inputLayoutRegisPassword)
        email = findViewById(R.id.inputLayoutEmail)
        tanggal = findViewById(R.id.inputLayoutTanggalLahir)
        telepon = findViewById(R.id.inputLayoutTelepon)
        btnRegister =findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener{
            val moveMain = Intent(this@RegisterActivity, MainActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("username", username.getEditText()?.getText().toString())
            mBundle.putString("password", password.getEditText()?.getText().toString())
            moveMain.putExtra("register", mBundle)

            startActivity(moveMain)
        }
    }
}