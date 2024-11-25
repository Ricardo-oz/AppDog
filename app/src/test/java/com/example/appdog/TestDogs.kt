package com.example.appdog

import com.example.appdog.model.local.Dogs
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestDogs {
    private lateinit var dogs: Dogs

    @Before
    fun setup(){
        dogs = Dogs(breed = "Test")
    }

    @Test
    fun testConstructor(){
        assert(dogs.breed=="Test")
    }
}