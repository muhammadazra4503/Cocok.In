package com.dicoding.cocokin.ui.main
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.dicoding.cocokin.databinding.FragmentCartBinding
//import com.dicoding.cocokin.ui.adapter.CartAdapter
//import com.dicoding.cocokin.ui.viewmodel.CartViewModel
//import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory
//
//class CartFragment : Fragment() {
//    private val viewModel by viewModels<CartViewModel> {
//        ViewModelFactory.getInstance(requireContext())
//    }
//    private var _binding: FragmentCartBinding? = null
//    private val binding get() = _binding
//    private lateinit var adapter: CartAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)
//
//        // Periksa apakah binding tidak null sebelum menggunakannya
//        binding?.let {
//            setupRecyclerView()
//
//            viewModel.cartList.observe(viewLifecycleOwner) { cartList ->
//                // ...
//            }
//
//            viewModel.errorState.observe(viewLifecycleOwner) { errorMessage ->
//                // ...
//            }
//        } ?: run {
//            // Jika binding null, kembalikan null dari onCreateView
//            return null
//        }
//
//        return binding?.root
//    }
//
//
//
//    private fun setupRecyclerView() {
//        adapter = CartAdapter()
//        binding?.rvCart?.layoutManager = LinearLayoutManager(context)
//        binding?.rvCart?.adapter = adapter
//    }
//
//}