package com.example.ablyproject.presentation.favorite

import androidx.lifecycle.ViewModel
import com.example.ablyproject.data.repository.HomeDataRepository
import com.example.ablyproject.util.SchedulerProvider
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val repository: HomeDataRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel()   {

}