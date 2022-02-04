package com.example.newsapp.di

import com.scan.ngwamovies.ui.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
}