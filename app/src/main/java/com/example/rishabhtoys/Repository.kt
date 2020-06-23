package com.example.rishabhtoys

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(application: Application) {

    private var mEntityDao: EntityDao?

    var mPurchaseEntities: LiveData<List<EntityTransData>>? = null
    var mSaleEntities: LiveData<List<EntityTransData>>? = null
    private var mListOfCompanyName: List<EntityTransData>? = ArrayList()
    private var listOfDetailEntityInfo: LiveData<List<DetailInfoForEntity>>? = null
    private var insertQueryStatus : Long? = 0

    init {
        val db: RishabhToysDB? = RishabhToysDB.getDatabase(application)
        mEntityDao = db?.entityDao()

    }

    suspend fun insert(entity: Entity) {
        withContext(Dispatchers.IO) {
            mEntityDao?.insert(entity)
        }
    }

    suspend fun getCompanyNameList(): List<EntityTransData>? {
        withContext(Dispatchers.IO) {
            mListOfCompanyName = mEntityDao?.getListOfCompanyName()
        }
        return mListOfCompanyName
    }

    suspend fun insertTxnLog(txnHistoryEntity: TxnHistoryEntity) : Long? {
        withContext(Dispatchers.IO) {
            insertQueryStatus = mEntityDao?.insertTxnLog(txnHistoryEntity)
        }

        return insertQueryStatus
    }

    fun getDetailInfoForEntity(id: Int) {
        listOfDetailEntityInfo = mEntityDao?.getDetailInfoForEntity(id)
    }

    fun getPurchaseEntity() {
        mPurchaseEntities = mEntityDao?.getPurchaseEntity()
    }

    fun getSaleEntity() {
        mSaleEntities = mEntityDao?.getSaleEntity()
    }

    fun getEntityDetailList() : LiveData<List<DetailInfoForEntity>>?{
        return listOfDetailEntityInfo
    }

}