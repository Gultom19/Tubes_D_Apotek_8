package com.example.tugasbesar

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.tugasbesar.databinding.ActivityCategoryObatBinding
import com.example.tugasbesar.databinding.ActivityMainBinding
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class CategoryObatActivity : AppCompatActivity() {
    private var binding: ActivityCategoryObatBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryObatBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        binding!!.buttonSave.setOnClickListener {
            val obat = binding!!.editTextObat.text.toString()
            val jenis = binding!!.editTextJenis.text.toString()
            val harga = binding!!.editTextHarga.text.toString()

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    if(obat.isEmpty() && jenis.isEmpty() && harga.isEmpty()){
                        Toast.makeText(applicationContext, "Semuanya Tidak boleh Kosong", Toast.LENGTH_SHORT).show()
                    } else {
                        createPDF(obat, jenis, harga)
                    }
                }
            } catch (e: FileNotFoundException){
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(
        FileNotFoundException::class
    )
    private fun createPDF(obat: String, jenis: String, harga: String) {
        //ini berguna untuk akses Writing ke Storage HP kalian dalam mode Download.
        //harus diketik jangan COPAS!!!!
        val  pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfpath, "tugaspbp.pdf")
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
        val table = Table(width)

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph("Obat")))
        table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph(obat)))
        table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph("Jenis")))
        table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph(jenis)))
        table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph("Harga")))
        table.addCell(Cell().add(com.itextpdf.layout.element.Paragraph(harga)))
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

        val barcodeQrCode = BarcodeQRCode(
            """
                $obat
                $jenis
                $harga
                ${LocalDate.now().format(dateTimeFormatter)}
                ${LocalTime.now().format(timeFormatter)}
                """.trimIndent()
        )
        val qrCodeObject = barcodeQrCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = com.itextpdf.layout.element.Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(
            HorizontalAlignment.CENTER)

        document.add(image)
        document.add(namaPengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)

        document.close()
        Toast.makeText(this,"Pdf Created",Toast.LENGTH_SHORT).show()
    }
}