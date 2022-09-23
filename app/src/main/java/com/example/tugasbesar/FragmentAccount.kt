package com.example.tugasbesar

import android.content.DialogInterface
import android.content.Intent
import android.widget.Button
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar.databinding.FragmentAccountBinding
import com.example.tugasbesar.entity.Obat
import com.example.tugasbesar.room.Constant
import com.example.tugasbesar.room.User
import com.example.tugasbesar.room.UserDB
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentAccount: Fragment(){
    private lateinit var binding: FragmentAccountBinding
    lateinit var userAdapter: AccountAdapter
    val db by lazy { UserDB(requireContext()) }
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//            layoutManager = LinearLayoutManager(activity)
//
//            adapter = RecyclerAdapter()

        val btnLogout: Button = view.findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener(){
            val backLogin = Intent(getActivity(), MainActivity::class.java)
            startActivity(backLogin)
        }
    }

//    private fun setupRecyclerView() {
//        userAdapter = AccountAdapter(arrayListOf(), object :
//            AccountAdapter.OnAdapterListener {
//            override fun onClick(note: User) {
//                intentEdit(note.id, Constant.TYPE_READ)
//            }
//
//            override fun onUpdate(note: User) {
//                intentEdit(note.id, Constant.TYPE_UPDATE)
//            }
//
//            override fun onDelete(note: User) {
//                deleteDialog(note)
//            }
//        })
//        list_note.apply {
//            layoutManager = LinearLayoutManager(applicationContext)
//            adapter = noteAdapter
//        }
//    }

    private fun deleteDialog(user: User) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are You Sure to delete this data From${user.username}?")
            setNegativeButton("Cancel", DialogInterface.OnClickListener
            { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            setPositiveButton("Delete", DialogInterface.OnClickListener
            { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().deleteUser(user)
                    loadData()
                }
            })
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = db.userDao().getUser()
            Log.d("MainActivity", "dbResponse: $users")
            withContext(Dispatchers.Main) {
                userAdapter.setData(users)
            }
        }
    }

//    fun setupListener() {
//        button_create.setOnClickListener {
//            intentEdit(0, Constant.Type_CREATE)
//        }
//    }

    //pick data dari Id yang sebagai primary key
//    fun intentEdit(noteId: Int, intentType: Int) {
//        startActivity(
//            Intent(applicationContext, EditActivity::class.java)
//                .putExtra("intent_id", noteId)
//                .putExtra("intent_type", intentType)
//        )
//    }
}