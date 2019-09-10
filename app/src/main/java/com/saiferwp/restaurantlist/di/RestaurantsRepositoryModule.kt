package com.saiferwp.restaurantlist.di

import android.content.Context
import com.saiferwp.restaurantlist.data.RestaurantsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
open class RestaurantsRepositoryModule {
    @Provides
    @Singleton
    open fun provideRestaurantsRepository(
        context: Context
    ) = RestaurantsRepository(context)
}