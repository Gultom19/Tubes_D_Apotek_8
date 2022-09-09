package com.example.tugasbesar

import android.widget.Button

class FragmentAccount: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_account, container, false)

        val btnPrev = view.findViewById<Button>(R.id.btnLogout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btnPrev = view.findViewById<Button>(R.id.btnLogout)

        btnPrev.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@FragmentAccount)
            builder.setMessage("Are you sure want to exit?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                    override fun onClick(dialogInterface: DialogInterface, i: Int){
                        // Keluar dari aplikasi
                        finishAndRemoveTask()
                    }
                })
                .show()
        }
    }
}