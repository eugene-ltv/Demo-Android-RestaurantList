package com.saiferwp.restaurantlist.ui.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saiferwp.restaurantlist.R
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.data.model.SortingCategory
import kotterknife.bindView

class RestaurantListAdapter : RecyclerView.Adapter<RestaurantListAdapter.RestaurantTileHolder>() {

    private var userIds = emptyList<Restaurant>()
    private var sortingCategory: SortingCategory? = null

    fun setData(
        items: List<Restaurant>,
        currentSortingCategory: SortingCategory
    ) {
        userIds = items
        sortingCategory = currentSortingCategory
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantTileHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RestaurantTileHolder(
            inflater.inflate(R.layout.restaurant_list_item, parent, false)
        )
    }

    override fun getItemCount() = userIds.size

    override fun onBindViewHolder(holder: RestaurantTileHolder, position: Int) {
        holder.bind(userIds[position], sortingCategory)
    }

    class RestaurantTileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView by bindView(R.id.textView_restaurant_name)
        private val status: TextView by bindView(R.id.textView_restaurant_status)
        private val sortingValue: TextView by bindView(R.id.textView_restaurant_sorting_value)

        fun bind(restaurant: Restaurant, sortingCategory: SortingCategory?) {
            name.text = restaurant.name
            status.setText(restaurant.getStatus().titleIdx)

            val resources = itemView.resources
            status.setBackgroundColor(
                resources.getColor(
                    restaurant.getStatus().colorIdx
                )
            )

            sortingCategory?.let {
                sortingValue.text = "${resources.getString(it.titleIdx)}: ${it.getSortValue(restaurant)}"
            }
        }
    }
}