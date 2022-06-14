package com.example.crudapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.crudapplication.R
import com.example.crudapplication.db.UserDatabase
import com.example.crudapplication.db.entities.User
import com.example.crudapplication.repository.UserRepository
import com.example.crudapplication.util.Constants
import com.example.crudapplication.util.Constants.Companion.KeyIdentifier
import kotlinx.android.synthetic.main.activity_use_add.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class UseAddActivity : AppCompatActivity(),KodeinAware {
    override val kodein by kodein()
    private val factory: UserViewModelFactory by instance()
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_add)

        val receivedObject =intent.getSerializableExtra(KeyIdentifier) as? User

        viewModel = ViewModelProvider(this,factory).get(UserViewModel::class.java)

        if (receivedObject != null){
            save_button.text = "update"
            user_name.setText(receivedObject.name)
            user_designation.setText(receivedObject.designation)
            user_age.setText(receivedObject.age.toString())
        }


        save_button.setOnClickListener {
            val name = user_name.text.toString().trim()
            val designation = user_designation.text.toString().trim()
            val age = (user_age.text.toString().trim()).toInt()

            if (!name.isNullOrEmpty()){
                if (age > 1){
                    if (receivedObject != null) {
                        val user = User(name, designation, age)
                        user.id = receivedObject.id
                        viewModel.upsert(user)
                        Toast.makeText(this, "Successfully Updated!!", Toast.LENGTH_SHORT).show()
                    }else{
                        val user = User(name, designation, age)
                        viewModel.upsert(user)
                        Toast.makeText(this, "Successfully Added!!", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }else{
                    Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }else{
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}