package com.example.tugasbesar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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
            startActivity(moveMain)
        }
    }
}