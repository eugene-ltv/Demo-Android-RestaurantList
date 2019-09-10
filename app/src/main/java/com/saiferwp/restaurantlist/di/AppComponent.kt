package com.saiferwp.restaurantlist.di


import com.saiferwp.restaurantlist.data.RestaurantsRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RestaurantsRepositoryModule::class
    ]
)
interface AppComponent {
    fun restaurantsRepository(): RestaurantsRepository
}
