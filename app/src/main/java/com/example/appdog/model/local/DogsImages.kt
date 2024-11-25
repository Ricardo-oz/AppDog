package com.example.appdog.model.local

import androidx.room.Entity

@Entity(tableName = "images_table", primaryKeys = ["breed", "imageUrl"])
data class DogsImages(
    val imageUrl: String,
    val breed: String,
    var fav: Boolean = false
)