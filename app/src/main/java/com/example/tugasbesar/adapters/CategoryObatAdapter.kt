package com.example.tugasbesar.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar.CategoryObatActivity
import com.example.tugasbesar.R
import com.example.tugasbesar.admin.AddEditActivity
import com.example.tugasbesar.admin.AdminActivity
import com.example.tugasbesar.models.Obat
import java.util.*

class CategoryObatAdapter(private var categoryObatList: List<Obat>, context: Context) :
RecyclerView.Adapter<CategoryObatAdapter.ViewHolder>(), Filterable {

    private var filteredCategoryObatList: MutableList<Obat>
    private val context: Context

    init {
        filteredCategoryObatList = ArrayList(categoryObatList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_obat, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredCategoryObatList.size
    }

    fun setObatList(obatList: Array<Obat>){
        this.categoryObatList = obatList.toList()
        filteredCategoryObatList = obatList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obat = filteredCategoryObatList[position]
        holder.tvObat.text = obat.obat
        holder.tvJenis.text = obat.jenis
        holder.tvHarga.text = obat.harga
        holder.btnDelete.setVisibility(View.INVISIBLE);
        holder.btnRemove.setVisibility(View.INVISIBLE);

        holder.btnAdd.setOnClickListener {
            if (context is CategoryObatActivity) obat.id?.let { it1 ->
                context.create(
                    holder.tvObat.text.toString(),
                    holder.tvJenis.text.toString(),
                    holder.tvHarga.text.toString()
                )
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Obat> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(categoryObatList)
                }else{
                    for(obat in categoryObatList){
                        if (obat.obat.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(obat)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredCategoryObatList.clear()
                filteredCategoryObatList.addAll((filterResults.values as List<Obat>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvObat: TextView
        var tvJenis: TextView
        var tvHarga: TextView
        var btnDelete: ImageButton
        var btnAdd: ImageButton
        var btnRemove: ImageButton
        var cvObat: CardView

        init {
            tvObat = itemView.findViewById(R.id.tv_nama)
            tvJenis = itemView.findViewById(R.id.tv_jenis)
            tvHarga = itemView.findViewById(R.id.tv_harga)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            btnAdd = itemView.findViewById(R.id.btn_addShopping)
            btnRemove = itemView.findViewById(R.id.btn_removeShopping)
            cvObat = itemView.findViewById(R.id.cv_obat)
        }
    }
}