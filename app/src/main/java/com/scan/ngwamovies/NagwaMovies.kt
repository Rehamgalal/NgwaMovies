package com.scan.ngwamovies

import android.app.Application
import com.example.newsapp.di.AppComponent
import com.example.newsapp.di.AppModule
import com.example.newsapp.di.DaggerAppComponent

class NagwaMovies : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}