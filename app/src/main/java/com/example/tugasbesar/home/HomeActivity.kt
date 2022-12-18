package com.example.tugasbesar.home

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getSupportActionBar()?.hide()
        changeFragment(FragmentObat())
        getBundle()
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

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val menuInflater = MenuInflater(this)
//        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.menu_home){
//            changeFragment(FragmentObat())
//        }else if(item.itemId == R.id.menu_feed){
//            changeFragment(FragmentPromo())
//        }
//        return super.onOptionsItemSelected(item)
//    }
}