package com.example.ablyproject.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ablyproject.R
import com.example.ablyproject.data.api.response.Favorite
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.databinding.FragmentHomeBinding
import com.example.ablyproject.presentation.App
import com.example.ablyproject.presentation.MainActivity
import com.example.ablyproject.presentation.MainViewModel
import com.example.ablyproject.presentation.Result
import com.example.ablyproject.presentation.adapter.BannerVp2Adapter
import com.example.ablyproject.presentation.adapter.GoodRvAdapter
import com.example.ablyproject.presentation.adapter.GoodRvAdapter.*
import com.example.ablyproject.presentation.decoration.GoodRvDecoration
import com.example.ablyproject.util.Findable
import dagger.android.support.DaggerFragment
import io.github.droidkaigi.confsched2018.util.ext.observe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : DaggerFragment(), Findable {

    companion object {
        fun newInstance() : HomeFragment =
            HomeFragment()

        const val INTERVAL_TIME : Long  = 3000L

        const val GRID_COUNT_HOME : Int = 2
    }

    private lateinit var binding : FragmentHomeBinding
    private lateinit var bannerVp2Adapter : BannerVp2Adapter
    private lateinit var goodRvAdapter : GoodRvAdapter

    private var bannerSize : Int = 0

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private var favoriteGoods : MutableList<Favorite> = mutableListOf()

    private val mainViewModel : MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
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

        // swipeLayout 을 통하여 데이터 변경.
        binding.swipeLayout.setOnRefreshListener {
            mainViewModel.refreshHomeData()
            binding.swipeLayout.isRefreshing = false
        }

        bannerVp2Adapter = BannerVp2Adapter()
        binding.vp2Banners.adapter = bannerVp2Adapter

        goodRvAdapter = GoodRvAdapter()
        binding.rvHomeGoods.adapter = goodRvAdapter

        val goodDecoration = GoodRvDecoration(resources.getDimensionPixelSize(R.dimen.grid_decoration))
        binding.rvHomeGoods.addItemDecoration(goodDecoration)


        binding.nestedScrollview.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v!!.measuredHeight) {
                Log.d("jeongsu", "scrollcheck")
                mainViewModel.addGoodData(goodRvAdapter.itemCount)
            }
        }

        goodRvAdapter.setItemClickListener(object : ItemClickListener {
            override fun onClick(checked : Boolean, position: Int, good : Good) {
                if(checked) {
                    mainViewModel.saveId(good.id!!)
                    App.prefs.setBoolean(good.id!!.toString(), checked)
                } else {
                    mainViewModel.deleteFavoriteId(good.id!!)
                    App.prefs.setBoolean(good.id!!.toString(), checked)
                }
            }
        })

        val gridlayoutManager = GridLayoutManager(requireActivity(),
            GRID_COUNT_HOME
        )
        binding.rvHomeGoods.layoutManager = gridlayoutManager

        mainViewModel.banners.observe(this) {result ->
            when (result) {
                is Result.Success -> {
                    Log.d("jeongsuchoi", "banners result.data : " + result.data)

                    bannerVp2Adapter.setBanners(result.data)
                    binding.vp2Banners.setCurrentItem(vp2CurrentPosition, false) // 현재 위치를 지정합니다.

                    bannerSize = result.data.size
                    binding.tvCounter.text = "1" + " / " + result.data.size // 페이지 넘버 초기화.
                }

                is Result.Failure -> {
                    Log.d("jeongsuchoi", "banners result.data failure!!")
                }
            }
        }

        mainViewModel.goods.observe(this) {result ->
            when (result) {
                is Result.Success -> {
                    if (goodRvAdapter.itemCount != 0 && (goodRvAdapter.itemCount < result.data.size)) {

                        // 추가적인 데이터만 전달
                        val addList = result.data.subList(goodRvAdapter.itemCount, result.data.size)

                        // 기존 데이터의 마지막 데이터에 이어서 데이터 전달
                        goodRvAdapter.addGoods(addList.toMutableList())
                    } else {
                        goodRvAdapter.setGoods(result.data.toMutableList())
                    }

                    findingFavoriteData(favoriteGoods, result.data.toMutableList())

                }
                is Result.Failure -> {
                    Log.d("jeongsuchoi", "goods result.data failure!!")
                }
            }
        }

        mainViewModel.favorites.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    favoriteGoods = result.data.toMutableList()
                }
            }
        }

        mainViewModel.refreshResult.observe(this) {result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("jeongsuchoi", "failure!!")
                }
            }
        }

        // 자동 스크롤 되게 처리 (3초)
        val subscribe = Observable.interval(INTERVAL_TIME, TimeUnit.MILLISECONDS)
            .observeOn(
                AndroidSchedulers.mainThread())
            .subscribe {
                binding.vp2Banners.currentItem = binding.vp2Banners.currentItem + 1
                binding.tvCounter.text = "${binding.vp2Banners.currentItem%3 + 1}" + " / " + bannerSize
            }

        compositeDisposable.add(subscribe)

        lifecycle.addObserver(mainViewModel)
    }

    override val tagForBinding = MainActivity.BottomNavigationItem.HOME.name

    private fun findingFavoriteData(
        checkedList: MutableList<Favorite> ,
        totalList : MutableList<Good>){
        for (i in totalList) {
            val id  = i.id
            for (j in checkedList) {
                if (id == j.id) {
                    App.prefs.setBoolean(id!!.toString(), true)
                }
            }
        }
    }
}



