package com.example.ablyproject.presentation

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.example.ablyproject.R
import com.example.ablyproject.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var navigationController : NavigationController

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        setupBottomNavigation(savedInstanceState)
        forceReloadCurrentFragment()
    }


    private fun forceReloadCurrentFragment() {
        binding.bottomNavigation.apply{
            menu.findItem(selectedItemId)?.let {
                BottomNavigationItem.forId(it.itemId)
                    .navigate(navigationController)
            }
        }
    }

    /**
     * Bottom Navigation View 세팅 함수.
     */
    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
        binding.bottomNavigation.itemIconTintList = null

        setupToolbar(BottomNavigationItem.HOME) // toolbar 초기화.

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val navigationItem = BottomNavigationItem
                    .forId(item.itemId)
            setupToolbar(navigationItem)

            navigationItem.navigate(navigationController)
            true
        }

        binding.bottomNavigation.setOnNavigationItemReselectedListener { item ->
            val navigationItem = BottomNavigationItem
                    .forId(item.itemId)
            val fragment = supportFragmentManager.findFragmentByTag(navigationItem.name)
            if (fragment is BottomNavigationItem.OnReselectedListener) {
                fragment.onReselected()
            }
        }
    }

    private fun setupToolbar(navigationItem: BottomNavigationItem) {
        supportActionBar?.apply {
            title = getString(navigationItem.titleRes!!)
            setDisplayShowHomeEnabled(false)
            setIcon(null)
        }
    }


    // BottomNavigationView 의 변환을 위한 enum 클래스
    enum class BottomNavigationItem (
        @MenuRes val menuId: Int,
        @StringRes val titleRes: Int?,
        @DrawableRes val imageRes : Int?,
        val navigate: NavigationController.() -> Unit
    ) {
        HOME(R.id.navigation_home, R.string.bottom_nav_home, R.drawable.icon_home, {
            navigateToHome()
        }),
        FAVORITE(R.id.navigation_favorite, R.string.bottom_nav_favorite, R.drawable.icon_zzim, {
            navigateToFavorite()
        });

        companion object {
            fun forId(@IdRes id: Int): BottomNavigationItem {
                return values().first {it.menuId == id}
            }
        }

        interface OnReselectedListener {
            fun onReselected()
        }
    }
}