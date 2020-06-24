package com.example.rishabhtoys

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Entity)

    @Query("SELECT companyName, id, totalAmount from Entity WHERE entityType = 0 ")
    fun getPurchaseEntity(): LiveData<List<EntityTransData>>

    @Query("SELECT companyName, id, totalAmount from Entity WHERE entityType = 1 ")
    fun getSaleEntity(): LiveData<List<EntityTransData>>

    @Query("SELECT companyName , id , totalAmount from Entity ")
    fun getListOfCompanyName(): List<EntityTransData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTxnLog(txnHistoryEntity: TxnHistoryEntity): Long

    @Query("SELECT Entity.*, TxnHistoryEntity.* FROM Entity INNER JOIN TxnHistoryEntity ON Entity.Id = TxnHistoryEntity.entityId WHERE Entity.Id =:entityId")
    fun getDetailInfoForEntity(entityId: Int): LiveData<List<DetailInfoForEntity>>

    @Query("UPDATE Entity SET totalAmount = :updatedAmount WHERE id = :entityId")
    fun updateTotalAmount(entityId: Int, updatedAmount: Float?)

    @Transaction
    fun insertAndUpdateTxn(
        txnHistoryEntity: TxnHistoryEntity,
        entityId: Int,
        updatedAmount: Float?
    ): Long {
        val float1: Long = insertTxnLog(txnHistoryEntity)
        updateTotalAmount(entityId, updatedAmount)
        return float1
    }

}