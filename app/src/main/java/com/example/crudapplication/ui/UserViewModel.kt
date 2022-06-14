package com.example.crudapplication.ui

import androidx.lifecycle.ViewModel
import com.example.crudapplication.db.entities.User
import com.example.crudapplication.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {

    fun upsert(user: User) = GlobalScope.launch {
        repository.upsert(user)
    }

    fun delete(user: User) = GlobalScope.launch {
        repository.delete(user)
    }

    fun getAllData() = repository.getAllUserDetails()

}