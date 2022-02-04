package com.scan.ngwamovies.ui.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scan.ngwamovies.NagwaMovies
import com.scan.ngwamovies.api.MoviesApi
import com.scan.ngwamovies.model.MediaItem
import com.scan.ngwamovies.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel(@NonNull application: Application) : AndroidViewModel(application) {


    private var liveDataList: MutableLiveData<List<MediaItem>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private var networkState: MutableLiveData<NetworkState> = MutableLiveData()

    @Inject
    lateinit var mService: MoviesApi

    init {
        (application as NagwaMovies).getAppComponent().inject(this)
        makeApiCall()
    }

    private fun makeApiCall(
    ): MutableLiveData<List<MediaItem>> {
        this.networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            mService.getMovies().subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        liveDataList.postValue(it)
                        networkState.postValue(NetworkState.LOADED)
                    }, {
                        networkState.postValue(NetworkState.error(it.message))
                    }
                )
        )
        return liveDataList
    }

    fun getRecyclerListObserver(): LiveData<List<MediaItem>> {
        return liveDataList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}