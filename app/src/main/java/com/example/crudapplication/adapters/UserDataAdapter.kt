package com.example.crudapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapplication.R
import com.example.crudapplication.db.entities.User
import com.example.crudapplication.ui.UserViewModel
import kotlinx.android.synthetic.main.user_card.view.*

class UserDataAdapter(
    var users:List<User>,
    private val viewModel: UserViewModel
): RecyclerView.Adapter<UserDataAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_card,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var currentUser = users[position]
        holder.itemView.apply {
            text_name.text = currentUser.name
            text_designation.text = currentUser.designation
            text_age.text = currentUser.age.toString()

            setOnClickListener {
                onItemClickListener?.let { it(currentUser) }
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}