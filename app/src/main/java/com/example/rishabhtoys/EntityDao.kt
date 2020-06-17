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

    @Query("SELECT * from entity_table WHERE Entity_Type = 'Purchase'")
    fun getPurchaseEntity() : LiveData<List<Entity>>

    @Query("SELECT * from entity_table WHERE Entity_Type = 'Sale'")
    fun getSaleEntity() : LiveData<List<Entity>>

    @Query("SELECT * from entity_table WHERE Company_Name = :companyName")
    fun getEntity(companyName: String?) : Entity


}