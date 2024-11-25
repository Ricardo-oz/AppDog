package com.example.appdog.model.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.appdog.model.local.DogsDao
import com.example.appdog.model.local.DogsImages

class DogRepository(private val dogsDao: DogsDao) {

    private val networkService = RetrofitClient.retrofitInstance()
    val breedListLivedata = dogsDao.getAllBreedList()

    suspend fun fetchBreed() {
        val service = kotlin.runCatching { networkService.getBreedsList() }
        service.onSuccess {
            when (it.code()) {
                in 200..299 ->it.body()?.let {
                    dogsDao.insertAllBreedList(fromInternetToBreedEntity(it))
                }
                else -> Log.d("REPO", "${it.code()} - ${it.errorBody()}")
            }
        }
        service.onFailure {
            Log.e("REPO", "${it.message}")
        }
    }


    //Recibe la raza y realiza la solicitud guardando el elemento en la Base de datos.
    suspend fun fetchDogImages(breed: String) {
        val service = kotlin.runCatching { networkService.getImagesList(breed) }
        service.onSuccess {
            when (it.code()) {
                200 -> it.body()?.let {
                    dogsDao.insertAllImagesList(fromInternetToImagesEntity(it, breed))
                }
                else -> Log.d("REPO-IMG", "${it.code()} - ${it.errorBody()}")
            }
        }
        service.onFailure {
            Log.e("REPO", "${it.message}")
        }
    }

    // Retorna las imagenes por raza desde la base de datos.
    fun getAllImagesByBreed(breed: String): LiveData<List<DogsImages>> {
        return dogsDao.getAllDoggiesImages(breed)
    }

    suspend fun updateFavImages(dogsImages: DogsImages) {
        Log.d("updateFav", " updatefav")
        dogsDao.updateFavImages(dogsImages)
    }
    suspend fun deleteFavImages() {
        Log.d("deleteFav", " updatefav")
        dogsDao.deleteFavImages()
    }

}