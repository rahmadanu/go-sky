package com.binar.gosky.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.gosky.R
import com.binar.gosky.data.network.model.auth.user.CurrentUserData
import com.binar.gosky.data.network.model.tickets.SearchTickets
import com.binar.gosky.data.network.model.transactions.earnings.EarningsData
import com.binar.gosky.databinding.FragmentHomeBinding
import com.binar.gosky.presentation.ui.account.AccountViewModel
import com.binar.gosky.presentation.ui.notification.NotificationViewModel
import com.binar.gosky.util.ConvertUtil
import com.binar.gosky.util.DateUtil.day
import com.binar.gosky.util.DateUtil.departureTime
import com.binar.gosky.util.DateUtil.formattedMonth
import com.binar.gosky.util.DateUtil.getTimeStamp
import com.binar.gosky.util.DateUtil.hour
import com.binar.gosky.util.DateUtil.minute
import com.binar.gosky.util.DateUtil.month
import com.binar.gosky.util.DateUtil.returnTime
import com.binar.gosky.util.DateUtil.showDatePickerDialog
import com.binar.gosky.util.DateUtil.year
import com.binar.gosky.wrapper.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //use AccountViewModel to get current user
    private val accountViewModel: AccountViewModel by viewModels()

    private val homeViewModel: HomeViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()

    var category: String = ONE_WAY
    var from: String = ""
    var to: String = ""
    var roundTrip: Boolean = false

    lateinit var accessToken: String

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
        accountViewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            accessToken = it
            accountViewModel.getCurrentUser(getString(R.string.bearer_token, it))
        }
        accountViewModel.currentUserResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    setWelcomeMessage(it.data?.data)
                    it.data?.data?.role?.let { role -> accountViewModel.setUserRole(role)
                        Log.d("checkadmin", role)
                    }
                }
                else -> {}
            }
        }
        accountViewModel.checkIfUserIsAdmin().observe(viewLifecycleOwner) {
            val isAdmin = it.equals("ADMIN")
            showFabIfUserIsAdmin(isAdmin)
            getEarnings()
            showEarningsIfUserIsAdmin(isAdmin)
            showHomeTitleAsAdmin(isAdmin)
        }
        homeViewModel.earningsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    bindEarningsDataToView(it.data?.data)
                    binding.pbEarnings.isVisible = false
                }
                is Resource.Loading -> {
                    binding.pbEarnings.isVisible = true
                }
                else -> {}
            }
        }
        Log.d("checkadmin", accountViewModel.checkIfUserIsAdmin().toString())
        notificationViewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            Log.d("accessToken in home", it)
            accessToken = it
            getNotification(it)
        }
        notificationViewModel.unreadNotificationCount.observe(viewLifecycleOwner) {
            it.let { unreadCount ->
                if (unreadCount == 0) {
                    binding.tvCartBadge.isVisible = false
                } else {
                    binding.tvCartBadge.isVisible = true
                    binding.tvCartBadge.text = unreadCount.toString()
                }
            }
        }
    }

    private fun showHomeTitleAsAdmin(admin: Boolean) {
        if (admin) {
            binding.tvTitleGoSky.text = getString(R.string.title_gosky_admin)
        } else {
            binding.tvTitleGoSky.text = getString(R.string.title_gosky)
        }
    }

    private fun getNotification(accessToken: String? = "") {
        notificationViewModel.getNotification(getString(R.string.bearer_token, accessToken))
    }

    private fun showEarningsIfUserIsAdmin(isAdmin: Boolean) {
        binding.apply {
            cvEarnings.isVisible = isAdmin
            tvWelcomeMessage.isVisible = !isAdmin
            ivUserImage.isVisible = !isAdmin
        }
    }

    private fun setWelcomeMessage(currentUserData: CurrentUserData?) {
        binding.apply {
            currentUserData?.let {
                tvWelcomeMessage.text = getString(R.string.welcome_message, it.name)
                Glide.with(requireContext())
                    .load(it.imageUrl)
                    .placeholder(R.drawable.ic_placeholder_image)
                    .circleCrop()
                    .into(ivUserImage)
            }
        }
    }

    private fun getEarnings() {
        homeViewModel.getEarnings(getString(R.string.bearer_token,accessToken))
    }

    private fun bindEarningsDataToView(earnings: EarningsData?) {
        earnings?.let {
            binding.apply {
                tvTotalEarnings.text = it.thisYear?.count?.let { count ->
                    Log.d("earningscount", count.toString())
                    resources.getQuantityString(R.plurals.total_earnings,
                        count,
                        count
                    )
                }
                tvTodayEarnings.text = ConvertUtil.convertRupiah(it.today?.earnings)
                tvThisMonthEarnings.text = ConvertUtil.convertRupiah(it.thisMonth?.earnings)
                tvThisYearEarnings.text = ConvertUtil.convertRupiah(it.thisYear?.earnings)
            }
        }
    }

    private fun showFabIfUserIsAdmin(isAdmin: Boolean) {
        binding.fabAddTicket.isVisible = isAdmin
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
        departureTime = getTimeStamp(year, month, day, hour, minute)
        if (binding.swRoundTrip.isChecked) {
            returnTime = getTimeStamp(year, month, day, hour, minute)
        }
    }

    private fun setOnClickListener() {
        binding.etDepartureDate.setOnClickListener {
            showDatePickerDialog(it.id, requireContext(), homeBinding = binding)
            Log.d("id", "departure: ${it.id}")
        }
        binding.etReturnDate.setOnClickListener {
            Log.d("id", "return: ${it.id}")
            showDatePickerDialog(it.id, requireContext(), homeBinding = binding)
        }
        binding.swRoundTrip.setOnCheckedChangeListener { _, isChecked ->
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
            val searchTickets =
                parseFormIntoEntity(category, from, to, departureTime, returnTime, roundTrip)
            navigateToSearchResult(searchTickets)
        }
        binding.ivNotification.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
        }
        binding.fabAddTicket.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditConfirmationTicketFragment(accessToken)
            //action.accessToken = accessToken
            findNavController().navigate(action)
        }
    }

    private fun parseFormIntoEntity(
        category: String,
        from: String,
        to: String,
        departureTime: String,
        returnTime: String?,
        roundTrip: Boolean
    ): SearchTickets {
        return SearchTickets(
            category,
            from = binding.etFrom.text.toString(),
            to = binding.etTo.text.toString(),
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
        const val ONE_WAY = "ONE_WAY"
        const val ROUND_TRIP = "ROUND_TRIP"
    }
}