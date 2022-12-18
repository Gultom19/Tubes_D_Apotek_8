package com.example.tugasbesar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesar.adapters.ObatAdapter
import com.example.tugasbesar.adapters.TransaksiAdapter
import com.example.tugasbesar.admin.AddEditActivity
import com.example.tugasbesar.api.ObatApi
import com.example.tugasbesar.api.TransaksiApi
import com.example.tugasbesar.home.HomeActivity
import com.example.tugasbesar.models.Obat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TransaksiActivity : AppCompatActivity() {
    private var srTransaksi: SwipeRefreshLayout? = null
    private var adapter: TransaksiAdapter? = null
    private var svTransaksi: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private lateinit var btnBuy: Button
    private var queue: RequestQueue? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        getSupportActionBar()?.hide()

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srTransaksi = findViewById(R.id.sr_transaksi)
        svTransaksi = findViewById(R.id.sv_transaksi)
        btnBuy = findViewById(R.id.btnBuy)

        srTransaksi?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allObat() })

        val rvProduk = findViewById<RecyclerView>(R.id.rv_transaksi)
        adapter = TransaksiAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allObat()

        topAppBar.setNavigationOnClickListener {
            val back = Intent(this@TransaksiActivity, HomeActivity::class.java)
            startActivity(back)
        }

        btnBuy.setOnClickListener {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        createPDF()
                }
            } catch (e: FileNotFoundException){
                e.printStackTrace()
            }
        }
    }

    private fun allObat() {
        srTransaksi!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TransaksiApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                var obat : Array<Obat> = gson.fromJson(jsonArray.toString(), Array<Obat>::class.java)

                adapter!!.setTransaksiList(obat)
                adapter!!.filter.filter(svTransaksi!!.query)
                srTransaksi!!.isRefreshing = false

                if(!obat.isEmpty())
                    Toast.makeText(this@TransaksiActivity, "Data Berhasil Diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(this@TransaksiActivity, "Data Kosong!", Toast.LENGTH_SHORT)
                        .show()
            }, Response.ErrorListener { error ->
                srTransaksi!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@TransaksiActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@TransaksiActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    fun delete(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, TransaksiApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var mahasiswa = gson.fromJson(response, Obat::class.java)
                if(mahasiswa != null)
                    Toast.makeText(this@TransaksiActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                allObat()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@TransaksiActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@TransaksiActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = java.util.HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    fun deleteAll() {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, TransaksiApi.DELETE_ALL_URL, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var mahasiswa = gson.fromJson(response, Obat::class.java)
                if(mahasiswa != null)
                    Toast.makeText(this@TransaksiActivity, "Semua Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                allObat()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@TransaksiActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@TransaksiActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = java.util.HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allObat()
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
            layoutLoading!!.visibility = View.GONE
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(
        FileNotFoundException::class
    )
    private fun createPDF() {
        //ini berguna untuk akses Writing ke Storage HP kalian dalam mode Download.
        //harus diketik jangan COPAS!!!!
        val pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()
        val file = File(pdfpath, "TRANSAKSI.pdf")
        FileOutputStream(file)

        //inisialisasi pembuatan PDF
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(5f, 5f, 5f, 5f)
        @SuppressLint("UseCompatLoadingForDrawables") val d = getDrawable(R.drawable.transaction)

        //penambahan gambar pada Gambar atas
        val bitmap = (d as BitmapDrawable?)!!.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapData = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapData)
        val image = com.itextpdf.layout.element.Image(imageData)
        val namaPengguna = com.itextpdf.layout.element.Paragraph("Identitas Pembeli").setBold().setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph(
            """
                        Berikut adalah
                        Nama Pembeli 2022/2023
                        """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)
        val width = floatArrayOf(100f,100f)

        srTransaksi!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TransaksiApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    val akun = jsonArray.getJSONObject(i)
                    val table = Table(width)
                    table.setHorizontalAlignment(HorizontalAlignment.CENTER)
                    table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph("Obat")))
                    table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph(akun.getString("obat"))))
                    table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph("Jenis")))
                    table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph(akun.getString("jenis"))))
                    table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph("Harga")))
                    table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph(akun.getString("harga"))))
                }
                var obat : Array<Obat> = gson.fromJson(jsonArray.toString(), Array<Obat>::class.java)

                adapter!!.setTransaksiList(obat)
                adapter!!.filter.filter(svTransaksi!!.query)
                srTransaksi!!.isRefreshing = false

                if(!obat.isEmpty())
                    Toast.makeText(this@TransaksiActivity, "Data Berhasil Diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(this@TransaksiActivity, "Data Kosong!", Toast.LENGTH_SHORT)
                        .show()
            }, Response.ErrorListener { error ->
                srTransaksi!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@TransaksiActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@TransaksiActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
        val table = Table(width)

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph("Tanggal Buat PDF")))
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        table.addCell(
            Cell().add(
                com.itextpdf.layout.element.Paragraph(
                    LocalDate.now().format(dateTimeFormatter)
                )
            ))
        table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph("Pukul Pembuatan PDF")))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        table.addCell(
            Cell().add(
                com.itextpdf.layout.element.Paragraph(
                    LocalTime.now().format(timeFormatter)
                )
            ))

        document.add(image)
        document.add(namaPengguna)
        document.add(group)
        document.add(table)

        document.close()
        Toast.makeText(this,"Pdf Created",Toast.LENGTH_SHORT).show()
    }
}