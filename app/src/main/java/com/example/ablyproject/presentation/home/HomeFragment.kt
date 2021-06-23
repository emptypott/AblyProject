package com.example.ablyproject.presentation.home

import androidx.lifecycle.ViewModelProvider
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.databinding.FragmentHomeBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    companion object {
        fun newInstance() : HomeFragment = HomeFragment()
    }

    private lateinit var binding : FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val homeViewModel : HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

}