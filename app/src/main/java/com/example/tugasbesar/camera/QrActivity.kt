package com.example.tugasbesar.camera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tugasbesar.R
import com.example.tugasbesar.databinding.ActivityCameraBinding
import com.example.tugasbesar.databinding.ActivityQrBinding
import com.example.tugasbesar.home.HomeActivity
import com.example.tugasbesar.map.MapActivity
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.android.synthetic.main.activity_home.*

class QrActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQrBinding
    companion object{
        private const val CAMERA_REQUEST_CODE = 100
        private const val TAG = "MAIN_TAG"
    }
    private lateinit var cameraPermission: Array<String>

    private var imageUri: Uri? = null

    private var barcodeScannerOption: BarcodeScannerOptions? = null
    private var barcodeScanner: BarcodeScanner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide();

        binding.cameraButton.setOnClickListener(this)
        binding.scanBtn.setOnClickListener(this)

        cameraPermission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        barcodeScannerOption = BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build()
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOption!!)

        topAppBar.setNavigationOnClickListener {
            val back = Intent(this@QrActivity, HomeActivity::class.java)
            startActivity(back)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cameraButton -> {
                if(checkCameraPermission()){
                    pickImageCamera()
                }else{
                    requestCameraPermission()
                }
            }
            R.id.scanBtn ->{
                if(imageUri == null){
                    showToast("Pick image first")
                }else{
                    detectResultFromImage()
                }
            }
        }
    }

    private fun checkCameraPermission():Boolean{
        val resultCamera = (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
        val resultStorage = (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        return resultCamera && resultStorage
    }

    private fun requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if(grantResults.isNotEmpty()){
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if(cameraAccepted&&storageAccepted){
                        pickImageCamera()
                    }else{
                        showToast("Camera And Storage Permission Are Required")
                    }
                }
            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    private fun pickImageCamera(){
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE,"Foto Sample")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Deskripsi Foto Sample")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)

        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            Log.d(TAG,"cameraActivityResultLauncher: imageUri: $imageUri")

            binding.imageTv.setImageURI(imageUri)
        }
    }

    private fun detectResultFromImage(){
        try{
            val inputImage = InputImage.fromFilePath(this,imageUri!!)

            val barcodeResult = barcodeScanner?.process(inputImage)
                ?.addOnSuccessListener { barcodes->
                    extractbarcodeQrCodeInfo(barcodes)
                }
                ?.addOnFailureListener{ e ->
                    Log.d(TAG,"detectResultFromImage: ",e)
                    showToast("Failed scanning due to ${e.message}")
                }
        }catch (e: Exception){
            Log.d(TAG,"detectResultFromImage: ",e)
            showToast("Failed due to ${e.message}")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun extractbarcodeQrCodeInfo(barcodes: List<Barcode>){
        for (barcode in barcodes){
            val bound = barcode.boundingBox
            val corner = barcode.cornerPoints

            val rawValue = barcode.rawValue
            Log.d(TAG,"ExtractBarcodeCodeInfo: RawValue : $rawValue")

            val valueType = barcode.valueType
            when(valueType){
                Barcode.TYPE_WIFI->{
                    val typeWifi = barcode.wifi
                    val ssid = "${typeWifi?.ssid}"
                    val password = "${typeWifi?.password}"
                    var encryptionType = "${typeWifi?.encryptionType}"

                    if(encryptionType == "1"){
                        encryptionType = "OPEN"
                    }else if(encryptionType == "2"){
                        encryptionType = "WPA"
                    }else if(encryptionType == "3"){
                        encryptionType = "WEP"
                    }
                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_WIFI")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: ssid: $ssid")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: password:$password")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: encryptionType:$encryptionType")
                    binding.resultView.text =
                        "TYPE_WIFI \n ssid: $ssid \npassword: $password\nencryptionType: $encryptionType" + "\n\nrawValue : $rawValue"
                }
                Barcode.TYPE_URL -> {
                    val typeUrl = barcode.url
                    val title = "${typeUrl?.title}"
                    val url = "${typeUrl?.url}"
                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_URL")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: title: $title")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: url: $url")
                    binding.resultView.text =
                        "TYPE_URL \ntitle: $title \nurl: $url \n\nrawValue: $rawValue"
                }
                Barcode.TYPE_EMAIL -> {
                    val typeEmail = barcode.email
                    val address = "${typeEmail?.address}"
                    val body = "${typeEmail?.body}"
                    val subject = "${typeEmail?.subject}"
                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_EMAIL")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: address:$address")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: body: $body")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: subject:$subject")
                    binding.resultView.text =
                        "TYPE_EMAIL \ntitle: $address \nurl: $body\nsubject: $subject \n\nrawValue : $rawValue"
                }
                Barcode.TYPE_CONTACT_INFO -> {
                    val typeContact = barcode.contactInfo
                    val title = "${typeContact?.title}"
                    val organisasi = "${typeContact?.organization}"
                    val name = "${typeContact?.name}"
                    val phone = "${typeContact?.phones}"
                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_CONTACT_INFO")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Title: $title")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Organization:$organisasi")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: nama: $name")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Phone: $phone")
                    binding.resultView.text = "TYPE_CONTACT_INFO " +
                            "\ntitle: $title " +
                            "\nnama: $name " +
                            "\norganization: $organisasi " +
                            "\nPhone : $phone" + "\n\nrawValue :$rawValue"
                }
                else -> {
                    binding.resultView.text = "rawValue: $rawValue"
                }
            }
        }
    }
}