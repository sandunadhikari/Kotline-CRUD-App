package com.example.crudapplication.repository

import com.example.crudapplication.db.UserDatabase
import com.example.crudapplication.db.entities.User

class UserRepository(private val db : UserDatabase) {

    suspend fun upsert(user:User) = db.getUserDao().upsert(user)

    suspend fun delete(user: User ) = db.getUserDao().delete(user)

    fun getAllUserDetails() = db.getUserDao().getAllUsers()
}