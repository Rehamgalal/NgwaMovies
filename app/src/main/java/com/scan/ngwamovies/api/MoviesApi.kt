package com.scan.ngwamovies.api

import com.scan.ngwamovies.model.MediaItem
import io.reactivex.Single
import retrofit2.http.GET

interface MoviesApi {

    @GET("movies/")
     fun getMovies():Single<List<MediaItem>>
}