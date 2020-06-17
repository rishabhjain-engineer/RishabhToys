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
    private var mEntity:Entity? = null

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

    suspend fun getEntity(companyName : String?) : Entity?{
        withContext(Dispatchers.IO){
            mEntity = mEntityDao?.getEntity(companyName)
            Log.e("Rishabh","value received: "+mEntity?.companyName)
            Log.e("Rishabh","value received: "+mEntity?.gstNo)
        }
        return mEntity
    }

    fun getEntity() : Entity? {
        return mEntity
    }

}