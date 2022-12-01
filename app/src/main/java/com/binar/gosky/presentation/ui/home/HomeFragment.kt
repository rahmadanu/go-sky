package com.binar.gosky.presentation.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.gosky.data.network.model.tickets.SearchTickets
import com.binar.gosky.databinding.FragmentHomeBinding
import com.binar.gosky.presentation.ui.search.SearchResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchResultViewModel by viewModels()

    private lateinit var args: SearchTickets

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.etDepartureDate.setOnClickListener {
            showDatePickerDialog(it.id)
            Log.d("id", "departure: ${it.id}")
        }
        binding.etReturnDate.setOnClickListener {
            Log.d("id", "return: ${it.id}")
            showDatePickerDialog(it.id)
        }
        binding.swRoundTrip.setOnCheckedChangeListener { compoundButton, isChecked ->
            binding.tilReturnDate.isVisible = isChecked
            args.roundTrip = isChecked
        }
        binding.ivSwap.setOnClickListener {
            val temp = binding.etFrom.text
            binding.etFrom.text = binding.etTo.text
            binding.etTo.text = temp
            args.from = binding.etFrom.text.toString().trim()
            args.to = binding.etTo.text.toString().trim()
        }
        binding.btnSearch.setOnClickListener {
            navigateToSearchResult()
        }
    }

    private fun showDatePickerDialog(id: Int) {

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                day = dayOfMonth
                val formattedMonth = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Des")
                when (id) {
                    binding.etDepartureDate.id -> {
                        binding.etDepartureDate.setText("$dayOfMonth ${formattedMonth.get(monthOfYear)}, $year")
                        args.departureTime = getTimeStamp(year, monthOfYear, dayOfMonth)
                    }
                    binding.etReturnDate.id -> {
                        binding.etReturnDate.setText("$dayOfMonth ${formattedMonth.get(monthOfYear)}, $year")
                        args.returnDate = getTimeStamp(year, monthOfYear, dayOfMonth)
                    }
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun getTimeStamp(year: Int, monthOfYear: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = monthOfYear
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val timestamp: Long = calendar.timeInMillis
        Log.d("timestamp", timestamp.toString())

        return timestamp.toString()
    }

    private fun navigateToSearchResult() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(args)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        private var day = calendar.get(Calendar.DAY_OF_MONTH)
    }
}