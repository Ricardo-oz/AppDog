package com.example.appdog

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appdog.model.local.Dogs
import com.example.appdog.model.local.DogsDao
import com.example.appdog.model.local.DogsDatabase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestDao {
    private lateinit var dDao: DogsDao
    private lateinit var dDB: DogsDatabase


    @Before
    fun setup(){
        val context= ApplicationProvider.getApplicationContext<android.content.Context>()
        dDB=  Room.inMemoryDatabaseBuilder(context,DogsDatabase::class.java).build()
        dDao=dDB.dogsDao()
    }
    @After
    fun shutdown(){
        dDB.close()
    }

    @Test
    fun testInsertRazas() = runBlocking  {
        val RazasList= listOf(
            Dogs("Perro1" ),
            Dogs("Perro2" ),
            Dogs("Perro3" ),
            Dogs("Perro4" )
        )
        dDao.insertAllBreedList(RazasList)

        val razasLiveData = dDao.getAllBreedList()
        val listRazas = razasLiveData.value?: emptyList()

        MatcherAssert.assertThat(listRazas, CoreMatchers.not(emptyList()))
        MatcherAssert.assertThat(listRazas.size, CoreMatchers.equalTo(4))

    }
}