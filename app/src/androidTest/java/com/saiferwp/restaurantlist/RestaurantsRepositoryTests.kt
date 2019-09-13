package com.saiferwp.restaurantlist

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.saiferwp.restaurantlist.data.RestaurantsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantsRepositoryTests {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun loadRestaurantListFromJson() {
        val repository = RestaurantsRepository(appContext)

        runBlocking {
            val list = repository.loadListFromDummyApi()
            Assert.assertTrue(list.size == 19)
        }
    }

    @Test
    fun loadRestaurantListFromJson_checkItem() {
        val repository = RestaurantsRepository(appContext)

        runBlocking {
            val list = repository.loadListFromDummyApi()
            Assert.assertTrue(list[2].name == "Royal Thai")
        }
    }
}