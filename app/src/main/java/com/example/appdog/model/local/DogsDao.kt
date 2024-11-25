package com.example.appdog.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DogsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBreedList(listBreed: List<Dogs>)

    @Query("SELECT * FROM breed_table ORDER BY breed ASC")
    fun getAllBreedList(): LiveData<List<Dogs>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllImagesList(dogsImages: List<DogsImages>)

    @Update
    suspend fun updateFavImages(dogsImages: DogsImages)

    @Query("UPDATE images_table SET fav = 0 WHERE fav = 1")
    suspend fun deleteFavImages()

    @Query("SELECT * FROM images_table WHERE breed = :breed")
    fun getAllDoggiesImages(breed : String): LiveData<List<DogsImages>>

    // Funcion que trae todos los favoritos
    @Query("SELECT * FROM images_table WHERE fav = 1")
    fun getAllFavImages(): LiveData<List<DogsImages>>
}