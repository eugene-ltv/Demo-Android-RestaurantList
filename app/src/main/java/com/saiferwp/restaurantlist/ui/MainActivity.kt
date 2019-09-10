package com.saiferwp.restaurantlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saiferwp.restaurantlist.R
import com.saiferwp.restaurantlist.ui.restaurants.RestaurantListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RestaurantListFragment.newInstance())
                .commitNow()
        }
    }

}
