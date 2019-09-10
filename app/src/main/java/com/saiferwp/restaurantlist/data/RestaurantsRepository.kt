package com.saiferwp.restaurantlist.data

import android.content.Context
import com.google.gson.Gson
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.data.model.RestaurantList
import java.io.InputStreamReader

class RestaurantsRepository(private val context: Context){

    fun loadJson(): List<Restaurant> {
        val input = context.assets.open("restaurants.json")
        val reader = InputStreamReader(input, "UTF-8")

        val list = Gson().fromJson(reader, RestaurantList::class.java)

        return list.restaurants ?: emptyList()
    }
}