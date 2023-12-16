package com.dicoding.cocokin.ui.predict

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.cocokin.databinding.FragmentPredictBinding

class PredictFragment : Fragment() {
    private var _binding: FragmentPredictBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPredictBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    // You can add any additional logic or setup for the PredictFragment here
}