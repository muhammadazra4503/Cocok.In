package com.dicoding.cocokin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dicoding.cocokin.databinding.FragmentHomeBinding
import com.dicoding.cocokin.ui.viewmodel.HomeViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory
import com.dicoding.cocokin.ui.welcome.WelcomeActivity

class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutAction()
    }

    private fun logoutAction(){
        binding?.logoutButton?.setOnClickListener{
            viewModel.logout()
            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            requireActivity().finish()
        }
    }
}