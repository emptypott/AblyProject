package com.example.ablyproject.presentation.favorite

import androidx.fragment.app.Fragment
import com.example.ablyproject.presentation.home.HomeFragment
import dagger.android.support.DaggerFragment

class FavoriteFragment : DaggerFragment() {

    companion object {
        fun newInstance() : FavoriteFragment = FavoriteFragment()
    }
}