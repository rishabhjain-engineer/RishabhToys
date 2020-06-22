package com.example.rishabhtoys

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Entity)

    @Query("DELETE FROM entity_table")
    fun deleteAll()

    @Query("SELECT * from entity_table")
    fun getAllEntities(): LiveData<List<Entity>>

    @Query("SELECT * from entity_table WHERE Entity_Type = 0 ")
    fun getPurchaseEntity(): LiveData<List<Entity>>

    @Query("SELECT * from entity_table WHERE Entity_Type = 1 ")
    fun getSaleEntity(): LiveData<List<Entity>>

    @Query("SELECT * from entity_table WHERE Company_Name = :companyName")
    fun getEntity(companyName: String?): Entity

    @Query("SELECT Company_Name , Id , Txn_Amount from entity_table")
    fun getListOfCompanyName(): List<EntityTransData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTxnLog(txnHistoryEntity : TxnHistoryEntity)


    @Query("SELECT entity_table.Company_Name, entity_table.Company_Owner_Name,TxnHistory.TxnAmount FROM entity_table INNER JOIN TxnHistory ON entity_table.Id = TxnHistory.Entity_Id WHERE entity_table.Id =:entityId")
    fun getDetailInfoForEntity(entityId: Int) : List<DetailInfoForEntity>

}