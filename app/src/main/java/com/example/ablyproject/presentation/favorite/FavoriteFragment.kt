package com.example.ablyproject.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ablyproject.databinding.FragmentFavoriteBinding
import com.example.ablyproject.databinding.FragmentHomeBinding
import com.example.ablyproject.di.ViewModelFactory
import com.example.ablyproject.presentation.home.HomeFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {

    private lateinit var binding : FragmentFavoriteBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val favoriteViewModel : FavoriteViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
    }

    companion object {
        fun newInstance() : FavoriteFragment = FavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}