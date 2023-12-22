package com.dicoding.cocokin.ui.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
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

        viewModel.getSession().observe(this) { user ->
            if (!user.isLoggedIn) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                // Update UI with user data, for example, display the user's display name
                supportActionBar?.title = "Hello, ${user.displayName}"
            }
        }

        setupView()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_cart -> {
                    replaceFragment(CartFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_predict -> {
                    replaceFragment(PredictFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
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

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null) // Add the transaction to the back stack
            .commit()
    }

    override fun onBackPressed() {
        // Check if there are fragments in the back stack
        if (supportFragmentManager.backStackEntryCount > 0) {
            // Pop the top fragment from the back stack
            supportFragmentManager.popBackStack()
        } else {
            // If there are no fragments in the back stack, proceed with the default back behavior
            super.onBackPressed()
        }
    }
}