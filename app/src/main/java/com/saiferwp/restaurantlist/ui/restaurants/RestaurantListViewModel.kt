package com.saiferwp.restaurantlist.ui.restaurants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saiferwp.restaurantlist.App
import com.saiferwp.restaurantlist.data.RestaurantsRepository
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.data.model.SortingCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RestaurantListViewModel : ViewModel() {

    internal val repository: RestaurantsRepository = App.component.restaurantsRepository()
    private val restaurantsListLiveData = MutableLiveData<List<Restaurant>>()

    private var list: List<Restaurant>? = null

    fun restaurantList(): MutableLiveData<List<Restaurant>> {
        loadList()
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
        loadList()
    }

    // PRIVATE

    private fun loadList() {
        loadList(repository.currentSort)
    }

    private fun loadList(sort: SortingCategory) {
        if (list == null) {
            viewModelScope.launch(Dispatchers.IO) {
                list = repository.loadListFromDummyApi()
                list?.let {
                    sortAndPublish(it.toList(), sort)
                }
            }
        } else {
            list?.let {
                sortAndPublish(it.toList(), sort)
            }
        }
    }

    private fun sortAndPublish(list: List<Restaurant>, sort: SortingCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteRestaurants = repository.getFavoriteRestaurants()
            val favoriteNames = favoriteRestaurants.map { it.name }
            list.forEach {
                it.favourire = favoriteNames.contains(it.name)
            }

            val sorted = sort.sortListDescending(list)

            withContext(Dispatchers.Main) {
                restaurantsListLiveData.value = sorted
            }
        }
    }

    fun favouriteClicked(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteRestaurants = repository.getFavoriteRestaurants()
            val favoriteNames = favoriteRestaurants.firstOrNull { it.name == restaurant.name }
            if (favoriteNames == null) {
                repository.addFavoriteRestaurant(restaurant.name)
            } else {
                repository.removeFavoriteRestaurant(restaurant.name)
            }

            loadList()
        }
    }
}
