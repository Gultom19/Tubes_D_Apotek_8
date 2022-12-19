package com.example.tugasbesar.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesar.CategoryObatActivity
import com.example.tugasbesar.R
import com.example.tugasbesar.adapters.ObatAdapter
import com.example.tugasbesar.api.ObatApi
import com.example.tugasbesar.models.Obat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        getSupportActionBar()?.hide()

        val btnCategoryObat: Button = findViewById<Button>(R.id.btnAdminObat)
        btnCategoryObat.setOnClickListener(){
            val toAdminObat = Intent(this@AdminActivity, AdminKontrasepsiActivity::class.java)
            startActivity(toAdminObat)
        }

        val btnCategoryKontrasepsi: Button = findViewById<Button>(R.id.btnAdminKontrasepsi)
        btnCategoryKontrasepsi.setOnClickListener(){
            val toAdminKontrasepsi = Intent(this@AdminActivity, AdminKontrasepsiActivity::class.java)
            startActivity(toAdminKontrasepsi)
        }
    }
}