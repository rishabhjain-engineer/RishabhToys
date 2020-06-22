package com.example.rishabhtoys

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigInteger

@Entity(tableName = "TxnHistory")
class TxnHistoryEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0

    @NonNull
    @ColumnInfo(name = "Entity_Id")
    var entityId:Int = 0

    @NonNull
    @ColumnInfo(name = "TxnDate")
    lateinit var date:String

    @NonNull
    @ColumnInfo(name = "TxnAmount")
    var txnAmount:Float = 0F

    //0 ->Debit
    //1->Credit
    @NonNull
    @ColumnInfo(name = "TxnType")
    var txnType:Int = 0

    @ColumnInfo(name = "isActive")
    var isActive:Boolean = true

    @ColumnInfo(name = "Remark")
    lateinit var remark:String

}