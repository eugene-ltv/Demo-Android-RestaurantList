package com.saiferwp.restaurantlist

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.ui.restaurants.RestaurantListAdapter
import com.saiferwp.restaurantlist.ui.restaurants.RestaurantListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class RestaurantFavouriteTests {

    @Test
    fun testMakeFirstRestaurantFavorite() {
        val scenario = launchFragmentInContainer(Bundle(), R.style.AppTheme) {
            RestaurantListFragment()
        }
        scenario.moveToState(Lifecycle.State.RESUMED)

        val idx = 0 //first
        var first: Restaurant? = null
        var restaurantName: String? = null
        scenario.onFragment { fragment ->
            val currentList = fragment.adapter.items
            assertTrue("Restaurants list should not be empty", currentList.isNotEmpty())

            first = currentList[idx]
            restaurantName = first?.name
        }

        // Restore later if tested item is already favorite
        val firstIsFavorite = first?.favourire
        if (firstIsFavorite!!) {
            onView(withId(R.id.recyclerView_restaurant_list))
                .perform(
                    clickFavoriteAction(idx, R.id.button_favorite)
                )
        }

        sleep(50)

        onView(withId(R.id.recyclerView_restaurant_list))
            .perform(
                clickFavoriteAction(idx, R.id.button_favorite)
            )

        sleep(50)

        scenario.onFragment { fragment ->
            runBlocking {
                withContext(Dispatchers.IO) {
                    val list = fragment.viewModel.repository.getFavoriteRestaurants()
                    assertTrue(
                        "List must contain favorited item",
                        list.firstOrNull { it.name == restaurantName } != null
                    )
                }
            }
        }

        sleep(50)

        onView(withId(R.id.recyclerView_restaurant_list))
            .check(isImageViewChecked(idx, R.id.button_favorite))

        sleep(50)

        onView(withId(R.id.recyclerView_restaurant_list))
            .perform(
                clickFavoriteAction(idx, R.id.button_favorite)
            )

        sleep(50)

        scenario.onFragment { fragment ->
            runBlocking {
                withContext(Dispatchers.IO) {
                    val list = fragment.viewModel.repository.getFavoriteRestaurants()
                    assertTrue(
                        "Not excluded from favorites",
                        list.firstOrNull { it.name == restaurantName } == null
                    )
                }
            }
        }

        if (firstIsFavorite) {
            onView(withId(R.id.recyclerView_restaurant_list))
                .perform(
                    clickFavoriteAction(idx, R.id.button_favorite)
                )
        }
    }
}

private fun clickFavoriteAction(position: Int, @IdRes id: Int): ViewAction? {
    return RecyclerViewActions.actionOnItemAtPosition<RestaurantListAdapter.RestaurantTileHolder>(
        position,
        object : ViewAction {
            override fun getDescription(): String {
                return "Click on Favorite button"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(
                    isAssignableFrom(ImageView::class.java)
                )
            }

            override fun perform(uiController: UiController?, view: View?) {
                val button = view?.findViewById<ImageView>(id)
                button?.performClick()
            }
        }
    )
}

private fun isImageViewChecked(position: Int, @IdRes id: Int): ViewAssertion {
    return ViewAssertion { view, exception ->
        if (view !is RecyclerView) {
            throw exception
        }

        val itemView =
            view.findViewHolderForAdapterPosition(position)!!
                .itemView.findViewById<ImageView>(id)

        assertTrue(
            itemView.isSelected
        )
    }
}
