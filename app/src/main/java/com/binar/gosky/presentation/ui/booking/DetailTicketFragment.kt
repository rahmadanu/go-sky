package com.binar.gosky.presentation.ui.booking

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.gosky.R
import com.binar.gosky.data.network.model.transactions.byid.TransactionByIdData
import com.binar.gosky.databinding.FragmentDetailTicketBinding
import com.binar.gosky.util.ConvertUtil
import com.binar.gosky.wrapper.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTicketFragment : Fragment() {

    private var _binding: FragmentDetailTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by viewModels()

    private val detailTicketArgs: DetailTicketFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            btnBackToHome.setOnClickListener { findNavController().navigate(R.id.action_detailTicketFragment_to_homeFragment) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeData() {
        viewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            viewModel.getTransactionById(getString(R.string.bearer_token, it), detailTicketArgs.transactionId)
        }
        viewModel.transactionByIdResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.payload?.data?.let { data -> bindDataToForm(data) }
                    binding.pbLoading.isVisible = false
                }
                is Resource.Loading -> {
                    binding.pbLoading.isVisible = true
                }
                else -> {}
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindDataToForm(item: TransactionByIdData) {
        binding.apply {
            tvOrderBy.text = item.user?.name
            val totalPrice = (item.amount?.times(item.ticket?.price!!))
            tvTotalPrice.text = ConvertUtil.convertRupiah(totalPrice)
            tvOrderDate.text = ConvertUtil.convertISOtoDay(item.createdAt)
            tvBookingCode.text = item.bookingCode
            tvAmount.text = item.amount.toString()
            //Departure part
            tvFromDeparture.text = item.ticket?.from
            tvToDeparture.text = item.ticket?.to
            tvDepartureTimeDepartureDateDay.text = ConvertUtil.convertISOtoDay(item.ticket?.departureTime)
            tvDepartureTimeDeparture.text = ConvertUtil.convertISOtoHour(item.ticket?.departureTime)
            tvArrivalTimeDeparture.text =
                item.ticket?.duration?.let { ConvertUtil.convertISOtoHour(item.ticket.departureTime, it.toLong()) }
            Log.d("arrival", "${item.ticket?.departureTime} ${item.ticket?.duration?.toLong()}")
            Glide.with(requireContext())
                .load(item.ticket?.imageUrl)
                .into(ivImageDeparture)
            tvFlightNumberDeparture.text = item.ticket?.flightNumber
            tvDurationDeparture.text = item.ticket?.duration?.let {
                ConvertUtil.convertMinutesToHourAndMinutes(
                    it.toLong()
                )
            }

            //Return part
            if (item.ticket?.returnTime.isNullOrEmpty()) {
                cvTicketDetailReturn.isVisible = false
            } else {
                cvTicketDetailReturn.isVisible = true
                tvReturnTimeReturnDateDay.text = ConvertUtil.convertISOtoDay(item.ticket?.returnTime)
                tvFromReturn.text = item.ticket?.to
                tvToReturn.text = item.ticket?.from
                tvDepartureTimeReturn.text = ConvertUtil.convertISOtoHour(item.ticket?.returnTime)
                tvArrivalTimeReturn.text =
                    item.ticket?.duration?.let { ConvertUtil.convertISOtoHour(item.ticket.returnTime, it.toLong()) }
                Glide.with(requireContext())
                    .load(item.ticketId)
                    .into(ivImageReturn)
                tvFlightNumberReturn.text = item.ticket?.flightNumber
                tvDurationReturn.text = item.ticket?.duration?.let {
                    ConvertUtil.convertMinutesToHourAndMinutes(
                        it.toLong()
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}