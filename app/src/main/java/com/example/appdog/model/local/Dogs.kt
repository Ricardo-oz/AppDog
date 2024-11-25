package com.example.appdog.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "breed_table")
data class Dogs (@PrimaryKey val breed: String)