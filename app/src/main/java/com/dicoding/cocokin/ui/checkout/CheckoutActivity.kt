package com.dicoding.cocokin.ui.checkout


import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import com.dicoding.cocokin.R
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import com.dicoding.cocokin.databinding.ActivityCheckoutBinding
import com.google.android.material.textfield.TextInputEditText

class CheckoutActivity : AppCompatActivity() {

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
        val etAddress = binding.addressEditText

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
    }
}