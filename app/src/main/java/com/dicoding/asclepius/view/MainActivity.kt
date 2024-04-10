package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.data.ClassificationResults
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            analyzeImage()
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        imageClassifierHelper = ImageClassifierHelper(context = this, classifierListener = object : ImageClassifierHelper.ClassifierListener {
            override fun onError(error: String) {
                showToast(error)
            }

            override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                showToast("Berhasil")
                val classificationResultsList = mutableListOf<ClassificationResults>()
                results?.let { it ->
                    if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                        println(it)
                        val sortedCategories = it[0].categories.sortedByDescending { it?.score }
                        sortedCategories.forEach { category ->
                            classificationResultsList.add(ClassificationResults(category.label, category.score))
                        }
                    } else {
                        showToast("No data")
                    }
                }
                moveToResult(ArrayList(classificationResultsList), inferenceTime)
            }
        })
        currentImageUri?.let { imageClassifierHelper.classifyStaticImage(it) }
    }

    private fun moveToResult(results: ArrayList<ClassificationResults>?, inferenceTime: Long) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putParcelableArrayListExtra(ResultActivity.EXTRA_CLASSIFICATION_RESULT, results)
        intent.putExtra(ResultActivity.EXTRA_INFERENCE_TIME, inferenceTime)
        if (currentImageUri != null) {
            intent.putExtra(ResultActivity.EXTRA_IMAGE, currentImageUri.toString())
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}