package com.binar.gosky.presentation.ui.booking

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.gosky.R
import com.binar.gosky.databinding.FragmentConfirmationTicketBinding
import com.binar.gosky.util.ConvertUtil.convertISOtoDay
import com.binar.gosky.util.ConvertUtil.convertISOtoHour
import com.binar.gosky.util.ConvertUtil.convertMinutesToHourAndMinutes
import com.binar.gosky.util.ConvertUtil.convertRupiah
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ConfirmationTicketFragment : Fragment() {

    private var _binding: FragmentConfirmationTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by viewModels()
    private val confirmationTicketArgs: com.binar.gosky.presentation.ui.order.ConfirmationTicketFragmentArgs by navArgs()

    private var sumPrice = 0
    private var ticketPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmationTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            ivBack.setOnClickListener { findNavController().navigateUp() }
            ibMinus.setOnClickListener { minAmount() }
            ibPlus.setOnClickListener { plusAmount() }
            btnOrder.setOnClickListener {
                findNavController().navigate(R.id.action_confirmationTicketFragment_to_detailTicketFragment)
            }
        }
    }

    private fun plusAmount() {
        var tempAmount = binding.tvAmount.text.toString().toInt()
        if (tempAmount > 0) {
            tempAmount++
            sumPrice += ticketPrice
            binding.tvAmount.setText((tempAmount).toString())
        }
        binding.tvTotalAmount.text = getString(R.string.total_amount, convertRupiah(sumPrice))
    }

    private fun minAmount() {
        var tempAmount = binding.tvAmount.text.toString().toInt()
        if (tempAmount > 1) {
            tempAmount--
            sumPrice -= ticketPrice
            binding.tvAmount.setText((tempAmount).toString())
        }
        binding.tvTotalAmount.text = getString(R.string.total_amount, convertRupiah(sumPrice))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        binding.apply {
            confirmationTicketArgs.ticketsItem.apply {
                //Departure part
                tvFromDeparture.text = from
                tvToDeparture.text = to
                tvDepartureTimeDepartureDateDay.text = convertISOtoDay(departureTime)
                tvDepartureTimeDeparture.text = convertISOtoHour(departureTime)
                Glide.with(requireContext())
                    .load(imageUrl)
                    .into(ivImageDeparture)
                tvFlightNumberDeparture.text = flightNumber
                tvDurationDeparture.text = duration?.let { convertMinutesToHourAndMinutes(it) }

                //Return part
                if (returnTime.isNullOrEmpty()) {
                    tvReturnTimeReturnDateDay.isVisible = false
                    tvFromReturn.isVisible = false
                    tvToReturn.isVisible = false
                    tvDepartureTimeReturn.isVisible = false
                    ivImageReturn.isVisible = false
                    tvFlightNumberReturn.isVisible = false
                    tvDurationReturn.isVisible = false
                    tvOperatedByReturn.isVisible = false
                    tvReturnLabel.isVisible = false
                    tvAirlineReturn.isVisible = false
                    vLine.isVisible = false
                } else {
                    tvReturnTimeReturnDateDay.text = convertISOtoDay(returnTime)
                    tvFromReturn.text = to
                    tvToReturn.text = from
                    tvDepartureTimeReturn.text = convertISOtoHour(returnTime)
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .into(ivImageReturn)
                    tvFlightNumberReturn.text = flightNumber
                    tvDurationReturn.text = duration?.let { convertMinutesToHourAndMinutes(it) }
                }

                //Description
                tvDescription.text = description

                //Total amount
                price?.let {
                    ticketPrice = it
                }
                sumPrice = ticketPrice
                binding.tvTotalAmount.text = getString(R.string.total_amount, convertRupiah(sumPrice))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}