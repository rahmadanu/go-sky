package com.binar.gosky.presentation.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.gosky.R
import com.binar.gosky.data.network.model.auth.user.CurrentUserData
import com.binar.gosky.data.network.model.tickets.SearchTickets
import com.binar.gosky.databinding.FragmentHomeBinding
import com.binar.gosky.presentation.ui.account.AccountViewModel
import com.binar.gosky.wrapper.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //use AccountViewModel to get current user
    private val viewModel: AccountViewModel by viewModels()

    var category: String = ONE_WAY
    var from: String = ""
    var to: String = ""
    lateinit var departureTime: String
    lateinit var returnTime: String
    var roundTrip: Boolean = false

    private val formattedMonth =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Des")

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
        observeData()
        setOnClickListener()
    }

    private fun observeData() {
        viewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            viewModel.getCurrentUser(getString(R.string.bearer_token, it))
        }
        viewModel.currentUserResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    setWelcomeMessage(it.data?.data)
                    it.data?.data?.role?.let { role -> viewModel.setUserRole(role)
                        Log.d("checkadmin", role)
                    }
                }
                else -> {}
            }
        }
        viewModel.checkIfUserIsAdmin().observe(viewLifecycleOwner) {
            showFabIfUserIsAdmin(it.equals("ADMIN"))
        }
        Log.d("checkadmin", viewModel.checkIfUserIsAdmin().toString())
    }

    private fun showFabIfUserIsAdmin(isAdmin: Boolean) {
        binding.fabAddTicket.isVisible = isAdmin
    }

    private fun setWelcomeMessage(currentUserData: CurrentUserData?) {
        binding.apply {
            currentUserData?.let {
                tvWelcomeMessage.text = getString(R.string.welcome_message, it.name)
                Glide.with(requireContext())
                    .load(it.imageUrl)
                    .placeholder(R.drawable.ic_placeholder_image)
                    .into(ivUserImage)
            }
        }
    }

    private fun initView() {
        val province = resources.getStringArray(R.array.province)
        val arrayAdapter =
            activity?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_list_item_1,
                    province
                )
            }
        binding.apply {
            etDepartureDate.setText("$day ${formattedMonth.get(month)}, $year")
            etReturnDate.setText("$day ${formattedMonth.get(month)}, $year")
        }
        from = binding.etFrom.text.toString().trim().uppercase()
        Log.d("from", binding.etFrom.text.toString())
        to = binding.etTo.text.toString().trim().uppercase()

        binding.etFrom.setAdapter(arrayAdapter)
        binding.etTo.setAdapter(arrayAdapter)
        departureTime = getTimeStamp(year, month, day)
        returnTime = getTimeStamp(year, month, day)

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
            from = binding.etFrom.text.toString().trim().uppercase()
            to = binding.etTo.text.toString().trim().uppercase()
        }
        binding.btnSearch.setOnClickListener {
            initView()
            val searchTickets =
                parseFormIntoEntity(category, from, to, departureTime, returnTime, roundTrip)
            navigateToSearchResult(searchTickets)
        }
        binding.ivNotification.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
        }
    }

    private fun showDatePickerDialog(id: Int) {

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                day = dayOfMonth
                when (id) {
                    binding.etDepartureDate.id -> {
                        binding.etDepartureDate.setText(
                            "$dayOfMonth ${
                                formattedMonth.get(
                                    monthOfYear
                                )
                            }, $year"
                        )
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
        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val localeID = Locale("in", "ID")
        val formattedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", localeID).format(date)
        Log.d("timestamp", formattedDate.toString())

        return formattedDate
    }

    private fun parseFormIntoEntity(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String,
        roundTrip: Boolean
    ): SearchTickets {
        return SearchTickets(
            category,
            from,
            to,
            departureTime,
            returnTime,
            roundTrip,
        )
    }

    private fun navigateToSearchResult(searchTickets: SearchTickets) {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(searchTickets)
        Log.d("args", searchTickets.toString())

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