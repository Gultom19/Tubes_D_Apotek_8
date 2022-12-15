package com.example.tugasbesar.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesar.R
import com.example.tugasbesar.api.ObatApi
import com.example.tugasbesar.models.Obat
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

// Kelas AddEditActivity
class AddEditActivity : AppCompatActivity() {
    private var etNama: EditText? = null
    private var etJenis: EditText? = null
    private var etHarga: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this)
        etNama = findViewById(R.id.et_nama)
        etJenis = findViewById(R.id.et_jenis)
        etHarga = findViewById(R.id.et_harga)
        layoutLoading = findViewById(R.id.layout_loading)

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getLongExtra("id", -1)
        if (id == -1L) {
            tvTitle.setText("Tambah Obat")
            btnSave.setOnClickListener { createObat() }
        } else {
            tvTitle.setText("Edit Obat")
            getObatById(id)
            btnSave.setOnClickListener { updateObat(id) }
        }
    }

    private fun getObatById(id: Long) {
        // Fungsi untuk menampilkan data obat berdasarkan id
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, ObatApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val obat = gson.fromJson(response, Obat::class.java)

                etNama!!.setText(obat.obat)
                etJenis!!.setText(obat.jenis)
                etHarga!!.setText(obat.harga)

                Toast.makeText(this@AddEditActivity, "Data Berhasil diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    private fun createObat() {
        // Fungsi untuk menambah data obat.
        setLoading(true)

        if(etNama!!.text.toString().isEmpty()) {
            Toast.makeText(this@AddEditActivity, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etJenis!!.text.toString().isEmpty()) {
            Toast.makeText(this@AddEditActivity, "Jenis tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etHarga!!.text.toString().isEmpty()) {
            Toast.makeText(this@AddEditActivity, "Harga tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }else{
            val obat = Obat(
                etNama!!.text.toString(),
                etJenis!!.text.toString(),
                etHarga!!.text.toString()
            )
            val stringRequest: StringRequest =
                object : StringRequest(Method.POST, ObatApi.ADD_URL, Response.Listener { response ->
                    val gson = Gson()
                    var obat = gson.fromJson(response, Obat::class.java)

                    if(obat != null)
                        Toast.makeText(this@AddEditActivity, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()

                    setLoading(false)
                }, Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@AddEditActivity,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
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
                        val requestBody = gson.toJson(obat)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }

            // Menambahkan request ke request queue
            queue!!.add(stringRequest)
        }
        setLoading(false)
    }

    private fun updateObat(id: Long) {
        // Fungsi untuk mengubah data obat
        setLoading(true)

        val obat = Obat(
            etNama!!.text.toString(),
            etJenis!!.text.toString(),
            etHarga!!.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, ObatApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                var obat = gson.fromJson(response, Obat::class.java)

                if(obat != null)
                    Toast.makeText(this@AddEditActivity, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
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
                val requestBody = gson.toJson(obat)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }

    // Fungsi ini digunakan menampilkan layout loading
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
}
