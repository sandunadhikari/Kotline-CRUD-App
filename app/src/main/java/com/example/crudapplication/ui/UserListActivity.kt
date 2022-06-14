package com.example.crudapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapplication.adapters.UserDataAdapter
import com.example.crudapplication.db.UserDatabase
import com.example.crudapplication.repository.UserRepository
import com.example.crudapplication.ui.UseAddActivity
import com.example.crudapplication.ui.UserViewModel
import com.example.crudapplication.ui.UserViewModelFactory
import com.example.crudapplication.util.Constants.Companion.KeyIdentifier
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class UserListActivity : AppCompatActivity(),KodeinAware {
    override val kodein by kodein()
    private val factory: UserViewModelFactory by instance()
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        viewModel = ViewModelProvider(this,factory).get(UserViewModel::class.java)

        val adapter = UserDataAdapter(listOf(),viewModel)
        user_recycler_main.layoutManager = LinearLayoutManager(this)
        user_recycler_main.adapter = adapter

        viewModel.getAllData().observe(this, Observer {
            adapter.users = it
            adapter.notifyDataSetChanged()
        })

        adapter.setOnItemClickListener {
            val intent = Intent(this, UseAddActivity::class.java)
            intent.putExtra(KeyIdentifier, it)
            startActivity(intent)

        }

        fab_button.setOnClickListener {
            var Intent = Intent(this, UseAddActivity::class.java)
            startActivity(Intent)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val user = adapter.users[position]
                viewModel.delete(user)
                Snackbar.make(findViewById(R.id.user_recycler_main),"Successfully deleted user",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.upsert(user)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(user_recycler_main)
        }

    }
}