package com.dicoding.asclepius.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.dicoding.asclepius.data.ClassificationResults
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            croppedImageUri = File(filesDir, "croppedImage_" + UUID.randomUUID().toString() + ".jpg").toUri()
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
        croppedImageUri = File(filesDir, "croppedImage.jpg").toUri()
        launcherGallery.launch("image/*")
    }

    private fun showImage(listUri: List<Uri>) {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        cropImage.launch(listUri)
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
        croppedImageUri?.let { imageClassifierHelper.classifyStaticImage(it) }
    }

    private fun moveToResult(results: ArrayList<ClassificationResults>?, inferenceTime: Long) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putParcelableArrayListExtra(ResultActivity.EXTRA_CLASSIFICATION_RESULT, results)
        intent.putExtra(ResultActivity.EXTRA_INFERENCE_TIME, inferenceTime)
        if (croppedImageUri != null) {
            intent.putExtra(ResultActivity.EXTRA_IMAGE, croppedImageUri.toString())
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}