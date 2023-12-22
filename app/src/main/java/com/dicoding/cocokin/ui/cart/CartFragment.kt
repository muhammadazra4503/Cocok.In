package com.dicoding.cocokin.ui.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cocokin.data.remote.response.CartResponseItem
import com.dicoding.cocokin.databinding.FragmentCartBinding
import com.dicoding.cocokin.ui.adapter.CartAdapter
import com.dicoding.cocokin.ui.checkout.CheckoutActivity
import com.dicoding.cocokin.ui.viewmodel.CartViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory

class CartFragment : Fragment() {
    private val viewModel by viewModels<CartViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        viewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            // Update the adapter when cartItems change
            adapter.submitList(cartItems)
        }

        viewModel.errorState.observe(viewLifecycleOwner) { error ->
            // Handle error state if needed
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnCheckout?.setOnClickListener {
            val selectedItems = viewModel.getSelectedItems()
            if (selectedItems.isNotEmpty()) {
                // Navigate to CheckoutActivity with selected items
                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                intent.putExtra("selectedItems", ArrayList(selectedItems))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Please select items before checkout", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.fetchCart()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding?.rvCart?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding?.rvCart?.addItemDecoration(itemDecoration)
        adapter = CartAdapter()
        adapter.setOnCheckBoxClickListener { cartItem, isChecked ->
            // Handle checkbox click, e.g., update the ViewModel
            viewModel.updateCartItemState(cartItem, isChecked)
        }
        adapter.setOnDeleteClickListener { cartItem ->
            // Handle delete action, e.g., call the deleteCartItem function in the ViewModel
            viewModel.deleteCartItem(cartItem.idkeranjang.toString())
        }
        binding?.rvCart?.adapter = adapter
    }
}