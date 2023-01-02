package com.binar.gosky.presentation.ui.wishlist

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.gosky.R
import com.binar.gosky.data.local.mapper.toTicketsItem
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.databinding.FragmentWishlistBinding
import com.binar.gosky.presentation.ui.search.SearchResultFragmentDirections
import com.binar.gosky.presentation.ui.search.adapter.SearchResultAdapter
import com.binar.gosky.presentation.ui.search.adapter.TicketItemClickListener
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WishlistViewModel by viewModels()
    lateinit var accessToken: String

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter (object : TicketItemClickListener {
            override fun onItemClicked(item: TicketsItem) {
                val action = SearchResultFragmentDirections.actionSearchResultFragmentToKonfirmasiTiketFragment(item)
                findNavController().navigate(action)
            }

            override fun onUpdateMenuClicked(item: TicketsItem) {
                val action = SearchResultFragmentDirections.actionSearchResultFragmentToEditConfirmationTicketFragment()
                action.ticketsItem = item
                findNavController().navigate(action)
            }

            override fun onDeleteMenuClicked(id: Int) {
                showDeleteDialog(id)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observeData()
    }

    private fun observeData() {
        viewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            viewModel.getWishlist(getString(R.string.bearer_token, it))
            accessToken = it
        }
        viewModel.getWishlistTickets().observe(viewLifecycleOwner) {
            adapter.submitList(it.map { ticketsItemWishlist ->
                ticketsItemWishlist.toTicketsItem()
            })
        }
    }

    private fun initList() {
        binding.rcvTrip.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@WishlistFragment.adapter
        }
    }

    fun showDeleteDialog(id: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Ticket")
            .setMessage("Are you sure you want to delete this ticket?")
            .setPositiveButton("Delete") { _, _ ->
                deleteTicket(id)

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteTicket(id: Int) {
        viewModel.deleteTicketById(getString(R.string.bearer_token, accessToken), id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
