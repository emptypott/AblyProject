package com.example.ablyproject.presentation.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.databinding.FragmentHomeBinding
import com.example.ablyproject.databinding.ItemHomeBannerBinding
import com.example.ablyproject.presentation.MainActivity
import com.example.ablyproject.presentation.Result
import com.example.ablyproject.presentation.adapter.ViewPager2Adapter
import com.example.ablyproject.util.AppSchedulerProvider
import com.example.ablyproject.util.Findable
import com.example.ablyproject.util.SchedulerProvider
import dagger.android.support.DaggerFragment
import io.github.droidkaigi.confsched2018.util.ext.observe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HomeFragment : DaggerFragment(), Findable {

    private val interval  = 3000L

    companion object {
        fun newInstance() : HomeFragment = HomeFragment()
    }

    private lateinit var binding : FragmentHomeBinding
    private lateinit var vp2Adapter : ViewPager2Adapter

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val homeViewModel : HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private var vp2CurrentPosition = Int.MAX_VALUE / 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.banners.observe(this) {result ->
            when (result) {
                is Result.Success -> {

                    vp2Adapter = ViewPager2Adapter()
                    binding.vp2Banners.adapter = vp2Adapter

                    Log.d("jeongsuchoi", "banners result.data : " + result.data)

                    vp2Adapter.setBanners(result.data)
                    binding.vp2Banners.setCurrentItem(vp2CurrentPosition, false) // 현재 위치를 지정합니다.
                    binding.tvCounter.text = "1" + " / " + result.data.size // 페이지 넘버 초기화.

                }

                is Result.Failure -> {
                    Log.d("jeongsuchoi", "banners result.data failure!!")
                }
            }
        }

        homeViewModel.goods.observe(this) {result ->
            when (result) {
                is Result.Success -> {
                    Log.d("jeongsuchoi", "goods result.data : " + result.data)
                }
                is Result.Failure -> {
                    Log.d("jeongsuchoi", "goods result.data failre!!")
                }
            }
        }

        homeViewModel.refreshResult.observe(this) {result ->
            when (result) {
                is Result.Success -> {
                    Log.d("jeongsuchoi", "success!!")
                }

                is Result.Failure -> {
                    Log.d("jeongsuchoi", "failure!!")
                }
            }
        }


        // 자동 스크롤 되게 처리 (cf. 3초)
        val subscribe = Observable.interval(interval, TimeUnit.MILLISECONDS)
            .observeOn(
                AndroidSchedulers.mainThread())
            .subscribe {
                binding.vp2Banners.currentItem = binding.vp2Banners.currentItem + 1
                binding.tvCounter.text = "${binding.vp2Banners.currentItem%3 + 1}" + " / " + "3"
            }

        compositeDisposable.add(subscribe)

        lifecycle.addObserver(homeViewModel)
    }

    override val tagForBinding = MainActivity.BottomNavigationItem.HOME.name

}