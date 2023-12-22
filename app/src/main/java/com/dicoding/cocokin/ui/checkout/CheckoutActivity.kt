package com.dicoding.cocokin.ui.checkout


import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.cocokin.R
import com.dicoding.cocokin.data.Result
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import com.dicoding.cocokin.databinding.ActivityCheckoutBinding
import com.dicoding.cocokin.ui.main.HomeFragment
import com.dicoding.cocokin.ui.main.MainActivity
import com.dicoding.cocokin.ui.viewmodel.CheckoutViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory

class CheckoutActivity : AppCompatActivity() {

    private val viewModel by viewModels<CheckoutViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var button: Button
    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button = binding.btnPay

        val rootView: View = findViewById(android.R.id.content)

        // Set up a global layout listener on the root view
        rootView.viewTreeObserver.addOnGlobalLayoutListener { // Check if the keyboard is visible
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - r.bottom

            // If the keyboard is visible, hide the button; otherwise, show it
            if (keypadHeight > screenHeight * 0.15) {
                button.visibility = View.GONE
            } else {
                button.visibility = View.VISIBLE
            }
        }

        val selectedItems = intent.getSerializableExtra("selectedItems") as ArrayList<CartResponseItem>

        val tvProduct = binding.tvProduct
        val tvNote = binding.tvNote
        val tvPrice = binding.tvPrice

        // Display information for each selected item
        val productText = StringBuilder()
        val noteText = StringBuilder()
        var totalPrice = 0

        for (item in selectedItems) {
            productText.append("${item.nama}\n")
            noteText.append("${item.catatan}\n")
            totalPrice += item.harga
        }

        tvProduct.text = productText.toString()
        tvNote.text = noteText.toString()
        tvPrice.text = getString(R.string.harga, totalPrice)

        viewModel.paymentResult.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    // Payment successful, update local cart logic if needed
                    Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show()

                    val etAddress = binding.addressEditText.text.toString()
                    viewModel.processPayment(selectedItems, etAddress)

                    // Navigate back to the home activity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                is Result.Error -> {
                    // Payment failed, show an error message
                    Toast.makeText(
                        this,
                        "Payment failed: ${result.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
        // Set up a click listener for the "Pay Now" button
        button.setOnClickListener {
            val etAddress = binding.addressEditText.text.toString()
            viewModel.processPayment(selectedItems, etAddress)
        }
    }
}