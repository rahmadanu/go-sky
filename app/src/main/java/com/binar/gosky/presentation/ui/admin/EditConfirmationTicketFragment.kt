package com.binar.gosky.presentation.ui.admin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.gosky.R
import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.databinding.FragmentEditConfirmationTicketBinding
import com.binar.gosky.presentation.ui.home.HomeFragment
import com.binar.gosky.util.ConvertUtil
import com.binar.gosky.util.DateUtil.departureTime
import com.binar.gosky.util.DateUtil.returnTime
import com.binar.gosky.util.DateUtil.showDatePickerDialog
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditConfirmationTicketFragment : Fragment() {

    private var _binding: FragmentEditConfirmationTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditTicketViewModel by viewModels()

    private val editTicketArgs: EditConfirmationTicketFragmentArgs by navArgs()

    var category: String = HomeFragment.ONE_WAY
    private lateinit var accessToken: String

    private var listener: OnTicketItemChangedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnTicketItemChangedListener){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditConfirmationTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

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
        viewModel.editTicketResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    listener?.onTicketItemChanged()
                    findNavController().navigateUp()
                }
                is Resource.Loading -> {}
                else -> {}
            }
        }
    }

    private fun parseFormIntoEntity(): EditTicketRequestBody {
        return EditTicketRequestBody(
            category = category,
            from = binding.etFrom.text.toString(),
            to = binding.etTo.text.toString(),
            flightNumber = binding.etFlightNumber.text.toString(),
            departureTime = departureTime,
            returnTime = returnTime,
            price = binding.etPrice.text.toString().toInt(),
//            imageId = ,
//            imageUrl = ,
            description = binding.etDescription.text.toString()
        )
    }

    private fun initView() {
        editTicketArgs.ticketsItem.apply {
            binding.apply {
                etFrom.setText(from)
                etTo.setText(to)
                etFlightNumber.setText(flightNumber)
                etDepartureDate.setText(ConvertUtil.convertIOStoDate(departureTime))

                etPrice.setText(price.toString())
                etDescription.setText(description)

                swRoundTrip.setOnCheckedChangeListener { compoundButton, isChecked ->
                    binding.tilReturnDate.isVisible = isChecked
                    //roundTrip = isChecked
                    this@EditConfirmationTicketFragment.category = if (isChecked) HomeFragment.ROUND_TRIP else HomeFragment.ONE_WAY
                }

            }
        }
    }

    private fun setOnClickListener() {
        binding.apply {
            etDepartureDate.setOnClickListener {
                showDatePickerDialog(it.id, requireContext(), editTicketBinding = binding)
            }
            etReturnDate.setOnClickListener {
                showDatePickerDialog(it.id, requireContext(), editTicketBinding = binding)
            }
            ivBack.setOnClickListener { findNavController().navigateUp() }
            btnUpdate.setOnClickListener {
                editTicketArgs.ticketsItem.id?.let { ticketId ->
                    viewModel.putTicketById(
                        getString(R.string.bearer_token, accessToken),
                        ticketId,
                        parseFormIntoEntity()
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

interface OnTicketItemChangedListener {
    fun onTicketItemChanged()
}