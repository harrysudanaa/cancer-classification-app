package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.ClassificationResults
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        // Deserialize the results list
        val results = intent.getParcelableArrayListExtra<ClassificationResults>(EXTRA_CLASSIFICATION_RESULT)
        val inferenceTime = intent.getLongExtra(EXTRA_INFERENCE_TIME, 0)
        val resultImage = intent.getStringExtra(EXTRA_IMAGE)
        if (resultImage != null) {
            val resultImageUri = Uri.parse(resultImage)
            binding.resultImage.setImageURI(resultImageUri)
        }


        Toast.makeText(this, "$inferenceTime ms", Toast.LENGTH_SHORT).show()
        binding.resultText.text = "${results?.get(0)?.label} ${NumberFormat.getPercentInstance().format(results?.get(0)?.score)}"
    }

    companion object {
        const val EXTRA_CLASSIFICATION_RESULT = "classification_result"
        const val EXTRA_INFERENCE_TIME = "inference_time"
        const val EXTRA_IMAGE = "image"
    }


}