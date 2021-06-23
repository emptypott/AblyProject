package com.example.ablyproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import com.example.ablyproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject lateinit var navigationController : NavigationController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        FAVORITE(R.id.navigation_home, R.id.navigation_favorite, R.drawable.icon_zzim, {
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