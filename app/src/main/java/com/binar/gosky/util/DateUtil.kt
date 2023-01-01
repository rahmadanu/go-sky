package com.binar.gosky.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.TimePicker
import com.binar.gosky.databinding.FragmentEditConfirmationTicketBinding
import com.binar.gosky.databinding.FragmentHomeBinding
import com.binar.gosky.presentation.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

object DateUtil {
    lateinit var departureTime: String
    var returnTime: String? = null

    private val calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)


    val formattedMonth =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Des")

    fun showDatePickerDialog(id: Int, context: Context, homeBinding: FragmentHomeBinding? = null, editTicketBinding: FragmentEditConfirmationTicketBinding? = null) {

        val datePickerDialog = DatePickerDialog(
            context,
            { view, year, monthOfYear, dayOfMonth ->
                this.year = year
                month = monthOfYear
                day = dayOfMonth
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
                        showTimePickerDialog(id, context, editTicketBinding, year, monthOfYear, dayOfMonth)
                    }
                    editTicketBinding?.etReturnDate?.id -> {
                        showTimePickerDialog(id, context, editTicketBinding, year, monthOfYear, dayOfMonth)
                    }
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    fun showTimePickerDialog(id: Int, context: Context, editTicketBinding: FragmentEditConfirmationTicketBinding, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val timePickerDialog = TimePickerDialog(
            context,
            { view, hourOfDay, minute ->
                when (id) {
                    editTicketBinding.etDepartureDate.id -> {
                        editTicketBinding.etDepartureDate.setText(
                            "$dayOfMonth ${formattedMonth.get(monthOfYear)} $year at $hourOfDay:$minute WIB"
                        )
                        departureTime = getTimeStamp(year, monthOfYear, dayOfMonth, hourOfDay, minute)
                    }
                    editTicketBinding.etReturnDate.id -> {
                        editTicketBinding.etReturnDate.setText(
                            "$dayOfMonth ${formattedMonth.get(monthOfYear)} $year at $hourOfDay:$minute WIB"
                        )
                        returnTime = getTimeStamp(year, monthOfYear, dayOfMonth, hourOfDay, minute)
                    }
                }
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    fun getTimeStamp(year: Int, monthOfYear: Int, dayOfMonth: Int, hour: Int = 0, minute: Int = 0): String {
        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val localeID = Locale("in", "ID")
        val formattedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", localeID).format(date)
        Log.d("timestamp", formattedDate.toString())

        return formattedDate
    }

}