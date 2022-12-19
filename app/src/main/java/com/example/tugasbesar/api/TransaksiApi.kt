package com.example.tugasbesar.api

class TransaksiApi {
    companion object{
        val BASE_URL = "http://192.168.89.97:8000/api/"

        val GET_ALL_URL = BASE_URL + "transaksi/"
        val ADD_URL = BASE_URL + "transaksi"
        val DELETE_URL = BASE_URL + "transaksi/"
        val DELETE_ALL_URL = BASE_URL + "transaksi/"
    }
}