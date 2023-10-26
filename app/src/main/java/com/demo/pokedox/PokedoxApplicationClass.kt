package com.demo.pokedox

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokedoxApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}