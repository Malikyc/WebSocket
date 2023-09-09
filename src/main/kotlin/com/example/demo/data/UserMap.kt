package com.example.demo.data

import com.example.demo.models.User

object UserMap {
    private var users : HashMap<String, User> = HashMap()



    fun getUserById(id : String) : User{
        return users[id]!!
    }

    fun addUser(id : String){
        val user = User(id)
        users.put(id,user)
    }
}