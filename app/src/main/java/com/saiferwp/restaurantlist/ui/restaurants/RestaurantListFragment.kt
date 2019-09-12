package com.saiferwp.restaurantlist.ui.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saiferwp.restaurantlist.R
import com.saiferwp.restaurantlist.data.model.Restaurant
import com.saiferwp.restaurantlist.data.model.SortingCategory
import com.saiferwp.restaurantlist.misc.onItemSelected
import kotterknife.bindView


class RestaurantListFragment : Fragment() {

    companion object {
        fun newInstance() = RestaurantListFragment()
    }

    private lateinit var viewModel: RestaurantListViewModel

    private val recyclerView: RecyclerView by bindView(R.id.recyclerView_restaurant_list)
    private val adapter: RestaurantListAdapter =
        RestaurantListAdapter(::favouriteClicked)

    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val spinnerSortingCategory: Spinner by bindView(R.id.spinner_sorting_category)

    private val searchView: SearchView by bindView(R.id.searchView_filter_by_name)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.restaurant_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        spinnerSortingCategory.adapter = createSortingCategoriesSpinnerAdapter()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.clearFilter()
                } else {
                    adapter.filterByName(newText)
                }
                return true
            }
        })
        searchView.setOnCloseListener {
            adapter.clearFilter()
            searchView.clearFocus()
            true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RestaurantListViewModel::class.java)

        viewModel.restaurantList()
            .observe(this, Observer { list ->
                adapter.setData(list, viewModel.getCurrentSortingCategory())
            })

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        spinnerSortingCategory.onItemSelectedListener = null
        spinnerSortingCategory.setSelection(viewModel.getSortOptionPosition(), false)
        spinnerSortingCategory.onItemSelected { position: Int ->
            viewModel.setCurrentSortingCategory(position)
        }
    }

    private fun createSortingCategoriesSpinnerAdapter(): ArrayAdapter<String> {
        val categories = SortingCategory.values().asList()
        val categoriesTitles = categories.map { resources.getString(it.titleIdx) }

        val adapter: ArrayAdapter<String> = object :
            ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_item_sorting_category,
                categoriesTitles
            ) {
            override fun getItem(position: Int): String? {
                return categoriesTitles[position]
            }
        }
        adapter.setDropDownViewResource(R.layout.spinner_item_sorting_category)
        return adapter
    }

    private fun favouriteClicked(restaurant: Restaurant) {
        viewModel.favouriteClicked(restaurant)
    }
}
