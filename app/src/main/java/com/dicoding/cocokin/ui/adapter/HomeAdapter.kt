package com.dicoding.cocokin.ui.adapter

import android.content.Intent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dicoding.cocokin.R
import com.dicoding.cocokin.data.remote.response.ProductResponseItem
import com.dicoding.cocokin.databinding.ItemHomeBinding
import com.dicoding.cocokin.ui.detail.DetailActivity
import java.util.Locale


class HomeAdapter : ListAdapter<ProductResponseItem, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivProduct = binding.ivProduct
        val tvProduct = binding.tvProduct
        val tvPrice = binding.tvPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = getItem(position)

        // Set product data to views
        holder.tvProduct.text = product.nama
        holder.tvPrice.text = String.format(holder.itemView.context.getString(R.string.harga), product.harga)

        // Load image using Glide or any other image loading library
        Glide.with(holder.itemView.context)
            .load(product.gambar)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivProduct)

        // Set click listener if needed
        holder.itemView.setOnClickListener {
            // Handle item click
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductResponseItem>() {
            override fun areItemsTheSame(oldItem: ProductResponseItem, newItem: ProductResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductResponseItem, newItem: ProductResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

