package com.saiferwp.restaurantlist.data

import android.content.Context
import com.google.gson.Gson
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.data.model.RestaurantList
import com.saiferwp.restaurantlist.data.model.SortingCategory
import kotlinx.coroutines.delay
import java.io.InputStreamReader

class RestaurantsRepository(private val context: Context){

    var currentSort : SortingCategory = SortingCategory.getDefault()

    suspend fun loadJson(): List<Restaurant> {
        delay(0)

        val input = context.assets.open("restaurants.json")
        val reader = InputStreamReader(input, "UTF-8")

        val list = Gson().fromJson(reader, RestaurantList::class.java)

        return list.restaurants ?: emptyList()
    }
}