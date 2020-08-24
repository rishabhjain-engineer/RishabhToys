package com.business.rishabhtoys

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Entity): Long

    @Query("SELECT companyName, id, totalAmount, dateOfCreation from Entity WHERE entityType = 0 ")
    fun getPurchaseEntity(): LiveData<List<EntityTransData>>

    @Query("SELECT companyName, id, totalAmount, dateOfCreation from Entity WHERE entityType = 1 ")
    fun getSaleEntity(): LiveData<List<EntityTransData>>

    @Query("SELECT companyName , id , totalAmount, dateOfCreation from Entity ")
    fun getListOfCompanyName(): List<EntityTransData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTxnLog(txnHistoryEntity: TxnHistoryEntity): Long

    @Query("SELECT Entity.*, TxnHistoryEntity.* FROM Entity INNER JOIN TxnHistoryEntity ON Entity.Id = TxnHistoryEntity.entityId WHERE Entity.Id =:entityId ORDER BY TxnHistoryEntity.txnDate ASC")
    fun getDetailInfoForEntity(entityId: Long): LiveData<List<DetailInfoForEntity>>

    @Query("UPDATE Entity SET totalAmount = :updatedAmount WHERE id = :entityId")
    fun updateTotalAmount(entityId: Long, updatedAmount: Float?)

    @Transaction
    fun insertAndUpdateTxn(
        txnHistoryEntity: TxnHistoryEntity,
        entityId: Long,
        updatedAmount: Float?
    ): Long {
        val float1: Long = insertTxnLog(txnHistoryEntity)
        updateTotalAmount(entityId, updatedAmount)
        return float1
    }

    @Transaction
    fun createEntityAndInsertTxnHistory(entity: Entity, txnHistoryEntity: TxnHistoryEntity): Long {
        val id = insert(entity)
        txnHistoryEntity.entityId = id
        val long = insertTxnLog(txnHistoryEntity)
        return long
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addItemToRateList(rateListEntity : RateListEntity): Long

    @Query("SELECT * FROM RateListEntity WHERE purchaseId = :purchaseId")
    fun getRateItemList(purchaseId : Long) : LiveData<List<RateListEntity>>

    @Query("SELECT * FROM ENTITY WHERE id = :id")
    fun getEntityBasicDetail(id : Long) : Entity

    @Update
    fun updateEntity(entity : Entity) : Int

}