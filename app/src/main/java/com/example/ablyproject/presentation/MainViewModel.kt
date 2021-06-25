package com.example.ablyproject.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Favorite
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.data.repository.HomeRepository
import com.example.ablyproject.util.SchedulerProvider
import com.example.ablyproject.util.defaultErrorHandler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val schedulerProvider: SchedulerProvider,
    private val context : Context
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        refreshHomeData()

    }

    val banners : LiveData<Result<List<Banner>>> by lazy {
        repository.banners.toResult(schedulerProvider).toLiveData()
    }

    val goods : LiveData<Result<List<Good>>> by lazy {
        repository.goods.toResult(schedulerProvider).toLiveData()
    }

    val favorites : LiveData<Result<List<Favorite>>> by lazy {
        repository.favorites.toResult(schedulerProvider).toLiveData()
    }

    private val mutableRefreshState : MutableLiveData<Result<Unit>> = MutableLiveData()
    val refreshResult : LiveData<Result<Unit>> = mutableRefreshState

    fun refreshHomeData() {
        repository.refreshHomeData().toResult<Unit>(schedulerProvider).subscribeBy(
            onNext =  {mutableRefreshState.value = it},
            onError = defaultErrorHandler()
        )
            .addTo(compositeDisposable)
    }

    fun addGoodData(lastId : Int) {
        repository.addGoodData(lastId).toResult<Unit>(schedulerProvider).subscribeBy(
            onNext = {mutableRefreshState.value = it},
            onError = defaultErrorHandler()
        )
            .addTo(compositeDisposable)
    }

    fun saveId(favoriteId: Int) {
        repository.saveId(favoriteId)
    }

    fun deleteFavoriteId(favoriteId: Int) {
        repository.deleteId(favoriteId)
    }
}