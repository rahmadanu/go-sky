package com.binar.gosky.presentation.ui.booking

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionData
import com.binar.gosky.data.network.model.users.data.UserByIdData
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
        bindDataToForm(detailTicketArgs.userByIdData.name, detailTicketArgs.newTransactionData, detailTicketArgs.ticketDetail, detailTicketArgs.totalPrice)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            ivBack.setOnClickListener { findNavController().popBackStack() }
            btnBackToHome.setOnClickListener { findNavController().navigate(R.id.action_detailTicketFragment_to_homeFragment) }
        }
    }

    private fun observeData() {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindDataToForm(orderBy: String?, newTransaction: NewTransactionData?, ticketDetail: TicketsItem, totalPrice: Int) {
        binding.apply {
            Log.d("orderBy", orderBy.toString())
            tvOrderBy.text = orderBy
            tvTotalPrice.text = ConvertUtil.convertRupiah(totalPrice)
            newTransaction?.apply {
                tvOrderDate.text = ConvertUtil.convertISOtoDay(createdAt)
                tvBookingCode.text = bookingCode
                tvAmount.text = amount.toString()
            }
            ticketDetail.apply {
                //Departure part
                tvFromDeparture.text = from
                tvToDeparture.text = to
                tvDepartureTimeDepartureDateDay.text = ConvertUtil.convertISOtoDay(departureTime)
                tvDepartureTimeDeparture.text = ConvertUtil.convertISOtoHour(departureTime)
                Glide.with(requireContext())
                    .load(imageUrl)
                    .into(ivImageDeparture)
                tvFlightNumberDeparture.text = flightNumber
                tvDurationDeparture.text = duration?.let {
                    ConvertUtil.convertMinutesToHourAndMinutes(
                        it
                    )
                }

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
                    tvReturnTimeReturnDateDay.text = ConvertUtil.convertISOtoDay(returnTime)
                    tvFromReturn.text = to
                    tvToReturn.text = from
                    tvDepartureTimeReturn.text = ConvertUtil.convertISOtoHour(returnTime)
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .into(ivImageReturn)
                    tvFlightNumberReturn.text = flightNumber
                    tvDurationReturn.text = duration?.let {
                        ConvertUtil.convertMinutesToHourAndMinutes(
                            it
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}