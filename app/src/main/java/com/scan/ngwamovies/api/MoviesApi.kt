package com.scan.ngwamovies.api

import com.scan.ngwamovies.model.MediaItem
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movies")
     fun getMovies():MediaItem
}