package com.example.tugasbesar

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesar.api.UserApi
import com.example.tugasbesar.databinding.ActivityProfileBinding
import com.example.tugasbesar.models.EditUser
import com.example.tugasbesar.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


class ProfileActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private var etUsername: EditText? = null
    private var etEmail: EditText? = null
    private var etTgglLahir: EditText? = null
    private var etTelepon: EditText? = null
    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    private val calender = Calendar.getInstance()
    private val myPreference = "myPref"
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this)
        etUsername = binding.etProfileUsername
        etEmail = binding.etProfileEmail
        etTgglLahir = binding.etProfileTgglLahir
        etTgglLahir!!.setFocusable(false)
        etTgglLahir!!.setOnClickListener(View.OnClickListener{
            DatePickerDialog(this, this, calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show()
        })
        etTelepon = binding.etProfileTelepon
        layoutLoading = findViewById(R.id.layout_loading)

        val btnCancel = binding.btnCancel
        btnCancel.setOnClickListener { finish() }
        val btnSave = binding.btnSave
        val sharedPreference =  getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val usernameKey = sharedPreference!!.getString("username","")
        Log.d("tes",usernameKey!!)
        getObatByUsername(usernameKey!!)
        btnSave.setOnClickListener { updateUser(usernameKey) }
    }

    private fun getObatByUsername(username: String) {
        // Fungsi untuk menampilkan data obat berdasarkan id
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, UserApi.GET_BY_USERNAME_URL + username, Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    val akun = jsonArray.getJSONObject(i)
                    etUsername!!.setText(akun.getString("username"))
                    etEmail!!.setText(akun.getString("email"))
                    etTgglLahir!!.setText(akun.getString("tgglLahir"))
                    etTelepon!!.setText(akun.getString("telepon"))
                }
                Toast.makeText(this@ProfileActivity, "Data Berhasil diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@ProfileActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_SHORT).show()
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

    private fun updateUser(username: String) {
        // Fungsi untuk mengubah data obat
        setLoading(true)

        val user = EditUser(
            etUsername!!.text.toString(),
            etEmail!!.text.toString(),
            etTgglLahir!!.text.toString(),
            etTelepon!!.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, UserApi.UPDATE_URL + username, Response.Listener { response ->
                val gson = Gson()
                var user = gson.fromJson(response, EditUser::class.java)

                if(user != null)
                    Toast.makeText(this@ProfileActivity, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()

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
                        this@ProfileActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_SHORT).show()
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
                val requestBody = gson.toJson(user)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.e("Calender","$year -- $month -- $dayOfMonth")
        calender.set(year, month, dayOfMonth)
        displayFormattedDate(calender.timeInMillis)
    }

    private fun displayFormattedDate(timestamp: Long){
        findViewById<TextInputEditText>(R.id.et_ProfileTgglLahir).setText(formatter.format(timestamp))
        Log.i("Formatting",timestamp.toString())
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
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