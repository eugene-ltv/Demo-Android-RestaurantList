package com.saiferwp.restaurantlist.data.model

class Restaurant {
    var name: String? = null
    var status: String? = null
    var sortingValues: SortingValues? = null

    class SortingValues {
        val bestMatch: Float = 0f
        val newest: Float = 0f
        val ratingAverage: Float = 0f
        val distance: Int = 0
        val popularity: Float = 0f
        val averageProductPrice: Int = 0
        val deliveryCosts: Int = 0
        val minCost: Int = 0
    }
}