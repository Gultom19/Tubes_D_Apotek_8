package com.example.tugasbesar.api

class UserApi {
    companion object{
        //insert your ip address
        val BASE_URL = "http://192.168.89.97:8000/api/"

        val LOGIN = BASE_URL + "login/"
        val REGISTER = BASE_URL + "register/"

        val GET_BY_USERNAME_URL = BASE_URL + "User/"
        val UPDATE_URL = BASE_URL + "User/"
    }
}