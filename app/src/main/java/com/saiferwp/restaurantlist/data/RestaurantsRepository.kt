package com.saiferwp.restaurantlist.data

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.data.model.RestaurantList
import com.saiferwp.restaurantlist.data.model.SortingCategory
import kotlinx.coroutines.delay
import java.io.InputStreamReader

class RestaurantsRepository(private val context: Context) {

    private val favoriteRestaurantsDB = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "fav-restaurants"
    ).build()

    var currentSort: SortingCategory = SortingCategory.getDefault()

    suspend fun loadListFromDummyApi(): List<Restaurant> {
        delay(0)

        val input = context.assets.open("restaurants.json")
        val reader = InputStreamReader(input, "UTF-8")

        val list = Gson().fromJson(reader, RestaurantList::class.java)

        return list.restaurants ?: emptyList()
    }

    suspend fun getFavoriteRestaurants(): List<FavoriteRestaurant> {
        return favoriteRestaurantsDB.favoriteRestaurantDao().getAll()
    }

    suspend fun addFavoriteRestaurant(name: String) {
        favoriteRestaurantsDB.favoriteRestaurantDao().insertAll(FavoriteRestaurant(name))
    }

    suspend fun removeFavoriteRestaurant(name: String) {
        return favoriteRestaurantsDB.favoriteRestaurantDao().delete(FavoriteRestaurant(name))
    }
}

