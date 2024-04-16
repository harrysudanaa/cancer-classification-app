package com.dicoding.asclepius.view.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.helper.DateFormatterHelper
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.util.Date
import java.util.UUID

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            croppedImageUri = File(
                requireContext().filesDir,
                "croppedImage_" + UUID.randomUUID().toString() + ".jpg"
            ).toUri()
            val listUri = listOf(currentImageUri!!, croppedImageUri!!)
            showImage(listUri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val uCropContracts = object : ActivityResultContract<List<Uri>, Uri>() {
        override fun createIntent(context: Context, input: List<Uri>): Intent {
            val inputImageUri = input[0]
            val outputImageUri = input[1]

            val cropImage = UCrop.of(inputImageUri, outputImageUri)
                .withAspectRatio(0F, 0F)
                .withMaxResultSize(1200, 1200)

            return cropImage.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri {
            return UCrop.getOutput(intent!!)!!
        }

    }

    private val cropImage = registerForActivityResult(uCropContracts) { uri ->
        binding.previewImageView.setImageURI(uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navController = findNavController()
        binding.analyzeButton.setOnClickListener {
            navController.navigate(R.id.action_navigation_home_to_resultFragment)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }

        return root
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private fun showImage(listUri: List<Uri>) {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        cropImage.launch(listUri)
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    showToast("Success")
                    val classificationResultsList = mutableListOf<HistoryClassification>()
                    results?.let { it ->
                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                            val sortedCategories = it[0].categories.sortedByDescending { it?.score }
                            if (croppedImageUri != null) {
                                sortedCategories.forEach { category ->
                                    val newImage =
                                        activity?.contentResolver?.openInputStream(croppedImageUri!!)
                                            ?.readBytes()
                                    classificationResultsList.add(
                                        HistoryClassification(
                                            label = category.label,
                                            score = category.score,
                                            imageData = newImage!!,
                                            date = DateFormatterHelper.formatDate(Date())
                                        )
                                    )
                                }
                            }
                        } else {
                            showToast("No data")
                        }
                    }
                    moveToResult(classificationResultsList.toTypedArray(), inferenceTime)
                }
            })
        croppedImageUri?.let { imageClassifierHelper.classifyStaticImage(it) }
    }

    private fun moveToResult(results: Array<HistoryClassification>, inferenceTime: Long) {
        val action = HomeFragmentDirections.actionNavigationHomeToResultFragment(
            results,
            inferenceTime
        )
        findNavController().navigate(action)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}