package com.dicoding.cocokin.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dicoding.cocokin.R
import com.dicoding.cocokin.databinding.FragmentHomeBinding
import com.dicoding.cocokin.databinding.FragmentProfileBinding
import com.dicoding.cocokin.ui.viewmodel.ProfileViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory
import com.dicoding.cocokin.ui.welcome.WelcomeActivity

class ProfileFragment : Fragment() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.logoutButton?.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        viewModel.logout()
        val intent = Intent(requireContext(), WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        activity?.finish()
    }
}