package com.dicoding.cocokin.ui.predict

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.dicoding.cocokin.databinding.FragmentPredictBinding

class PredictFragment : Fragment() {
    private var _binding: FragmentPredictBinding? = null
    private val binding get() = _binding

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_FILE_PICK = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPredictBinding.inflate(layoutInflater, container, false)

        // Set onClickListener for Camera button
        binding?.button5?.setOnClickListener {
            dispatchTakePictureIntent()
        }

        // Set onClickListener for Upload button
        binding?.button4?.setOnClickListener {
            dispatchPickFileIntent()
        }

        return binding?.root
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: Exception) {
            // Handle exception (e.g., if the device doesn't have a camera)
        }
    }

    private fun dispatchPickFileIntent() {
        val pickFileIntent = Intent(Intent.ACTION_GET_CONTENT)
        pickFileIntent.type = "image/*"  // Set the MIME type you want to allow
        startActivityForResult(pickFileIntent, REQUEST_FILE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    // Now you can do something with the captured imageBitmap
                    binding?.imageView?.setImageBitmap(imageBitmap)
                }
                REQUEST_FILE_PICK -> {
                    // Handle the selected file, for example, display its path
                    val selectedFileUri = data?.data
                    // Process the selected file URI as needed
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
