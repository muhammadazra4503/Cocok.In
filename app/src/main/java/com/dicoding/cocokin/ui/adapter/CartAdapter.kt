package com.dicoding.cocokin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import com.dicoding.cocokin.databinding.ItemCartBinding

class CartAdapter : ListAdapter<CartResponseItem, CartAdapter.CartViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        } else {
            // Handle case when currentItem is null (cart is empty)
            // For example, show a message or hide the view
            holder.showEmptyCartMessage()
        }
    }

    class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartResponseItem) {
            binding.DescCart.text = cartItem.nama
            binding.HargaCart.text = cartItem.harga.toString()
            // Glide or Picasso can be used to load the image
            Glide.with(binding.ivCart.context).load(cartItem.gambar).into(binding.ivCart)
        }

        // Show a message or hide the view when the cart is empty
        fun showEmptyCartMessage() {
            binding.root.visibility = View.GONE
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<CartResponseItem>() {
        override fun areItemsTheSame(oldItem: CartResponseItem, newItem: CartResponseItem): Boolean {
            return oldItem.idKeranjang == newItem.idKeranjang
        }

        override fun areContentsTheSame(oldItem: CartResponseItem, newItem: CartResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}