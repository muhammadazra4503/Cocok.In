package com.dicoding.cocokin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cocokin.R
import com.dicoding.cocokin.data.dummy.ProductDummy
import com.dicoding.cocokin.databinding.ItemHomeBinding

class HomeAdapter(): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val data = mutableListOf<ProductDummy>()

    fun updateData(newData : List<ProductDummy>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(produk: ProductDummy) = with(binding) {
            ivProduct.setImageResource(produk.imageId)
            tvProduct.text = produk.namaProduk
            root.setOnClickListener {
                val message = root.context.getString(R.string.message, produk.namaProduk)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}