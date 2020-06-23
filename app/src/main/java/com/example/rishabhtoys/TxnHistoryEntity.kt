package com.example.rishabhtoys

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
class TxnHistoryEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "txnHistoryId")
    var txnHistoryId: Long? = null

    @NonNull
    @ColumnInfo(name = "entityId")
    var entityId: Int? = null

    @NonNull
    @ColumnInfo(name = "txnDate")
    lateinit var date: String

    @NonNull
    @ColumnInfo(name = "txnAmount")
    var txnAmount: Float? = null

    //0 ->Debit
    //1->Credit
    @NonNull
    @ColumnInfo(name = "txnType")
    var txnType: Int? = null

    @ColumnInfo(name = "remark")
    lateinit var remark: String

}