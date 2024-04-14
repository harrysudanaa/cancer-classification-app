package com.dicoding.asclepius.view.result

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.databinding.ActivityResultBinding
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val resultViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ResultViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        // Deserialize the results list
        val results = intent.getParcelableArrayListExtra<HistoryClassification>(
            EXTRA_CLASSIFICATION_RESULT
        )

        val inferenceTime = intent.getLongExtra(EXTRA_INFERENCE_TIME, 0)
        val resultImage = results?.get(0)?.imageData
        if (resultImage != null) {
            val imageBitmap = BitmapFactory.decodeByteArray(
                resultImage, 0, resultImage.size
            )
            binding.resultImage.setImageBitmap(imageBitmap)
        }


        Toast.makeText(this, "$inferenceTime ms", Toast.LENGTH_SHORT).show()
        binding.resultText.text = "${results?.get(0)?.label} ${NumberFormat.getPercentInstance().format(results?.get(0)?.score)}"
        binding.btnSaveToHistory.setOnClickListener {
            if (results != null) {
                val historyData = HistoryClassification(
                    label = results[0].label,
                    score = results[0].score,
                    imageData = results[0].imageData,
                    date = results[0].date
                )
                resultViewModel.insertHistory(historyData)
            }
        }
    }

    companion object {
        const val EXTRA_CLASSIFICATION_RESULT = "classification_result"
        const val EXTRA_INFERENCE_TIME = "inference_time"
    }
}