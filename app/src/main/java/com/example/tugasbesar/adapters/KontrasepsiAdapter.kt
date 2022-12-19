package com.example.tugasbesar.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar.R
import com.example.tugasbesar.admin.AddEditActivity
import com.example.tugasbesar.admin.AdminActivity
import com.example.tugasbesar.admin.AdminKontrasepsiActivity
import com.example.tugasbesar.models.Kontrasepsi
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class KontrasepsiAdapter (private var kontrasepsiList: List<Kontrasepsi>, context: Context) :
    RecyclerView.Adapter<KontrasepsiAdapter.ViewHolder>(), Filterable {

    private var filteredKontrasepsiList: MutableList<Kontrasepsi>
    private val context: Context

    init {
        filteredKontrasepsiList = ArrayList(kontrasepsiList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_kontrasepsi, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredKontrasepsiList.size
    }

    fun setKontrasepsiList(kontrasepsiList: Array<Kontrasepsi>){
        this.kontrasepsiList = kontrasepsiList.toList()
        filteredKontrasepsiList = kontrasepsiList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kontrasepsi = filteredKontrasepsiList[position]
        holder.tvKontrasepsi.text = kontrasepsi.kontrasepsi
        holder.tvJenis.text = kontrasepsi.jenis
        holder.tvHarga.text = kontrasepsi.harga
        holder.btnAdd.setVisibility(View.INVISIBLE);
        holder.btnRemove.setVisibility(View.INVISIBLE);

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data produk ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is AdminKontrasepsiActivity) kontrasepsi.id?.let { it1 ->
                        context.deleteKontrasepsi(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvKontrasepsi.setOnClickListener {
            val i = Intent(context, AddEditActivity::class.java)
            i.putExtra("id", kontrasepsi.id)
            if (context is AdminKontrasepsiActivity)
                context.startActivityForResult(i, AdminKontrasepsiActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Kontrasepsi> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(kontrasepsiList)
                }else{
                    for(kontrasepsi in kontrasepsiList){
                        if (kontrasepsi.kontrasepsi.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(kontrasepsi)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredKontrasepsiList.clear()
                filteredKontrasepsiList.addAll((filterResults.values as List<Kontrasepsi>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvKontrasepsi: TextView
        var tvJenis: TextView
        var tvHarga: TextView
        var btnDelete: ImageButton
        var btnAdd: ImageButton
        var btnRemove: ImageButton
        var cvKontrasepsi: CardView

        init {
            tvKontrasepsi = itemView.findViewById(R.id.tv_nama)
            tvJenis = itemView.findViewById(R.id.tv_jenis)
            tvHarga = itemView.findViewById(R.id.tv_harga)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            btnAdd = itemView.findViewById(R.id.btn_addShopping)
            btnRemove = itemView.findViewById(R.id.btn_removeShopping)
            cvKontrasepsi = itemView.findViewById(R.id.cv_kontrasepsi)
        }
    }
}