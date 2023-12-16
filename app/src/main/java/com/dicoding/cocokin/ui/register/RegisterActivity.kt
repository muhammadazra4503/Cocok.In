package com.dicoding.cocokin.ui.register

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.cocokin.data.Result
import com.dicoding.cocokin.databinding.ActivityRegisterBinding
import com.dicoding.cocokin.ui.viewmodel.RegisterViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please enter both email and password.")
            } else {
                showLoading(true)
                viewModel.register(name, email, password)
            }
        }

        viewModel.registerResult.observe(this) { result ->
            // Hide loading bar
            showLoading(false)
            when (result) {
                is Result.Success -> {
                    showRegisterAlertDialog(
                        "Success",
                        "Registration has been successful!",
                        true
                    )
                }
                is Result.Error -> {
                    showRegisterAlertDialog(
                        "Failed",
                        "Registration failed!",
                        false
                    )
                }
                else -> {
                }
            }
        }
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

    private fun showRegisterAlertDialog(title: String, message: String, isSuccess: Boolean) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            if (isSuccess) {
                setPositiveButton("Continue") { _, _ ->
                    finish()
                }
            } else {
                setPositiveButton("OK", null)
            }
            create()
            show()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            // Show loading bar
            binding.progressBar.visibility = View.VISIBLE
        } else {
            // Hide loading bar
            binding.progressBar.visibility = View.GONE
        }
    }
}
