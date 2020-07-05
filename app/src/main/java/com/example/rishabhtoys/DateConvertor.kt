package com.example.rishabhtoys

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConvertor {

    @TypeConverter
    fun fromStringToDate(value : String): Date? {
        return Utils.getDateFromString(value)
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        val formatter = SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH)
        return formatter.format(date)
    }

}