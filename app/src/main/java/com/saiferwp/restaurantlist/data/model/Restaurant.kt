package com.saiferwp.restaurantlist.data.model

import com.saiferwp.restaurantlist.R

class Restaurant {
    var name: String? = null
    private var status: String? = null
    var sortingValues: SortingValues? = null

    enum class Status(val id: String, val titleIdx: Int, val colorIdx: Int) {
        OPEN("open", R.string.restaurant_status_open, R.color.restaurant_status_open),
        ORDER_AHEAD(
            "order ahead",
            R.string.restaurant_status_order_ahead,
            R.color.restaurant_status_order_ahead
        ),
        CLOSED("closed", R.string.restaurant_status_closed, R.color.restaurant_status_closed)
    }

    fun getStatus(): Status {
        return Status.values().find { it.id == status } ?: Status.OPEN
    }

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