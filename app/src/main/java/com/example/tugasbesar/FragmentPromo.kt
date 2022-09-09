package com.example.tugasbesar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar.entity.Promo

class FragmentPromo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_promo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVPromoAdapter = RVPromoAdapter(Promo.listOfFeed)

        val rvPromo : RecyclerView = view.findViewById(R.id.rv_promo)

        rvPromo.layoutManager = layoutManager

        rvPromo.setHasFixedSize(true)

        rvPromo.adapter = adapter
    }
}