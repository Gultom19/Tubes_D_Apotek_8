package com.example.tugasbesar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tugasbesar.room.User
import com.example.tugasbesar.room.UserDB
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.example.tugasbesar.databinding.ActivityMainBinding
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.*
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.svg.converter.SvgConverter.createPdf
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    lateinit var mbunlde : Bundle
    lateinit var vUsername : String
    lateinit var vPassword : String
    val db by lazy { UserDB(this) }
    lateinit var checkUsername : String
    lateinit var checkPassword : String
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view:View = binding!!.root
        setContentView(view)
        binding!!.buttonSave.setOnClickListener{
            val obat = binding!!.editTextObat.text.toString()
            val jumlah = binding!!.editTextJumlah.text.toString()
            val harga = binding!!.editTextHarga.text.toString()
            try{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    if(obat.isEmpty() && jumlah.isEmpty() && harga.isEmpty()){
                        Toast.makeText(applicationContext,"Semua Data Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
                    }else{
                        createPdf(obat,jumlah,harga)
                    }
                }
            }catch(e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
    @SuppressLint("ObseleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(
        FileNotFoundException::class
    )
    private fun createPdf(nama:String,umur:String,tlp:String,alamat:String,kampus:String){
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfPath,"pdf_tugasbesar.pdf")
        FileOutputStream(file)

        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(5f,5f,5f,5f)
        @SuppressLint("UseCompatLoadingForDrawables")
        val d = getDrawable(R.drawable.logoapk)

        val bitmap = (d as BitmapDrawable?)!!.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
        val bitmapData = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapData)
        val image = Image(imageData)
        val namaPengguna = Paragraph("Identitas Pengguna").setBold().setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph(
            """
            Berikut Adalah
            nama Pengguna UAJY 2022/2023
            """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)

        val width = floatArrayOf(100f,100f)
        val table = Table(width)

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Nama Obat")))
        table.addCell(Cell().add(Paragraph(obat)))
        table.addCell(Cell().add(Paragraph("Jumlah")))
        table.addCell(Cell().add(Paragraph(jumlah)))
        table.addCell(Cell().add(Paragraph("Harga")))
        table.addCell(Cell().add(Paragraph(harga)))
        table.addCell(Cell().add(Paragraph("Tanggal Buat PDF")))
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
        table.addCell(Cell().add(Paragraph("Pukul Pembuatan PDF")))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))

        val barcodeQrCode = BarcodeQRCode(
            """
                $nama
                $umur
                $tlp
                $alamat
                $kampus
                ${LocalDate.now().format(dateTimeFormatter)}
                ${LocalTime.now().format(timeFormatter)}
                """.trimIndent()
        )
        val qrCodeObject = barcodeQrCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(HorizontalAlignment.CENTER)

        document.add(image)
        document.add(namaPengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)

        document.close()
        Toast.makeText(this,"Pdf Created",Toast.LENGTH_SHORT).show()
    }

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

        textBtnSignUp.setOnClickListener {
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

            runBlocking(){
                val usernameDb = async {
                    val Account: User? = db.userDao().getAccount(username, password)
                    if (Account != null) {
                        Account.username
                    } else {
                        null
                    }
                }
                val passwordDb = async {
                    val Account: User? = db.userDao().getAccount(username, password)
                    Log.d("MainActivity","dbResponse: $Account")
                    if (Account != null) {
                        Account.password
                    } else {
                        null
                    }
                }
                checkUsername = usernameDb.await().toString()
                checkPassword = passwordDb.await().toString()
            }

            if (username == checkUsername && password == checkPassword) checkLogin = true

            if(username != checkUsername) {
                inputUsername.setError("The username you entered is incorrect")
                checkLogin = false
            }
            if(password != checkPassword){
                inputPassword.setError("The password you entered is incorrect")
                checkLogin = false
            }
            if (!checkLogin) return@OnClickListener
            else{
                val moveHome = Intent(this, HomeActivity::class.java)
                startActivity(moveHome)
            }

        })
    }

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

    fun setText() {
        etUsername = findViewById(R.id.etUsername)
        etUsername.setText(vUsername, TextView.BufferType.EDITABLE)
        etPassword = findViewById(R.id.etPassword)
        etPassword.setText(vPassword, TextView.BufferType.EDITABLE)
    }
}