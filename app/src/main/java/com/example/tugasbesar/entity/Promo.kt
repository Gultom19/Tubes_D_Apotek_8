package com.example.tugasbesar.entity

class Promo(var promo : String, var jenis : String) {
    companion object{
        @JvmField
        var listOfFeed = arrayOf(
            Promo(" %20", "Sakit Kepala"),
            Promo(" %30", "Obat Maag"),
            Promo(" %10", "Obat Batuk"),
            Promo(" %15", "Obat Demam"),
            Promo(" %5", "Obat Luka")
        )
    }
}