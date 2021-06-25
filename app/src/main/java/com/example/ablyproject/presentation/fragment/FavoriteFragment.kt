package com.example.ablyproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ablyproject.R
import com.example.ablyproject.data.api.response.Favorite
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.databinding.FragmentFavoriteBinding
import com.example.ablyproject.presentation.App
import com.example.ablyproject.presentation.MainViewModel
import com.example.ablyproject.presentation.Result
import com.example.ablyproject.presentation.adapter.FavoriteRvAdapter
import com.example.ablyproject.presentation.decoration.GoodRvDecoration

import dagger.android.support.DaggerFragment
import io.github.droidkaigi.confsched2018.util.ext.observe
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {

    companion object {
        fun newInstance() : FavoriteFragment =
            FavoriteFragment()

        const val GRID_COUNT_FAVORITE : Int = 2
    }

    private var checkFirst : Boolean = true

    private lateinit var binding : FragmentFavoriteBinding
    private lateinit var favoriteRvAdapter : FavoriteRvAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var favoriteGoods : MutableList<Favorite> = mutableListOf()

    private var allGoods : MutableList<Good> = mutableListOf()

    private val mainViewModel : MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val goodsForCheck = mainViewModel.goods
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = false
        }

        val gridlayoutManager = GridLayoutManager(requireActivity(),
            GRID_COUNT_FAVORITE
        )
        binding.rvFavoriteGoods.layoutManager = gridlayoutManager

        val goodDecoration = GoodRvDecoration(resources.getDimensionPixelSize(R.dimen.grid_decoration))
        binding.rvFavoriteGoods.addItemDecoration(goodDecoration)

        favoriteRvAdapter = FavoriteRvAdapter()
        binding.rvFavoriteGoods.adapter = favoriteRvAdapter

        favoriteRvAdapter.setItemClickListener(object : FavoriteRvAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, good: Good) {
                mainViewModel.deleteFavoriteId(good.id!!)

                App.prefs.setBoolean(good.id!!.toString(), false)
                if (favoriteGoods.size <= 1) {
                    binding.rvFavoriteGoods.removeAllViewsInLayout()
                } else {
                    favoriteRvAdapter.notifyDataSetChanged()
                }
            }
        })

        mainViewModel.favorites.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    favoriteGoods = result.data.toMutableList()
                    favoriteRvAdapter.setGoods(bindingFavoritedData(favoriteGoods, allGoods))
                }
            }
        }

        mainViewModel.goods.observe(this) {result ->
            when (result) {
                is Result.Success -> {
                    allGoods = result.data.toMutableList()
                    favoriteRvAdapter.setGoods(bindingFavoritedData(favoriteGoods, allGoods))
                }
            }
        }
    }

    private fun bindingFavoritedData(
        checkedList: MutableList<Favorite> ,
        totalList : MutableList<Good>) : MutableList<Good> {
        val favoriteList : MutableList<Good> = mutableListOf()
        for (i in totalList) {
            val id  = i.id
            for (j in checkedList) {
                if (id == j.id) {
                    favoriteList.add(i)
                }
            }
        }
        return favoriteList
    }
}