package com.saiferwp.restaurantlist.ui.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saiferwp.restaurantlist.R
import com.saiferwp.restaurantlist.data.model.Restaurant
import kotterknife.bindView

class RestaurantListAdapter : RecyclerView.Adapter<RestaurantListAdapter.RestaurantTileHolder>() {

    fun setData(items: List<Restaurant>) {
        userIds = items
        notifyDataSetChanged()
    }

    private var userIds = emptyList<Restaurant>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantTileHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RestaurantTileHolder(
            inflater.inflate(R.layout.restaurant_list_item, parent, false)
        )
    }

    override fun getItemCount() = userIds.size

    override fun onBindViewHolder(holder: RestaurantTileHolder, position: Int) {
        holder.bind(userIds[position])
    }

    class RestaurantTileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView by bindView(R.id.textView_restaurant_name)
        private val status: TextView by bindView(R.id.textView_restaurant_status)

        fun bind(restaurant: Restaurant) {
            name.text = restaurant.name
            status.setText(restaurant.getStatus().titleIdx)

            val resources = itemView.resources
            status.setBackgroundColor(
                resources.getColor(
                    restaurant.getStatus().colorIdx
                )
            )
        }
    }
}