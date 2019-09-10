package com.saiferwp.restaurantlist

import android.app.Application
import com.saiferwp.restaurantlist.di.AppComponent
import com.saiferwp.restaurantlist.di.ContextModule
import com.saiferwp.restaurantlist.di.DaggerAppComponent

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        component = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {
        lateinit var component: AppComponent private set
    }
}