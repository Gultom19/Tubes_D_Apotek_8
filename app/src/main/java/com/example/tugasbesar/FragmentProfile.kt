package com.example.tugasbesar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FragmentProfile : AppCompatActivity() {
    private lateinit var username: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var telepon: TextInputLayout
    private lateinit var tanggalLahir: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_profile)
    }
}