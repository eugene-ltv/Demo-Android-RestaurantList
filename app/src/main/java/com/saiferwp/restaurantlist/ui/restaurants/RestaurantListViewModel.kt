package com.saiferwp.restaurantlist.ui.restaurants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saiferwp.restaurantlist.App
import com.saiferwp.restaurantlist.data.RestaurantsRepository
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.data.model.SortingCategory


class RestaurantListViewModel : ViewModel() {

    private val repository: RestaurantsRepository = App.component.restaurantsRepository()

    private val restaurantsList: List<Restaurant> by lazy {
        //todo use coroutines
        repository.loadJson()
    }

    private val restaurantsListLiveData = MutableLiveData<List<Restaurant>>()

    fun getRestaurantList(): MutableLiveData<List<Restaurant>> {
        val sorted = repository.currentSort.sortListDescending(restaurantsList)
        restaurantsListLiveData.value = sorted
        return restaurantsListLiveData
    }

    fun getSortOptionPosition(): Int {
        return repository.currentSort.ordinal
    }

    fun getCurrentSortingCategory(): SortingCategory {
        return repository.currentSort
    }

    fun setCurrentSortingCategory(position: Int) {
        repository.currentSort = SortingCategory.values()[position]

        val sorted = repository.currentSort.sortListDescending(restaurantsList)

        restaurantsListLiveData.value = sorted
    }
}
