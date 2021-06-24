package com.example.ablyproject.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import javax.inject.Inject
import com.example.ablyproject.R
import com.example.ablyproject.presentation.favorite.FavoriteFragment
import com.example.ablyproject.presentation.home.HomeFragment
import com.example.ablyproject.util.Findable

class NavigationController @Inject constructor(private val activity: AppCompatActivity) {

    private val containerId : Int = R.id.root_cst_layout

    private val fragmentManager : FragmentManager = activity.supportFragmentManager

    fun navigateToHome() {
        replaceFragment(HomeFragment.newInstance())
    }

    fun navigateToFavorite() {
        replaceFragment(FavoriteFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = fragmentManager
                .beginTransaction()
                .replace(containerId, fragment, (fragment as? Findable)?.tagForBinding)

        if (fragmentManager.isStateSaved) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }
}