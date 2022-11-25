package com.example.tugasbesar

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
import com.google.gson.Gson
import com.example.tugasbesar.api.ObatApi
import com.example.tugasbesar.models.Obat
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditActivity : AppCompatActivity() {
//    companion object {
//        private val Obat_LIST =
//            arrayOf("Obat Pusing", "Obat Batuk", "Obat Panas", "Obat Maag", "Obat Masuk Angin")
//        private val Jenis_LIST = arrayOf(
//            "Paramex", "Bodrex", "Panadol", "Promag", "Tolak Angin"
//        )
//    }
//
//    private var etJenis: EditText? = null
//    private var etObat: EditText? = null
//    private var edJumlah: AutoCompleteTextView? = null
//    private var edPembelian: AutoCompleteTextView? = null
//    private var layoutLoading: LinearLayout? = null
//    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

//        // Pendeklarasian request queue
//        queue = Volley.newRequestQueue(this)
//        etJenis = findViewById(R.id.et_jenis)
//        etObat = findViewById(R.id.et_obat)
//        edJumlah = findViewById(R.id.ed_jumlah)
//        edPembelian = findViewById(R.id.ed_pembelian)
//        layoutLoading = findViewById(R.id.layout_loading)
//
//        setExposedDropDownMenu()
//
//        val btnCancel = findViewById<Button>(R.id.btn_cancel)
//        btnCancel.setOnClickListener { finish() }
//        val btnSave = findViewById<Button>(R.id.btn_save)
//        val tvTitle = findViewById<TextView>(R.id.tv_title)
//        val id = intent.getLongExtra("id", -1)
//        if (id == -1L) {
//            tvTitle.setText("Tambah Mahasiswa")
//            btnSave.setOnClickListener { createMahasiswa() }
//        } else {
//            tvTitle.setText("Edit Mahasiswa")
//            getMahasiswaById(id)
//            btnSave.setOnClickListener { updateMahasiswa(id) }
//        }
    }
//
//    fun setExposedDropDownMenu() {
//        val adapterFakultas: ArrayAdapter<String> = ArrayAdapter<String>(
//            this,
//            R.layout.item_list, Obat_LIST
//        )
//        edJumlah!!.setAdapter(adapterFakultas)
//
//        val adapterProdi: ArrayAdapter<String> = ArrayAdapter<String>(
//            this,
//            R.layout.item_list, Jenis_LIST
//        )
//        edPembelian!!.setAdapter(adapterProdi)
//    }
//
//    private fun getMahasiswaById(id: Long) {
//
//        setLoading(true)
//        val stringRequest: StringRequest = object :
//            StringRequest(Method.GET, ObatApi.GET_BY_ID_URL + id, Response.Listener { response ->
//                val gson = Gson()
//                val mahasiswa = gson.fromJson(response, Obat::class.java)
//
//                etJenis!!.setText(obat.jenis)
//                etObat!!.setText(obat.obat)
//                edPembelian!!.setText(obat.pemblian)
//                edProdi!!.setText(obat.prodi)
//                setExposedDropDownMenu()
//
//                Toast.makeText(this@AddEditActivity, "Data Berhasil diambil!", Toast.LENGTH_SHORT)
//                    .show()
//                setLoading(false)
//            }, Response.ErrorListener { error ->
//                setLoading(false)
//                try {
//                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
//                    val errors = JSONObject(responseBody)
//                    Toast.makeText(
//                        this@AddEditActivity,
//                        errors.getString("message"),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } catch (e: Exception) {
//                    Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
//                }
//            }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
//        }
//        queue!!.add(stringRequest)
//    }
//
//    private fun createMahasiswa() {
//        // Fungsi untuk menambah data mahasiswa.
//        setLoading(true)
//
//        val mahasiswa = Mahasiswa(
//            etNama!!.text.toString(),
//            etNPM!!.text.toString(),
//            edFakultas!!.text.toString(),
//            edProdi!!.text.toString()
//        )
//        val stringRequest: StringRequest =
//            object :
//                StringRequest(Method.POST, MahasiswaApi.ADD_URL, Response.Listener { response ->
//                    val gson = Gson()
//                    var mahasiswa = gson.fromJson(response, Mahasiswa::class.java)
//
//                    if (mahasiswa != null)
//                        Toast.makeText(
//                            this@AddEditActivity,
//                            "Data Berhasil Ditambahkan",
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                    val returnIntent = Intent()
//                    setResult(RESULT_OK, returnIntent)
//                    finish()
//
//                    setLoading(false)
//                }, Response.ErrorListener { error ->
//                    setLoading(false)
//                    try {
//                        val responseBody =
//                            String(error.networkResponse.data, StandardCharsets.UTF_8)
//                        val errors = JSONObject(responseBody)
//                        Toast.makeText(
//                            this@AddEditActivity,
//                            errors.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } catch (e: Exception) {
//                        Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
//                    }
//                }) {
//                @Throws(AuthFailureError::class)
//                override fun getHeaders(): Map<String, String> {
//                    val headers = HashMap<String, String>()
//                    headers["Accept"] = "application/json"
//                    return headers
//                }
//
//                @Throws(AuthFailureError::class)
//                override fun getBody(): ByteArray {
//                    val gson = Gson()
//                    val requestBody = gson.toJson(mahasiswa)
//                    return requestBody.toByteArray(StandardCharsets.UTF_8)
//                }
//
//                override fun getBodyContentType(): String {
//                    return "application/json"
//                }
//            }
//
//        // Menambahkan request ke request queue
//        queue!!.add(stringRequest)
//    }
//
//    private fun updateMahasiswa(id: Long) {
//        // Fungsi untuk mengubah data mahasiswa
//        setLoading(true)
//
//        val mahasiswa = Mahasiswa(
//            etNama!!.text.toString(),
//            etNPM!!.text.toString(),
//            edFakultas!!.text.toString(),
//            edProdi!!.text.toString()
//        )
//
//        val stringRequest: StringRequest = object :
//            StringRequest(Method.PUT, MahasiswaApi.UPDATE_URL + id, Response.Listener { response ->
//                val gson = Gson()
//                var mahasiswa = gson.fromJson(response, Mahasiswa::class.java)
//
//                if (mahasiswa != null)
//                    Toast.makeText(
//                        this@AddEditActivity,
//                        "Data berhasil diupdate",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                val returnIntent = Intent()
//                setResult(RESULT_OK, returnIntent)
//                finish()
//
//                setLoading(false)
//            }, Response.ErrorListener { error ->
//                setLoading(false)
//                try {
//                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
//                    val errors = JSONObject(responseBody)
//                    Toast.makeText(
//                        this@AddEditActivity,
//                        errors.getString("message"),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } catch (e: Exception) {
//                    Toast.makeText(this@AddEditActivity, e.message, Toast.LENGTH_SHORT).show()
//                }
//            }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
//
//            @Throws(AuthFailureError::class)
//            override fun getBody(): ByteArray {
//                val gson = Gson()
//                val requestBody = gson.toJson(mahasiswa)
//                return requestBody.toByteArray(StandardCharsets.UTF_8)
//            }
//
//            override fun getBodyContentType(): String {
//                return "application/json"
//            }
//        }
//        queue!!.add(stringRequest)
//    }
//
//    // Fungsi ini digunakan menampilkan layout loading
//    private fun setLoading(isLoading: Boolean) {
//        if (isLoading) {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//            )
//            layoutLoading!!.visibility = View.VISIBLE
//        } else {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//            layoutLoading!!.visibility = View.INVISIBLE
//        }
//    }
}
