package com.business.rishabhtoys

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity()
class TxnHistoryEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "txnHistoryId")
    var txnHistoryId: Long? = null

    @NonNull
    @ColumnInfo(name = "entityId")
    var entityId: Long? = null

    @NonNull
    @ColumnInfo(name = "txnDate")
    lateinit var date: Date

    @NonNull
    @ColumnInfo(name = "txnAmount")
    var txnAmount: Float? = null

    //0 ->Opening Balance
    //1 ->Goods
    //2 ->Payment
    @NonNull
    @ColumnInfo(name = "txnType")
    var txnType: TxnType? = null

    @ColumnInfo(name = "remark")
    var remark: String? = null
}