package com.dicoding.cocokin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dicoding.cocokin.R
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import com.dicoding.cocokin.databinding.ItemCartBinding

class CartAdapter : ListAdapter<CartResponseItem, CartAdapter.CartViewHolder>(DiffCallback()) {

    private var onCheckBoxClickListener: ((CartResponseItem, Boolean) -> Unit)? = null

    fun setOnCheckBoxClickListener(listener: (CartResponseItem, Boolean) -> Unit) {
        onCheckBoxClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = getItem(position)
        holder.checkBox.isChecked = cart.isChecked
        Glide.with(holder.itemView.context)
            .load(cart.gambar)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivProduct)
        holder.tvProduct.text = cart.nama
        holder.tvPrice.text = String.format(holder.itemView.context.getString(R.string.harga), cart.harga)
        holder.noteEditText.setText(cart.catatan)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheckBoxClickListener?.invoke(cart, isChecked)
        }
    }

    class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivProduct = binding.ivProduct
        val noteEditText = binding.noteEditText
        val checkBox = binding.checkBox
        val tvProduct = binding.tvProduct
        val tvPrice = binding.tvPrice
    }

    private class DiffCallback : DiffUtil.ItemCallback<CartResponseItem>() {
        override fun areItemsTheSame(oldItem: CartResponseItem, newItem: CartResponseItem): Boolean {
            return oldItem.idkeranjang == newItem.idkeranjang
        }

        override fun areContentsTheSame(oldItem: CartResponseItem, newItem: CartResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}
