package com.binar.gosky.presentation.ui.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.binar.gosky.util.DateUtil
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

    var category: String? = HomeFragment.ONE_WAY
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
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {}
                else -> {}
            }
        }
        viewModel.addTicketResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
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
            imageId = "-",
            imageUrl = "-",
            description = binding.etDescription.text.toString(),
            duration = binding.etDuration.text.toString().toLong(),
            wishlisted = false
        )
    }

    private fun isEditAction(): Boolean {
        return editTicketArgs.ticketsItem?.id != null
    }

    private fun initView() {
        if (isEditAction()) {
            editTicketArgs.ticketsItem?.apply {
                Log.d("editTicketsArgs", this.toString())
                binding.apply {
                    this@EditConfirmationTicketFragment.category = category
                    etFrom.setText(from)
                    etTo.setText(to)
                    etFlightNumber.setText(flightNumber)
                    etDepartureDate.setText(ConvertUtil.convertISOtoDateHoursMinute(departureTime))
                    if (departureTime != null) {
                        DateUtil.departureTime = departureTime
                    }
                    etDuration.setText(duration.toString())

                    etPrice.setText(price.toString())
                    etDescription.setText(description)
                    if (category == HomeFragment.ROUND_TRIP) {
                        swRoundTrip.isChecked = true
                        tilReturnDate.isVisible = true
                        etReturnDate.setText(ConvertUtil.convertISOtoDateHoursMinute(returnTime))
                        if (returnTime != null) {
                            DateUtil.returnTime = returnTime
                        }
                    } else {
                        DateUtil.returnTime = null
                        swRoundTrip.isChecked = false
                        tilReturnDate.isVisible = false
                    }
                }
                Log.d("editTicketsArgsReturn", returnTime.toString())
            }
        } else {
            binding.apply {
                tvPageTitle.text = getString(R.string.insert_ticket_data)
                btnUpdate.text = getString(R.string.insert)
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
            swRoundTrip.setOnCheckedChangeListener { compoundButton, isChecked ->
                binding.tilReturnDate.isVisible = isChecked
                //roundTrip = isChecked
                this@EditConfirmationTicketFragment.category = if (isChecked) HomeFragment.ROUND_TRIP else HomeFragment.ONE_WAY
            }
            ivBack.setOnClickListener { findNavController().navigateUp() }
            btnUpdate.setOnClickListener {
                saveData()
            }
        }
    }

    private fun saveData() {
        if (validateInput()) {
            if (isEditAction()) {
                editTicketArgs.ticketsItem?.id?.let { ticketId ->
                    viewModel.putTicketById(
                        getString(R.string.bearer_token, accessToken),
                        ticketId,
                        parseFormIntoEntity()
                    )
                }
            } else {
                viewModel.postTicket(getString(R.string.bearer_token, accessToken), parseFormIntoEntity())
            }
        }
        Log.d("edit", parseFormIntoEntity().toString())
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val from = binding.etFrom.text.toString()
        val to = binding.etTo.text.toString()
        val flightNumber = binding.etFlightNumber.text.toString()
        val price = binding.etPrice.text.toString()

        if (from.isEmpty()) {
            isValid = false
            binding.etFrom.error = "From field must not be empty"
        } else
        if (to.isEmpty()) {
            isValid = false
            binding.etTo.error = "To field must not be empty"
        } else
        if (flightNumber.isEmpty()) {
            isValid = false
            binding.etFlightNumber.error = "Flight number field must not be empty"
        } else
        if (price.isEmpty()) {
            isValid = false
            binding.etPrice.error = "Price field must not be empty"
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface OnTicketItemChangedListener {
    fun onTicketItemChanged()
}
