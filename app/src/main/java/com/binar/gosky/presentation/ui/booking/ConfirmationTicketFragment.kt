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
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionData
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.users.data.UserByIdData
import com.binar.gosky.databinding.FragmentConfirmationTicketBinding
import com.binar.gosky.util.ConvertUtil.convertISOtoDay
import com.binar.gosky.util.ConvertUtil.convertISOtoHour
import com.binar.gosky.util.ConvertUtil.convertMinutesToHourAndMinutes
import com.binar.gosky.util.ConvertUtil.convertRupiah
import com.binar.gosky.wrapper.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationTicketFragment : Fragment() {

    private var _binding: FragmentConfirmationTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by viewModels()
    private val confirmationTicketArgs: ConfirmationTicketFragmentArgs by navArgs()

    private var sumPrice = 0
    private var ticketPrice = 0

    lateinit var accessToken: String
    private var amount = 0
    private var ticketId = -1

    private lateinit var newTransactionData: NewTransactionData
    private lateinit var userByIdData: UserByIdData

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
        observeData()
        setOnClickListener()
    }

    private fun observeData() {
        viewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            accessToken = it
        }
        viewModel.newTransactionResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.data?.let { data ->
                        newTransactionData = data
                        data.userId?.let { id -> viewModel.getUserById(id) }
                    }
                    Log.d("newTransaction", it.data?.data.toString())
                    binding.pbOrder.isVisible = false
                }
                is Resource.Loading -> {
                    binding.pbOrder.isVisible = true
                }
                else -> {}
            }
        }
        viewModel.userByIdResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.data?.let { data ->
                        userByIdData = data
                        Log.d("userByIdData", data.toString())
                    }
                    navigateToOrderDetail()
                }
                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        binding.apply {
            ivBack.setOnClickListener { findNavController().navigateUp() }
            btnWishlist.setOnClickListener {
                confirmationTicketArgs.ticketsItem.id?.let { ticketId ->
                    viewModel.postTicketToWishlist(getString(R.string.bearer_token, accessToken),
                        ticketId
                    )
                }
            }
            btnMinus.setOnClickListener { minAmount() }
            btnPlus.setOnClickListener { plusAmount() }
            btnOrder.setOnClickListener {
                postNewTransaction(getString(R.string.bearer_token, accessToken))
            }
        }
    }

    private fun navigateToOrderDetail() {
        val action = ConfirmationTicketFragmentDirections.actionConfirmationTicketFragmentToDetailTicketFragment(
            confirmationTicketArgs.ticketsItem,
            newTransactionData,
            sumPrice,
            userByIdData
        )
        findNavController().navigate(action)
    }

    private fun postNewTransaction(accessToken: String) {
        viewModel.postNewTransaction(accessToken, NewTransactionRequestBody(amount, ticketId))
    }

    private fun plusAmount() {
        var tempAmount = binding.tvAmount.text.toString().toInt()
        if (tempAmount > 0) {
            tempAmount++
            sumPrice += ticketPrice
            binding.tvAmount.setText((tempAmount).toString())
        }
        amount = tempAmount
        binding.tvTotalAmount.text = getString(R.string.total_amount, convertRupiah(sumPrice))
    }

    private fun minAmount() {
        var tempAmount = binding.tvAmount.text.toString().toInt()
        if (tempAmount > 1) {
            tempAmount--
            sumPrice -= ticketPrice
            binding.tvAmount.setText((tempAmount).toString())
        }
        amount = tempAmount
        binding.tvTotalAmount.text = getString(R.string.total_amount, convertRupiah(sumPrice))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        binding.apply {
            confirmationTicketArgs.ticketsItem.apply {
                id?.let { ticketId = it }

                //Departure part
                tvFromDeparture.text = from
                tvToDeparture.text = to
                tvDepartureTimeDepartureDateDay.text = convertISOtoDay(departureTime)
                tvDepartureTimeDeparture.text = convertISOtoHour(departureTime)
                tvArrivalTimeDeparture.text = duration?.let { convertISOtoHour(departureTime, it) }
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
                    tvArrivalTimeReturn.text = duration?.let { convertISOtoHour(returnTime, it) }
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .into(ivImageReturn)
                    tvFlightNumberReturn.text = flightNumber
                    tvDurationReturn.text = duration?.let { convertMinutesToHourAndMinutes(it) }
                }

                //Description
                tvDescription.text = description

                //Total amount
                amount = tvAmount.text.toString().toInt()
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