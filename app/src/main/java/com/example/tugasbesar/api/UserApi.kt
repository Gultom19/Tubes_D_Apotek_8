package com.example.tugasbesar.api

class UserApi {
    companion object{
        //insert your ip address
        val BASE_URL = "http://192.168.1.27:8000/api/"

        val LOGIN = BASE_URL + "login/"
        val REGISTER = BASE_URL + "register/"

        val GET_ALL_URL = BASE_URL + "User/"
        val GET_BY_USERNAME_URL = BASE_URL + "User/"
        val UPDATE_URL = BASE_URL + "User/"
        val DELETE_URL = BASE_URL + "User/"
    }
}