package com.example.tugasbesar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar.databinding.ActivityMainBinding
import com.example.tugasbesar.entity.Promo
import com.example.tugasbesar.models.Obat
import kotlinx.android.synthetic.main.fragment_account.*

//aa
class FragmentObat: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_obat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCategoryObat: Button = view.findViewById(R.id.btnCategoryObat)
        btnCategoryObat.setOnClickListener(){
            val toCategoryObat = Intent(getActivity(), CategoryObatActivity::class.java)
            startActivity(toCategoryObat)
        }
    }
}