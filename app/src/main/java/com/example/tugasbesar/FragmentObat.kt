package com.example.tugasbesar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar.models.Obat

//aa
class FragmentObat: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_obat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVObatAdapter = RVObatAdapter(Obat)

        val rvObat : RecyclerView = view.findViewById(R.id.rv_obat)
        rvObat.layoutManager = layoutManager
        rvObat.setHasFixedSize(true)
        rvObat.adapter = adapter
    }
}