package com.example.rishabhtoys

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(application: Application) {

    private var mEntityDao:EntityDao?

    private var mAllEntities:LiveData<List<Entity>>? = null
    private var mPurchaseEntities:LiveData<List<Entity>>? = null
    private var mSaleEntities:LiveData<List<Entity>>? = null

    init {
        val db:RishabhToysDB? = RishabhToysDB.getDatabase(application)
        mEntityDao = db?.entityDao()
        mAllEntities = mEntityDao?.getAllEntities()
        mPurchaseEntities = mEntityDao?.getPurchaseEntity()
        mSaleEntities = mEntityDao?.getSaleEntity()
    }


    fun getAllEntities():LiveData<List<Entity>>?{
        return mAllEntities
    }

    fun getPurchaseEntities():LiveData<List<Entity>>? {
        return mPurchaseEntities
    }

    fun getSaleEntities():LiveData<List<Entity>>? {
        return mSaleEntities
    }

    suspend fun insert(entity: Entity){
        withContext(Dispatchers.IO) {
            mEntityDao?.insert(entity)
        }
    }

}