package com.example.crudapplication.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_table")
data class User (
    @ColumnInfo(name="user_name")
    var name: String,
    @ColumnInfo(name="user_designation")
    var designation: String,
    @ColumnInfo(name="user_age")
    var age: Int

    ):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}