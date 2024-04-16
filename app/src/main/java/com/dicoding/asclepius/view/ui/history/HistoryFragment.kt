package com.dicoding.asclepius.view.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.view.HistoryAdapter

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[HistoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistory.layoutManager = LinearLayoutManager(requireActivity())

        historyViewModel.getAllHistory().observe(viewLifecycleOwner) { history ->
            if (history.isNullOrEmpty()) {
                binding.tvMessageHistory.visibility = View.VISIBLE
                binding.tvMessageHistory.text = getString(R.string.no_history_data_message)
            } else {
                binding.tvMessageHistory.visibility = View.GONE
                setHistoryData(history)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setHistoryData(history: List<HistoryClassification>) {
        val historyAdapter = HistoryAdapter()
        historyAdapter.submitList(history)
        binding.rvHistory.adapter = historyAdapter
    }
}