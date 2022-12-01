package com.binar.gosky.presentation.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.gosky.data.network.model.tickets.SearchTickets
import com.binar.gosky.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //private lateinit var args: SearchTickets
    private var category = ""
    private var from = ""
    private var to = ""
    private var departureTime = ""
    private var returnTime = ""
    private var roundTrip = false

    private val formattedMonth = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Des")

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

        initView()
        setOnClickListener()
    }

    private fun initView() {
        binding.apply {
            etFrom.setText("JAKARTA")
            etTo.setText("SURABAYA")
            etDepartureDate.setText("$day ${formattedMonth.get(month)}, $year")
            etReturnDate.setText("$day ${formattedMonth.get(month)}, $year")
        }
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
            roundTrip = isChecked
            category = if (isChecked) ROUND_TRIP else ONE_WAY
        }
        binding.ivSwap.setOnClickListener {
            val temp = binding.etFrom.text
            binding.etFrom.text = binding.etTo.text
            binding.etTo.text = temp
            from = binding.etFrom.text.toString().trim()
            to = binding.etTo.text.toString().trim()
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
                when (id) {
                    binding.etDepartureDate.id -> {
                        binding.etDepartureDate.setText("$dayOfMonth ${formattedMonth.get(monthOfYear)}, $year")
                        departureTime = getTimeStamp(year, monthOfYear, dayOfMonth)
                    }
                    binding.etReturnDate.id -> {
                        binding.etReturnDate.setText("$dayOfMonth ${formattedMonth.get(monthOfYear)}, $year")
                        returnTime = getTimeStamp(year, monthOfYear, dayOfMonth)
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

    private fun parseFormIntoEntity(): SearchTickets {
        return SearchTickets(
            category,
            from,
            to,
            departureTime,
            returnTime,
            roundTrip,
        )
    }

    private fun navigateToSearchResult() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(parseFormIntoEntity())
        Log.d("args", parseFormIntoEntity().toString())

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

        private const val ONE_WAY = "ONE_WAY"
        private const val ROUND_TRIP = "ROUND_TRIP"
    }
}