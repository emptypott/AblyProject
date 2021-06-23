package com.example.ablyproject.presentation.home

import androidx.lifecycle.ViewModel
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.data.repository.HomeRepository
import com.example.ablyproject.util.SchedulerProvider
import com.example.ablyproject.util.defaultErrorHandler
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {


    fun onFavoriteClick(good : Good) {

    }

}