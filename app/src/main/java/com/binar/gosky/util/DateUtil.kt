package com.binar.gosky.util

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import com.binar.gosky.databinding.FragmentEditConfirmationTicketBinding
import com.binar.gosky.databinding.FragmentHomeBinding
import com.binar.gosky.presentation.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    lateinit var departureTime: String
    lateinit var returnTime: String

    val formattedMonth =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Des")

    fun showDatePickerDialog(id: Int, context: Context, homeBinding: FragmentHomeBinding? = null, editTicketBinding: FragmentEditConfirmationTicketBinding? = null) {

        val datePickerDialog = DatePickerDialog(
            context,
            { view, year, monthOfYear, dayOfMonth ->
                HomeFragment.day = dayOfMonth
                when (id) {
                    homeBinding?.etDepartureDate?.id -> {
                        homeBinding.etDepartureDate.setText(
                            "$dayOfMonth ${
                                formattedMonth.get(
                                    monthOfYear
                                )
                            }, $year"
                        )
                        departureTime = getTimeStamp(year, monthOfYear, dayOfMonth)
                    }
                    homeBinding?.etReturnDate?.id -> {
                        homeBinding.etReturnDate.setText("$dayOfMonth ${formattedMonth.get(monthOfYear)}, $year")
                        returnTime = getTimeStamp(year, monthOfYear, dayOfMonth)
                    }
                    editTicketBinding?.etDepartureDate?.id -> {
                        editTicketBinding.etDepartureDate.setText(
                            "$dayOfMonth ${
                                formattedMonth.get(
                                    monthOfYear
                                )
                            }, $year"
                        )
                        departureTime = getTimeStamp(year, monthOfYear, dayOfMonth)
                    }
                    editTicketBinding?.etReturnDate?.id -> {
                        editTicketBinding.etReturnDate.setText("$dayOfMonth ${formattedMonth.get(monthOfYear)}, $year")
                        returnTime = getTimeStamp(year, monthOfYear, dayOfMonth)
                    }
                }
            },
            HomeFragment.year,
            HomeFragment.month,
            HomeFragment.day
        )
        datePickerDialog.show()
    }

    fun getTimeStamp(year: Int, monthOfYear: Int, dayOfMonth: Int): String {
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

}