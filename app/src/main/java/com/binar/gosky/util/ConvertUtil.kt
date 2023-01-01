package com.binar.gosky.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

object ConvertUtil {
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertMinutesToHourAndMinutes(duration: Long): String {
        val durationTime = Duration.ofMinutes(duration)
        val hours = durationTime.toHours()
        val minutes = durationTime.minusHours(hours).toMinutes()

        return "${hours}h ${minutes}m"
    }

    fun convertRupiah(intPrice: Int?): String {
        val localId = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localId)
        return formatter.format(intPrice)
    }

    fun convertISOtoDateHoursMinute(isoString: String?, duration: Long = 0L): String {
        val localeID = Locale("in", "ID")
        var formattedDate = ""
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", localeID)
        try {
            cal.time = dateFormat.parse(isoString)
            cal.add(Calendar.MINUTE, duration.toInt())
            val c = cal.time
            val dformat = SimpleDateFormat("dd MMM yyyy\nHH:mm", localeID)
            formattedDate = dformat.format(c)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }

    fun convertIOStoDate(isoString: String?, duration: Long = 0L): String {
        val localeID = Locale("in", "ID")
        var formattedDate = ""
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", localeID)
        try {
            cal.time = dateFormat.parse(isoString)
            cal.add(Calendar.MINUTE, duration.toInt())
            val c = cal.time
            val dformat = SimpleDateFormat("dd MMM, yyyy", localeID)
            formattedDate = dformat.format(c)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }

    fun convertISOtoDay(isoString: String?): String {
        val localeID = Locale("in", "ID")
        var formattedDate = ""
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", localeID)
        try {
            cal.time = dateFormat.parse(isoString)
            val c = cal.time
            val dformat = SimpleDateFormat("EEEE, dd MMM yyyy", localeID)
            formattedDate = dformat.format(c)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }

    fun convertISOtoHour(isoString: String?, duration: Long = 0L): String {
        val localeID = Locale("in", "ID")
        var formattedDate = ""
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", localeID)
        try {
            cal.time = dateFormat.parse(isoString)
            cal.add(Calendar.MINUTE, duration.toInt())
            val c = cal.time
            val dformat = SimpleDateFormat("HH:mm", localeID)
            formattedDate = dformat.format(c)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }
}