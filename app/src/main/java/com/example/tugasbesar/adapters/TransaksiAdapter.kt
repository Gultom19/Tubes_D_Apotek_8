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
import com.example.tugasbesar.R
import com.example.tugasbesar.TransaksiActivity
import com.example.tugasbesar.admin.AddEditActivity
import com.example.tugasbesar.admin.AdminActivity
import com.example.tugasbesar.models.Obat
import java.util.*

class TransaksiAdapter (private var transaksiList: List<Obat>, context: Context) :
    RecyclerView.Adapter<TransaksiAdapter.ViewHolder>(), Filterable {

    private var filteredTransaksiList: MutableList<Obat>
    private val context: Context

    init {
        filteredTransaksiList = ArrayList(transaksiList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_obat, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredTransaksiList.size
    }

    fun setTransaksiList(transaksiList: Array<Obat>){
        this.transaksiList = transaksiList.toList()
        filteredTransaksiList = transaksiList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaksi = filteredTransaksiList[position]
        holder.tvTransaksi.text = transaksi.obat
        holder.tvJenis.text = transaksi.jenis
        holder.tvHarga.text = transaksi.harga
        holder.btnDelete.setVisibility(View.INVISIBLE);
        holder.btnAdd.setVisibility(View.INVISIBLE);

//        holder.btnDelete.setOnClickListener {
//            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
//            materialAlertDialogBuilder.setTitle("Konfirmasi")
//                .setMessage("Apakah anda yakin ingin menghapus data produk ini?")
//                .setNegativeButton("Batal", null)
//                .setPositiveButton("Hapus") { _, _ ->
//                    if (context is AdminActivity) transaksi.id?.let { it1 ->
//                        context.deleteTransaksi(
//                            it1
//                        )
//                    }
//                }
//                .show()
//        }
        holder.btnRemove.setOnClickListener {
            if (context is TransaksiActivity) transaksi.id?.let { it1 ->
                context.delete(
                    it1
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
                    filtered.addAll(transaksiList)
                }else{
                    for(transaksi in transaksiList){
                        if (transaksi.obat.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(transaksi)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredTransaksiList.clear()
                filteredTransaksiList.addAll((filterResults.values as List<Obat>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTransaksi: TextView
        var tvJenis: TextView
        var tvHarga: TextView
        var btnDelete: ImageButton
        var btnAdd: ImageButton
        var btnRemove: ImageButton
        var cvTransaksi: CardView

        init {
            tvTransaksi = itemView.findViewById(R.id.tv_nama)
            tvJenis = itemView.findViewById(R.id.tv_jenis)
            tvHarga = itemView.findViewById(R.id.tv_harga)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            btnAdd = itemView.findViewById(R.id.btn_addShopping)
            btnRemove = itemView.findViewById(R.id.btn_removeShopping)
            cvTransaksi = itemView.findViewById(R.id.cv_obat)
        }
    }
}