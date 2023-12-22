package com.dicoding.cocokin.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dicoding.cocokin.data.ResultPayment
import com.dicoding.cocokin.databinding.FragmentProfileBinding
import com.dicoding.cocokin.ui.viewmodel.ProfileViewModel
import com.dicoding.cocokin.ui.viewmodel.ViewModelFactory
import com.dicoding.cocokin.ui.welcome.WelcomeActivity

class ProfileFragment : Fragment() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userSession.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                // Update UI with user data
                binding?.tvName?.text = it.displayName
                binding?.tvEmail?.text = it.email
            }
        })

        viewModel.historyResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultPayment.SuccessWithData -> {
                    // Handle the successful result, update UI with payment history if needed
                    val historyStringBuilder = StringBuilder()
                    result.data.forEach { historyItem ->
                        historyStringBuilder.append("${historyItem.nama} - ${historyItem.totalharga}\n")
                    }
                    binding?.tvHistory?.text = historyStringBuilder.toString()
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.labelHistory?.visibility = View.VISIBLE
                }
                is ResultPayment.Error -> {
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        })

        viewModel.fetchHistory()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}