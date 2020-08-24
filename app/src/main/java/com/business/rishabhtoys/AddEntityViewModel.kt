package com.business.rishabhtoys

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AddEntityViewModel(application: Application) : AndroidViewModel(application) {

    var repository:Repository = Repository(application)
    var queryStatus:Long? = null

    suspend fun createEntityAndInsertTxnHistory(entity: Entity, txnHistoryEntity: TxnHistoryEntity) : Long?{
        queryStatus = repository.createEntityAndInsertTxnHistory(entity,txnHistoryEntity)
        return queryStatus
    }

}