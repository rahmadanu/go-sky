package com.binar.gosky.presentation.ui.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.gosky.R
import com.binar.gosky.data.network.model.image.ImageData
import com.binar.gosky.data.network.model.tickets.EditTicketRequestBody
import com.binar.gosky.databinding.FragmentEditConfirmationTicketBinding
import com.binar.gosky.presentation.ui.home.HomeFragment
import com.binar.gosky.util.ConvertUtil
import com.binar.gosky.util.DateUtil
import com.binar.gosky.util.DateUtil.departureTime
import com.binar.gosky.util.DateUtil.returnTime
import com.binar.gosky.util.DateUtil.showDatePickerDialog
import com.binar.gosky.util.ImageUtil
import com.binar.gosky.wrapper.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditConfirmationTicketFragment : Fragment() {

    private var _binding: FragmentEditConfirmationTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditTicketViewModel by viewModels()

    private val editTicketArgs: EditConfirmationTicketFragmentArgs by navArgs()

    var category: String? = HomeFragment.ONE_WAY
    private var imageIdInsert: String? = ""
    private var imageUrlInsert: String? = ""
    private lateinit var accessToken: String

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            val imageFile = ImageUtil.getImageMultipartBody(requireContext(), result)
            if (imageFile != null) {
                viewModel.postImage("Bearer ${editTicketArgs.accessToken}",
                    ImageUtil.IMAGE_TYPE_TICKET, imageFile)
            }
        }

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
        viewModel.imageResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.d("imageIdSuccess", it.payload?.data?.imageId.toString())
                    editTicketArgs.ticketsItem?.imageId = it.payload?.data?.imageId
                    editTicketArgs.ticketsItem?.imageUrl = it.payload?.data?.imageUrl
                    imageIdInsert = it.payload?.data?.imageId
                    imageUrlInsert = it.payload?.data?.imageUrl
                    setProfileImage(it.payload?.data)
                    binding.pbLoadingImage.isVisible = false
                    binding.tvAddImage.isVisible = false
                    binding.tvDeleteImage.isVisible = true
                    binding.ivImage.isVisible = true
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    binding.pbLoadingImage.isVisible = true
                    binding.ivImage.visibility = View.INVISIBLE
                }
                else -> {}
            }
            Log.d("imageresponse", it.payload?.data.toString())
        }
        viewModel.deleteImageResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    setProfileImage(null)
                    binding.tvAddImage.isVisible = true
                    binding.tvDeleteImage.isVisible = false
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {}
                else -> {}
            }
        }
    }

    private fun setProfileImage(image: ImageData?) {
        Glide.with(requireContext())
            .load(image?.imageUrl)
            .placeholder(R.drawable.ic_placeholder_image)
            .circleCrop()
            .into(binding.ivImage)
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
            imageId = editTicketArgs.ticketsItem?.imageId ?: imageIdInsert,
            imageUrl = editTicketArgs.ticketsItem?.imageUrl ?: imageUrlInsert,
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

                    if (editTicketArgs.ticketsItem?.imageId.isNullOrEmpty() || editTicketArgs.ticketsItem?.imageId.equals("-") ) {
                        tvAddImage.isVisible = true
                        tvDeleteImage.isVisible = false
                    } else {
                        Glide.with(requireContext())
                            .load(editTicketArgs.ticketsItem?.imageUrl)
                            .circleCrop()
                            .into(ivImage)
                        tvAddImage.isVisible = false
                        tvDeleteImage.isVisible = true
                    }
                }
                Log.d("editTicketsArgsReturn", returnTime.toString())
            }
        } else {
            binding.apply {
                tvPageTitle.text = getString(R.string.insert_ticket_data)
                btnUpdate.text = getString(R.string.insert)

                editTicketArgs.ticketsItem?.let {
                    if (editTicketArgs.ticketsItem?.imageId.isNullOrEmpty() || editTicketArgs.ticketsItem?.imageId.equals("-") ) {
                        tvAddImage.isVisible = true
                        tvDeleteImage.isVisible = false
                    } else {
                        Glide.with(requireContext())
                            .load(editTicketArgs.ticketsItem?.imageUrl)
                            .circleCrop()
                            .into(ivImage)
                        tvAddImage.isVisible = false
                        tvDeleteImage.isVisible = true
                    }
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
            swRoundTrip.setOnCheckedChangeListener { compoundButton, isChecked ->
                binding.tilReturnDate.isVisible = isChecked
                //roundTrip = isChecked
                this@EditConfirmationTicketFragment.category = if (isChecked) HomeFragment.ROUND_TRIP else HomeFragment.ONE_WAY
            }
            ivBack.setOnClickListener { findNavController().navigateUp() }
            ivImage.setOnClickListener {
                getImageFromGallery()
            }
            tvAddImage.setOnClickListener {
                getImageFromGallery()
            }
            tvDeleteImage.setOnClickListener {
                if (isEditAction()) {
                    editTicketArgs.ticketsItem?.imageId?.let { it1 ->
                        viewModel.deleteImage("Bearer ${editTicketArgs.accessToken}",
                            ImageUtil.IMAGE_TYPE_TICKET,
                            it1
                        )
                        editTicketArgs.ticketsItem?.imageId = "-"
                        editTicketArgs.ticketsItem?.imageUrl = "-"
                        Log.d("imageIdAfterDelete", editTicketArgs.ticketsItem?.imageId.toString())
                    }
                } else {
                    imageIdInsert?.let {
                        viewModel.deleteImage("Bearer ${editTicketArgs.accessToken}",
                            ImageUtil.IMAGE_TYPE_TICKET,
                            it
                        )
                        editTicketArgs.ticketsItem?.imageId = "-"
                        editTicketArgs.ticketsItem?.imageUrl = "-"
                        Log.d("imageIdAfterDelete", editTicketArgs.ticketsItem?.imageId.toString())
                    }
                }
            }
            btnUpdate.setOnClickListener {
                saveData()
            }
        }
    }

    private fun getImageFromGallery() {
        activity?.intent?.type = "image/*"
        galleryResult.launch("image/*")
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
                Log.d("insertTicket", parseFormIntoEntity().toString())
            }
        }
        Log.d("edit", parseFormIntoEntity().toString())
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val from = binding.etFrom.text.toString()
        val to = binding.etTo.text.toString()
        val flightNumber = binding.etFlightNumber.text.toString()
        var price = binding.etPrice.text.toString()

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
        if (price.equals("")) {
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