package com.example.tugasbesar.api

class UserApi {
    companion object{
        //insert your ip address
        val BASE_URL = "http://192.168.1.9:8000/api/"

        val LOGIN = BASE_URL + "login/"
        val REGISTER = BASE_URL + "register/"
    }
}