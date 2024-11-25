package com.example.appdog.model.remote

import com.example.appdog.model.local.Dogs
import com.example.appdog.model.local.DogsImages

fun fromInternetToBreedEntity(retroRazas: RetroBreed): List<Dogs> {
    val breedNames = retroRazas.message.keys
    return breedNames.map{breedNames->

        Dogs(breed = breedNames) }
}

fun fromInternetToImagesEntity(iImages: RetroImages, breed: String): List<DogsImages> {
    val imageName= iImages.message
    return imageName.map {imageName ->
        DogsImages(imageUrl = imageName, breed = breed) }
}