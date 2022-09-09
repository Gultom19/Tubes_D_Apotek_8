package com.example.tugasbesar.entity

class Obat(var obat : String, var jenis : String, var harga : Float) {
    companion object{
        @JvmField
        var listOfHome = arrayOf(
            Obat("Paramex", "Sakit Kepala", 5000000f),
            Obat(" Promaag", "Obat Maag",1000f),
            Obat(" Vick Formula", "Obat Batuk", 25000f),
            Obat(" Bodrex", "Obat Demam",5000f),
            Obat(" Betadine", "Obat Luka",5000f)
        )
    }
}