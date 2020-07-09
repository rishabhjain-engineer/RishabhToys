package com.business.rishabhtoys

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RateListEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rateListId")
    var rateListId: Long? = null

    // Id of entity whose entity type is : purchase
    @NonNull
    @ColumnInfo(name = "purchaseId")
    var purchaseId: Long? = null

    @NonNull
    @ColumnInfo(name = "itemName")
    lateinit var itemName: String

    @NonNull
    @ColumnInfo(name = "itemPrice")
    var itemPrice: Float? = null

}