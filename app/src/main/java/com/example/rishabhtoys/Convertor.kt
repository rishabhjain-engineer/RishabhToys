package com.example.rishabhtoys

import androidx.room.TypeConverter

class Convertor {

    @TypeConverter
    fun fromTxnType(value: TxnType): Int{
        return value.ordinal
    }

    @TypeConverter
    fun toTxnType(value: Int): TxnType?{
        return when(value){
            0 -> TxnType.OPENING_BALANCE
            1 -> TxnType.GOODS
            2 -> TxnType.PAYMENT
            else -> null
        }
    }
}