package com.example.appdog.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.appdog.model.remote.DogRepository
import com.example.appdog.model.local.Dogs
import com.example.appdog.model.local.DogsDatabase
import com.example.appdog.model.local.DogsImages
import kotlinx.coroutines.launch

class DogViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DogRepository

    init {
        val db = DogsDatabase.getDatabase(application)
        val dogsDao = db.dogsDao()
        repository = DogRepository(dogsDao)

        viewModelScope.launch {
            repository.fetchBreed()
        }
    }


    fun getBreedList(): LiveData<List<Dogs>> = repository.breedListLivedata

    private var breedSelected : String = ""

    fun getImagesByBreedFromInternet(breed: String) = viewModelScope.launch {
        breedSelected = breed
        repository.fetchDogImages(breed)
    }

    fun getImages(): LiveData<List<DogsImages>> = repository.getAllImagesByBreed(breedSelected)


    fun updateFav(dogsImages: DogsImages) = viewModelScope.launch {
        Log.d("repoFav", " repo fav")
        repository.updateFavImages(dogsImages)
    }
    fun deleteallFav() {
        viewModelScope.launch {
            Log.d("repoFav", " repo fav")
            repository.deleteFavImages()
        }
    }
}