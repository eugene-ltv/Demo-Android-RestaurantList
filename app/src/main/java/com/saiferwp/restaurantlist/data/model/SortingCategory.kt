package com.saiferwp.restaurantlist.data.model

import com.saiferwp.restaurantlist.R

enum class SortingCategory(
    val titleIdx: Int
) {
    BEST_MATCH(R.string.sorting_category_best_match),
    NEWEST(R.string.sorting_category_newest),
    RATING_AVERAGE(R.string.sorting_category_rating_average),
    DISTANCE(R.string.sorting_category_distance),
    POPULARITY(R.string.sorting_category_popularity),
    AVERAGE_PRODUCT_PRICE(
        R.string.sorting_category_average_product_price
    ),
    DELIVERY_COSTS(R.string.sorting_category_average_delivery_costs),
    MIN_COST(R.string.sorting_category_min_cost);

    companion object {
        fun getDefault() = BEST_MATCH
    }

    fun getSortValue(restaurant: Restaurant): Any {
        return when (this) {
            BEST_MATCH -> restaurant.sortingValues?.bestMatch!!
            NEWEST -> restaurant.sortingValues?.newest!!
            RATING_AVERAGE -> restaurant.sortingValues?.ratingAverage!!
            DISTANCE -> restaurant.sortingValues?.distance!!
            POPULARITY -> restaurant.sortingValues?.popularity!!
            AVERAGE_PRODUCT_PRICE -> restaurant.sortingValues?.averageProductPrice!!
            DELIVERY_COSTS -> restaurant.sortingValues?.deliveryCosts!!
            MIN_COST -> restaurant.sortingValues?.minCost!!
        }
    }

    fun sortListDescending(restaurantsList: List<Restaurant>): List<Restaurant> {
        return when (this) {

            BEST_MATCH -> restaurantsList.sortedWith(
                compareBy<Restaurant> {
                    it.getStatus().ordinal
                }.thenByDescending { it.sortingValues?.bestMatch })

            NEWEST -> restaurantsList.sortedWith(
                compareBy<Restaurant> {
                    it.getStatus().ordinal
                }.thenByDescending { it.sortingValues?.newest })

            RATING_AVERAGE -> restaurantsList.sortedWith(
                compareBy<Restaurant> {
                    it.getStatus().ordinal
                }.thenByDescending { it.sortingValues?.ratingAverage })

            DISTANCE -> restaurantsList.sortedWith(
                compareBy<Restaurant> {
                    it.getStatus().ordinal
                }.thenBy { it.sortingValues?.distance })

            POPULARITY -> restaurantsList.sortedWith(
                compareBy<Restaurant> {
                    it.getStatus().ordinal
                }.thenByDescending { it.sortingValues?.popularity })

            AVERAGE_PRODUCT_PRICE -> restaurantsList.sortedWith(
                compareBy<Restaurant> {
                    it.getStatus().ordinal
                }.thenBy { it.sortingValues?.averageProductPrice })

            DELIVERY_COSTS -> restaurantsList.sortedWith(
                compareBy<Restaurant> {
                    it.getStatus().ordinal
                }.thenBy { it.sortingValues?.deliveryCosts })

            MIN_COST -> restaurantsList.sortedWith(
                compareBy<Restaurant> {
                    it.getStatus().ordinal
                }.thenBy { it.sortingValues?.minCost })
        }
    }
}