package com.dicoding.asclepius.view.ui.result

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.databinding.FragmentResultBinding
import java.text.NumberFormat

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val arguments: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val resultViewModel by lazy {
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            )[ResultViewModel::class.java]
        }
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        with(arguments) {
            val resultImage = classificationResult[0].imageData
            val imageBitmap = resultImage?.let {
                BitmapFactory.decodeByteArray(
                    resultImage, 0, it.size
                )
            }
            binding.resultImage.setImageBitmap(imageBitmap)
            binding.resultText.text = getString(
                R.string.result_text,
                classificationResult[0].label,
                NumberFormat.getPercentInstance().format(classificationResult[0].score)
            )
            binding.tvInferenceTime.text =
                getString(R.string.inference_time, inferenceTime.toString())
            binding.saveButton.setOnClickListener {
                val historyData = HistoryClassification(
                    label = classificationResult[0].label,
                    score = classificationResult[0].score,
                    imageData = classificationResult[0].imageData,
                    date = classificationResult[0].date
                )
                resultViewModel.insertHistory(historyData)
                showToast("Saved to history")
            }
        }

        return binding.root
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}