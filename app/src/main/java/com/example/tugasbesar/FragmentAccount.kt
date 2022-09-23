package com.example.tugasbesar

import android.content.Intent
import android.widget.Button
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tugasbesar.databinding.FragmentAccountBinding

class FragmentAccount: Fragment(){
    private lateinit var binding: FragmentAccountBinding

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

        btnLogout.setOnClickListener(){
            val moveProfile = Intent(getActivity(), MainActivity::class.java)
            startActivity(moveProfile)
        }
    }


}