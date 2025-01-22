package com.example.citysearchapp

import android.app.Application
import com.example.citysearchapp.data.AppContainer
import com.example.citysearchapp.data.DefaultAppContainer

class CitySearchApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}