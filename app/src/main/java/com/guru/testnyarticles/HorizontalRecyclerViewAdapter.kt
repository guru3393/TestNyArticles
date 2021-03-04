package com.guru.testnyarticles

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.guru.testnyarticles.databinding.HorizontalRecyclerviewRowBinding

class HorizontalRecyclerViewAdapter: RecyclerView.Adapter<HorizontalRecyclerViewAdapter.MyViewHolder>() {
    var items = ArrayList<HorizontalRecyclerData>()

    fun setDataList(data :  ArrayList<HorizontalRecyclerData>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalRecyclerViewAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = com.guru.testnyarticles.databinding.HorizontalRecyclerviewRowBinding.inflate(layoutInflater)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class MyViewHolder(val binding: HorizontalRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HorizontalRecyclerData) {
            binding.horizontalrecyclerData = data
            binding.executePendingBindings()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(thubmImage: ImageView, url: String) {

            Glide.with(thubmImage)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(thubmImage)


        }



    }


}