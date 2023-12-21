package com.dicoding.cocokin.ui.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.dicoding.cocokin.R
import com.dicoding.cocokin.databinding.ActivityMainBinding
import com.dicoding.cocokin.ui.cart.CartFragment
import com.dicoding.cocokin.ui.predict.PredictFragment
import com.dicoding.cocokin.ui.profile.ProfileFragment
import com.dicoding.cocokin.ui.viewmodel.MainViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory
import com.dicoding.cocokin.ui.welcome.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this){ user ->
            if(!user.isLogin){
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        setupView()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, HomeFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CartFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_predict -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, PredictFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, ProfileFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}