package com.dicoding.cocokin.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.cocokin.data.LoginResult
import com.dicoding.cocokin.databinding.ActivityLoginBinding
import com.dicoding.cocokin.ui.MainActivity
import com.dicoding.cocokin.ui.viewmodel.LoginViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please enter both email and password.")
            } else {
                showLoading(true)
                viewModel.login(email, password)
            }
        }

        viewModel.loginResult.observe(this) { result ->
            // Hide loading bar
            showLoading(false)
            when (result) {
                is LoginResult.Success -> {
                    showLoginAlertDialog(
                        "Success",
                        "Login telah berhasil!",
                        true
                    )
                }
                is LoginResult.Error -> {
                    showLoginAlertDialog(
                        "Failed",
                        "Login gagal!",
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

    private fun showLoginAlertDialog(title: String, message: String, isSuccess: Boolean) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            if (isSuccess) {
                setPositiveButton("Lanjut") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
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
