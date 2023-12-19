package com.dicoding.cocokin.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dicoding.cocokin.R
import com.dicoding.cocokin.databinding.ActivityDetailBinding
import com.dicoding.cocokin.ui.viewmodel.DetailViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        if (productId != -1) {
            viewModel.fetchProductDetail(productId)
        } else {
            // Handle invalid product ID
            finish()
        }

        viewModel.productDetail.observe(this) { productDetail ->
            if (productDetail != null) {
                binding.tvProduct.text = productDetail.nama
                binding.tvPrice.text = String.format(getString(R.string.harga), productDetail.harga)
                binding.tvDescription.text = productDetail.deskripsi

                Glide.with(this)
                    .load(productDetail.gambar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivProduct)
            } else {
                Toast.makeText(this, "Product details not available", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.errorState.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage ?: "An unexpected error occurred", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
