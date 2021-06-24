package com.example.ablyproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.databinding.ItemHomeBannerBinding

class ViewPager2Adapter() : RecyclerView.Adapter<ViewPager2Adapter.ImageViewHolder>() {

    var bannerList = ArrayList<Banner>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val banner = bannerList[position%3]
        holder.bind(banner)
    }

    fun setBanners(banners: List<Banner>) {
        bannerList = banners as ArrayList<Banner>
        notifyDataSetChanged()
    }

    class ImageViewHolder private constructor(private val binding : ItemHomeBannerBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent : ViewGroup) : ImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeBannerBinding.inflate(layoutInflater, parent, false)
                return ImageViewHolder(binding)
            }
        }

        fun bind(item: Banner) {
            Glide.with(binding.root).load(item.image).into(binding.ivBannerImage)
        }

    }
}