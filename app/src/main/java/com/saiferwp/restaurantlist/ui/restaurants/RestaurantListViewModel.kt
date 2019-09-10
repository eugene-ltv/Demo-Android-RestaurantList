package com.saiferwp.restaurantlist.ui.restaurants

import androidx.lifecycle.ViewModel
import com.saiferwp.restaurantlist.App
import com.saiferwp.restaurantlist.data.RestaurantsRepository
import com.saiferwp.restaurantlist.data.model.Restaurant


class RestaurantListViewModel : ViewModel() {

    private val repository: RestaurantsRepository = App.component.restaurantsRepository()

    fun getRestaurantList(): List<Restaurant> {
        return repository.loadJson()
    }
}
