package com.saiferwp.restaurantlist.ui.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saiferwp.restaurantlist.R
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.data.model.SortingCategory
import kotterknife.bindView
import java.util.*

class RestaurantListAdapter(
    private val favoriteClicked: (Restaurant) -> (Unit)
) : RecyclerView.Adapter<RestaurantListAdapter.RestaurantTileHolder>() {

    private var items = emptyList<Restaurant>()
    private var filteredItems: List<Restaurant>? = null

    private var sortingCategory: SortingCategory? = null

    fun setData(
        items: List<Restaurant>,
        currentSortingCategory: SortingCategory
    ) {
        this.items = items
        sortingCategory = currentSortingCategory
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantTileHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RestaurantTileHolder(
            inflater.inflate(R.layout.restaurant_list_item, parent, false)
        )
    }

    override fun getItemCount() = filteredItems?.size ?: items.size

    override fun onBindViewHolder(holder: RestaurantTileHolder, position: Int) {
        holder.bind(filteredItems?.get(position) ?: items[position],
            sortingCategory,
            favoriteClicked
        )
    }

    fun filterByName(queryString: String) {
        val queryStringLowered = queryString.toLowerCase(Locale.ENGLISH)
        filteredItems = items.filter {
            it.name.toLowerCase(Locale.ENGLISH)?.contains(queryStringLowered) ?: false
        }
        notifyDataSetChanged()
    }

    fun clearFilter() {
        filteredItems = null
        notifyDataSetChanged()
    }

    class RestaurantTileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView by bindView(R.id.textView_restaurant_name)
        private val status: TextView by bindView(R.id.textView_restaurant_status)
        private val sortingValue: TextView by bindView(R.id.textView_restaurant_sorting_value)
        private val buttonFavorite: ImageView by bindView(R.id.button_favorite)

        fun bind(
            restaurant: Restaurant,
            sortingCategory: SortingCategory?,
            favoriteClicked: (Restaurant) -> (Unit)
        ) {
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

            buttonFavorite.isSelected = restaurant.favourire
            buttonFavorite.setOnClickListener {
                favoriteClicked.invoke(restaurant)
            }
        }
    }
}