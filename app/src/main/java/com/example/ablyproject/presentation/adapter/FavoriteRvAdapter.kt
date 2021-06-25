package com.example.ablyproject.presentation.adapter

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.databinding.ItemHomeGoodsBinding
import com.example.ablyproject.di.GlideApp
import java.text.DecimalFormat

class FavoriteRvAdapter : RecyclerView.Adapter<FavoriteRvAdapter.FavoriteItemViewHolder>() {

    var goodList : MutableList<Good> = mutableListOf()

    interface ItemClickListener {
        fun onClick(view: View, position: Int, good : Good)
    }
    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener : ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteItemViewHolder {
        return FavoriteItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return goodList.size
    }

    override fun onBindViewHolder(holder: FavoriteItemViewHolder, position: Int) {
        if (goodList.size <= 0) return
        val good = goodList[position]
        holder.bind(good, itemClickListener)
    }

    fun setGoods(goods: MutableList<Good>) {
        goodList.clear()
        goodList.addAll(goods)
        notifyDataSetChanged()
    }

    class FavoriteItemViewHolder(private val binding: ItemHomeGoodsBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent : ViewGroup) : FavoriteItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeGoodsBinding.inflate(layoutInflater, parent, false)
                return FavoriteItemViewHolder(binding)
            }
        }

        fun bind(item: Good, itemClickListener: ItemClickListener) {

            // 좋아요 되어있는 상태이므로 모두 active
            binding.ivFavorite.clickedDrawable()

            binding.ivFavorite.setOnClickListener {
                itemClickListener.onClick(it, adapterPosition, item)
            }


            GlideApp.with(binding.root).asDrawable()
                .load(item.image)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        binding.ivItemGoods.setImageDrawable(resource)
                        if (resource is Animatable) {
                            (resource as Animatable).start()
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            // 할인율 binding.
            val disCountPrice = item.actualPrice!! - item.price!!
            if (disCountPrice > 0) {
                val percentage = (disCountPrice.toDouble() / (item.actualPrice!!)) * 100
                binding.tvPercentage.text = percentage.toInt().toString() + "%"
            } else {
                binding.tvPercentage.visibility = View.GONE
            }

            // 할인가격 바인딩
            val dec = DecimalFormat("#,###")
            val decimalFormatPrice = dec.format(item.price)
            binding.tvPrice.text = decimalFormatPrice + "원"

            // 상품명 binding.
            binding.tvPrdName.text = item.name

            // NEW 이미지 노출 공개
            binding.ivBadgeNew.visibility = if (item.isNew!!) View.VISIBLE else View.GONE

            // 구매중 binding
            val decimalFormatSellCount = dec.format(item.sellCount)
            binding.tvNowPurchasing.text = decimalFormatSellCount + "개 구매중"
        }
    }
}