package com.example.rishabhtoys

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
class Entity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Long? = null ;

    @NonNull
    @ColumnInfo(name = "companyName")
    lateinit var companyName: String

    @NonNull
    @ColumnInfo(name = "companyOwnerName")
    lateinit var companyOwner: String

    @ColumnInfo(name = "companyAddress")
    lateinit var companyAddress: String

    @NonNull
    @ColumnInfo(name = "primaryContactNo")
    lateinit var primaryContactNo: String

    @ColumnInfo(name = "alternateContactNo")
    lateinit var altContactNo: String

    @ColumnInfo(name = "gstNo")
    lateinit var gstNo: String

    @NonNull
    @ColumnInfo(name = "totalAmount")
    var totalAmount: Float? = null

    // 0 -> Purchase : Buyer
    // 1 -> Sale : vendor
    @NonNull
    @ColumnInfo(name = "entityType")
    var entityType: Int? = null


    // 0 -> Debit
    // 1 -> Credit
    @NonNull
    @ColumnInfo(name = "amountType")
    var txnType: Int? = null

    @NonNull
    @ColumnInfo(name = "dateOfCreation")
    lateinit var txnDateTime: String

    @ColumnInfo(name = "isActive")
    var isActive: Boolean = true


}