package com.example.tugasbesar.entity

class Obat(var obat : String, var jenis : String, var harga : Double) {
    companion object{
        @JvmField
        var listOfHome = arrayOf(
            Obat("Paramex", "Sakit Kepala", 5000000.99),
            Obat(" Promaag", "Obat Maag",1000.0),
            Obat(" Vick Formula", "Obat Batuk", 25000.12),
            Obat(" Bodrex", "Obat Demam",5000.42),
            Obat(" Betadine", "Obat Luka",5000.62),
        )
    }
}