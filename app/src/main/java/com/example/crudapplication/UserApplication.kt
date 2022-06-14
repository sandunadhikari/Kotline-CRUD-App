package com.example.crudapplication

import android.app.Application
import com.example.crudapplication.db.UserDatabase
import com.example.crudapplication.repository.UserRepository
import com.example.crudapplication.ui.UserViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class UserApplication: Application(),KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@UserApplication))
        bind() from singleton { UserDatabase(instance()) }
        bind() from singleton {
            UserRepository(instance())
        }

        bind() from provider {
            UserViewModelFactory(instance())
        }
    }
}