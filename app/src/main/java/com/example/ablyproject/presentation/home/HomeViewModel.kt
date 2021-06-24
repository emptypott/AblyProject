package com.example.ablyproject.presentation.home

import android.util.Log
import androidx.lifecycle.*
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.data.api.response.Response
import com.example.ablyproject.data.repository.HomeRepository
import com.example.ablyproject.presentation.Result
import com.example.ablyproject.presentation.toResult
import com.example.ablyproject.util.SchedulerProvider
import com.example.ablyproject.util.defaultErrorHandler
import io.github.droidkaigi.confsched2018.util.ext.switchMap
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val schedulerProvider: SchedulerProvider
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


    private val mutableRefreshState : MutableLiveData<Result<Unit>> = MutableLiveData()
    val refreshResult : LiveData<Result<Unit>> = mutableRefreshState

    private fun refreshHomeData() {
        repository.refreshHomeData().toResult<Unit>(schedulerProvider).subscribeBy(
            onNext =  {mutableRefreshState.value = it},
            onError = defaultErrorHandler()
        )
            .addTo(compositeDisposable)
    }
}