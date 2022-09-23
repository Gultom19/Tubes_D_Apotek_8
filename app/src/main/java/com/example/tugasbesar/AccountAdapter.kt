package com.example.tugasbesar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar.room.User
import kotlinx.android.synthetic.main.activity_account_adapter.view.*


class AccountAdapter (private val users: ArrayList<User>, private val listener: OnAdapterListener) : RecyclerView.Adapter<AccountAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_account_adapter,parent, false)
        )
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.view.etUsernameProfile.setText(user.username)
        holder.view.etPasswordProfile.setText(user.password)
        holder.view.etEmailProfile.setText(user.email)
        holder.view.etTanggalLahirProfile.setText(user.tanggal)
        holder.view.etTeleponProfile.setText(user.telepon)
//        holder.view.text_title.setOnClickListener{
//            listener.onClick(note)
//        }
//        holder.view.icon_edit.setOnClickListener {
//            listener.onUpdate(note)
//        }
//        holder.view.icon_delete.setOnClickListener {
//            listener.onDelete(note)
//        }
    }
    override fun getItemCount() = users.size
    inner class UserViewHolder( val view: View) : RecyclerView.ViewHolder(view)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<User>){
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(note: User)
        fun onUpdate(note: User)
        fun onDelete(note: User)
    }

//    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val userText: TextView = itemView.findViewById(R.id.usernameView)
//        val emailText: TextView = itemView.findViewById(R.id.emailView)
//        val tglText: TextView = itemView.findViewById(R.id.tglView)
//        val tlpText: TextView = itemView.findViewById(R.id.tlpView)
//    }
}
