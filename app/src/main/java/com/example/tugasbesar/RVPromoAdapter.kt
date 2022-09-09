package com.example.tugasbesar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar.entity.Promo

class RVPromoAdapter(private val data: Array<Promo>) : RecyclerView.Adapter<RVPromoAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_promo, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNamaPromo.text = currentItem.promo
        holder.tvDetailsPromo.text = currentItem.jenis
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvNamaPromo : TextView = itemView.findViewById(R.id.tv_nama_promo)
        val tvDetailsPromo : TextView = itemView.findViewById(R.id.tv_details_promo)
    }
}