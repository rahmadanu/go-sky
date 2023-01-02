package com.binar.gosky.presentation.ui.booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.gosky.R
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionData
import com.binar.gosky.databinding.FragmentDetailTicketBinding
import com.binar.gosky.util.ConvertUtil
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTicketFragment : Fragment() {

    private var _binding: FragmentDetailTicketBinding? = null
    private val binding get() = _binding!!

    //private val viewModel: BookingViewModel by viewModels()

    private val detailTicketArgs: DetailTicketFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        bindDataToForm(detailTicketArgs.userByIdData.name, detailTicketArgs.newTransactionData, detailTicketArgs.ticketDetail, detailTicketArgs.totalPrice)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            btnBackToHome.setOnClickListener { findNavController().navigate(R.id.action_detailTicketFragment_to_homeFragment) }
        }
    }

    private fun observeData() {
    }

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
                tvArrivalTimeDeparture.text =
                    duration?.let { ConvertUtil.convertISOtoHour(departureTime, it) }
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
                    cvTicketDetailReturn.isVisible = false
                } else {
                    cvTicketDetailReturn.isVisible = true
                    tvReturnTimeReturnDateDay.text = ConvertUtil.convertISOtoDay(returnTime)
                    tvFromReturn.text = to
                    tvToReturn.text = from
                    tvDepartureTimeReturn.text = ConvertUtil.convertISOtoHour(returnTime)
                    tvArrivalTimeReturn.text =
                        duration?.let { ConvertUtil.convertISOtoHour(returnTime, it) }
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
