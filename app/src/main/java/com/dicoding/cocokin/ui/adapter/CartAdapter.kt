package com.dicoding.cocokin.ui.adapter

import android.text.Editable
import android.text.TextWatcher
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
    private var onDeleteClickListener: ((CartResponseItem) -> Unit)? = null

    fun setOnCheckBoxClickListener(listener: (CartResponseItem, Boolean) -> Unit) {
        onCheckBoxClickListener = listener
    }

    fun setOnDeleteClickListener(listener: (CartResponseItem) -> Unit) {
        onDeleteClickListener = listener
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
        // Update the noteEditText based on the cart item's catatan
        holder.noteEditText.text = Editable.Factory.getInstance().newEditable(cart.catatan.orEmpty())

        // Save the changes in the EditText to the corresponding CartResponseItem
        holder.noteEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update the catatan property of the corresponding CartResponseItem
                cart.catatan = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed
            }
        })


        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheckBoxClickListener?.invoke(cart, isChecked)
        }

        holder.deleteCart.setOnClickListener {
            onDeleteClickListener?.invoke(cart)
        }
    }

    class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivProduct = binding.ivProduct
        val noteEditText = binding.noteEditText
        val checkBox = binding.checkBox
        val tvProduct = binding.tvProduct
        val tvPrice = binding.tvPrice
        val deleteCart = binding.deleteCart
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
