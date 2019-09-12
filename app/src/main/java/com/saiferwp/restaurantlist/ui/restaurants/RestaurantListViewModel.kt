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


class RestaurantListViewModel : ViewModel() {

    private val repository: RestaurantsRepository = App.component.restaurantsRepository()
    private val restaurantsListLiveData = MutableLiveData<List<Restaurant>>()

    private fun loadList(currentSort: SortingCategory) {
        viewModelScope.launch(Dispatchers.Main) {
            val list = repository.loadJson()
            val sorted = currentSort.sortListDescending(list)
            restaurantsListLiveData.value = sorted
        }
    }

    fun restaurantList(): MutableLiveData<List<Restaurant>> {
        loadList(repository.currentSort)
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
        loadList(repository.currentSort)
    }
}
