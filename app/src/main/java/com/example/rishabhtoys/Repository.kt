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
    private var insertAndUpdateQueryStatus: Long? = 0
    private var createAndInsertQueryStatus: Long? = 0
    private var addItemQueryStatus: Long? = 0

    init {
        val db: RishabhToysDB? = RishabhToysDB.getDatabase(application)
        mEntityDao = db?.entityDao()

    }

    suspend fun createEntityAndInsertTxnHistory(entity: Entity , txnHistoryEntity: TxnHistoryEntity) : Long?{
        withContext(Dispatchers.IO) {
            createAndInsertQueryStatus = mEntityDao?.createEntityAndInsertTxnHistory(entity,txnHistoryEntity)
        }

        return createAndInsertQueryStatus
    }

    suspend fun getCompanyNameList(): List<EntityTransData>? {
        withContext(Dispatchers.IO) {
            mListOfCompanyName = mEntityDao?.getListOfCompanyName()
        }
        return mListOfCompanyName
    }


    fun getDetailInfoForEntity(id: Long) {
        listOfDetailEntityInfo = mEntityDao?.getDetailInfoForEntity(id)
    }

    fun getPurchaseEntity() {
        mPurchaseEntities = mEntityDao?.getPurchaseEntity()
    }

    fun getSaleEntity() {
        mSaleEntities = mEntityDao?.getSaleEntity()
    }

    fun getEntityDetailList(): LiveData<List<DetailInfoForEntity>>? {
        return listOfDetailEntityInfo
    }

    suspend fun insertAndUpdate(
        txnHistoryEntity: TxnHistoryEntity,
        entityId: Long,
        totalAmount: Float
    ): Long? {
        withContext(Dispatchers.IO) {
            insertAndUpdateQueryStatus =
                mEntityDao?.insertAndUpdateTxn(txnHistoryEntity, entityId, totalAmount)
        }
        return insertAndUpdateQueryStatus
    }

    suspend fun addItemToRateList(rateListEntity: RateListEntity) : Long?{
        withContext(Dispatchers.IO){
            addItemQueryStatus = mEntityDao?.addItemToRateList(rateListEntity)
        }
        return addItemQueryStatus
    }

    fun getRateList(purchaseId: Long) : LiveData<List<RateListEntity>>? {
        return mEntityDao?.getRateItemList(purchaseId)
    }

}