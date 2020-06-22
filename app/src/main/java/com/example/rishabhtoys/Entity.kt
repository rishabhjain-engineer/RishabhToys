package com.example.rishabhtoys

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entity_table")
class Entity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    var id: Int = 0;

    @NonNull
    @ColumnInfo(name = "Company_Name")
    lateinit var companyName: String

    @NonNull
    @ColumnInfo(name = "Company_Owner_Name")
    lateinit var companyOwner: String

    @ColumnInfo(name = "Company_Address")
    lateinit var companyAddress: String

    @NonNull
    @ColumnInfo(name = "Primary_Contact_No")
    lateinit var primaryContactNo: String

    @ColumnInfo(name = "Alternate_Contact_No")
    lateinit var altContactNo: String


    @ColumnInfo(name = "GST_No")
    lateinit var gstNo: String

    @NonNull
    @ColumnInfo(name = "Txn_Amount")
    var amount: Float = 0F

    // 0 -> Purchase : Buyer
    // 1 -> Sale : vendor
    @NonNull
    @ColumnInfo(name = "Entity_Type")
    var entityType: Int = 0


    // 0 -> Debit
    // 1 -> Credit
    @NonNull
    @ColumnInfo(name = "Txn_Type")
    var txnType: Int = 0

    @NonNull
    @ColumnInfo(name = "txn_Date_Time")
    lateinit var txnDateTime: String

    @ColumnInfo(name = "isActive")
    var isActive: Boolean = true


}