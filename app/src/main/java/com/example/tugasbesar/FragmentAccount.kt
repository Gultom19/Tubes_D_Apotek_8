package com.example.tugasbesar

import android.content.Intent
import android.widget.Button
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tugasbesar.databinding.FragmentAccountBinding
import com.example.tugasbesar.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentAccount: Fragment(){
//    private lateinit var binding: FragmentAccountBinding
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
        val btnLogout: Button = view.findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener(){
            val backLogin = Intent(getActivity(), MainActivity::class.java)
            startActivity(backLogin)
        }

    }

//    fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db.userDao()
//            Log.d("MainActivity", "dbResponse: $notes")
//        }
//    }

//    fun getNote() {
//        userId = intent.getIntExtra("intent_id", 0)
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db.userDao().getUser(userId)[0]
//            .setText(notes.title)
//            edit_note.setText(notes.note)
//        }
//    }
}