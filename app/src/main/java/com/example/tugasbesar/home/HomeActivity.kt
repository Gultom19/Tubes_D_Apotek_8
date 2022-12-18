package com.example.tugasbesar.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.tugasbesar.*
import com.example.tugasbesar.camera.CameraActivity
import com.example.tugasbesar.map.MapActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    lateinit var mbundle : Bundle
    lateinit var vKey : String
    private val myPreference = "myPref"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getSupportActionBar()?.hide()
        changeFragment(FragmentObat())
        getBundle()
        val sharedPreference = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val usernameKey = sharedPreference!!.getString("username","")
        Log.d("tes",usernameKey!!)
//        mbundle = intent?.getBundleExtra("key")!!
//        vKey = mbundle.getString("username")!!
        bottomNav = findViewById(R.id.bottom_navigation)
        Log.d("key",vKey)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    changeFragment(FragmentObat())
                    true
                }
                R.id.menu_feed -> {
                    changeFragment(FragmentPromo())
                    true
                }
                R.id.menu_account ->{
//                    val mBundle = Bundle()
//                    mBundle.putString("username", inputUsername.getEditText()?.getText().toString())
//                    moveHome.putExtra("key", mBundle)
//                    val mBundle = Bundle()
//                    mBundle.putString("key", vKey); Log.d("tes2", vKey)
//                    FragmentAccount().arguments = mBundle
                    supportFragmentManager.beginTransaction().replace(R.id.layoutFragment, FragmentAccount())
                        .commit()
                    changeFragment(FragmentAccount())
                    true
                }
                else -> false
            }
        }

        topAppBar.setNavigationOnClickListener {
            val back = Intent(this@HomeActivity, MapActivity::class.java)
            startActivity(back)
        }
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.camera -> {
                    val moveCamera = Intent(this@HomeActivity, CameraActivity::class.java)
                    startActivity(moveCamera)
                    true
                }
                else -> false
            }
        }
    }

    fun getBundle(){
        try{
            mbundle = intent?.getBundleExtra("key")!!
            if(mbundle != null){
                vKey = mbundle.getString("username")!!
            }else{

            }
        }catch (e: NullPointerException){
            vKey = ""
        }
    }

    fun changeFragment(fragment: Fragment?){
        if(fragment != null){
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layoutFragment, fragment)
                .commit()
        }
    }
}