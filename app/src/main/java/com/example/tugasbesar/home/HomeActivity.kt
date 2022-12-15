package com.example.tugasbesar.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tugasbesar.*
import com.example.tugasbesar.camera.CameraActivity
import com.example.tugasbesar.map.MapActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getSupportActionBar()?.hide()
        changeFragment(FragmentObat())

        bottomNav = findViewById(R.id.bottom_navigation)
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
                    changeFragment(FragmentAccount())
                    true
                }
                else -> false
            }
        }

        topAppBar.setNavigationOnClickListener {
            val moveMap = Intent(this@HomeActivity, MapActivity::class.java)
            startActivity(moveMap)
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.camera -> {
                    val moveCamera = Intent(this, CameraActivity::class.java)
                    startActivity(moveCamera)
                    true
                }
                else -> false
            }
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