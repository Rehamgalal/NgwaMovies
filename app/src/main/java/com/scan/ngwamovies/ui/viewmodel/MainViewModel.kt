package com.scan.ngwamovies.ui.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.annotation.RawRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.scan.ngwamovies.NagwaMovies
import com.scan.ngwamovies.R
import com.scan.ngwamovies.api.MoviesApi
import com.scan.ngwamovies.model.MediaItem
import com.scan.ngwamovies.utils.NetworkState
import io.reactivex.Flowable.intervalRange
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel(@NonNull application: Application) : AndroidViewModel(application) {


    private var liveDataList: MutableLiveData<List<MediaItem>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private var networkState: MutableLiveData<NetworkState> = MutableLiveData()
    private var downloadProcess: MutableLiveData<Long> = MutableLiveData()

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

                        if (it.message.equals("HTTP 429 ")) {
                            liveDataList.postValue(readRawJson(R.raw.getlistoffilesresponse))
                            networkState.postValue(NetworkState.LOADED)

                        } else {
                            networkState.postValue(NetworkState.error(it.message))
                        }
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
    fun getDownLoadProcess(): LiveData<Long> {
        return downloadProcess
    }

    fun downloadVideo() {
        compositeDisposable.add(intervalRange(0, 100, 60, 10, TimeUnit.MILLISECONDS,
            AndroidSchedulers.mainThread()).subscribe {
            downloadProcess.postValue(it)
            })
    }

    private inline fun <reified T> readRawJson(@RawRes rawResId: Int): T {
        val gson: Gson = GsonBuilder().create()
        getApplication<NagwaMovies>().resources.openRawResource(rawResId).bufferedReader().use {
            return gson.fromJson<T>(it, object: TypeToken<T>() {}.type)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}