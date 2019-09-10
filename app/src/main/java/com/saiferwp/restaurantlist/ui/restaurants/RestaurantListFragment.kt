package com.saiferwp.restaurantlist.ui.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saiferwp.restaurantlist.R
import kotterknife.bindView

class RestaurantListFragment : Fragment() {

    companion object {
        fun newInstance() = RestaurantListFragment()
    }

    private lateinit var viewModel: RestaurantListViewModel

    private val recyclerView: RecyclerView by bindView(R.id.recyclerView_restaurant_list)
    private val adapter: RestaurantListAdapter =
        RestaurantListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.restaurant_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RestaurantListViewModel::class.java)


        val list = viewModel.getRestaurantList()
        adapter.setData(list)
    }

}
