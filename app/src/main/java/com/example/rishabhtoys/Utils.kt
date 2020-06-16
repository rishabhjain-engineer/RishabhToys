package com.example.rishabhtoys

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {

    companion object {
        fun getTxnDateTime() : String{
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH)
            return formatter.format(date)
        }
    }
}