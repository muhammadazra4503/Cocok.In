package com.dicoding.cocokin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.cocokin.databinding.FragmentHomeBinding
import com.dicoding.cocokin.ui.adapter.HomeAdapter
import com.dicoding.cocokin.ui.viewmodel.HomeViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()

        with(binding) {
            this?.searchView?.setupWithSearchBar(searchBar)
            this?.searchView
                ?.editText
                ?.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // Fetch product data with the entered search query
                        viewModel.fetchProductData()
                        searchView.hide()
                        true
                    } else {
                        false
                    }
                }
        }

        viewModel.productList.observe(viewLifecycleOwner) { productList ->
            // Update the adapter with the new product list
            adapter.submitList(productList)
        }

        viewModel.errorState.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch product data when the fragment is created
        viewModel.fetchProductData()
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(context, 2)
        binding?.rvHome?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding?.rvHome?.addItemDecoration(itemDecoration)
        adapter = HomeAdapter()
        binding?.rvHome?.adapter = adapter
    }
}