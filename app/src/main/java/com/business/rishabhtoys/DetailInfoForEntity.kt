package com.business.rishabhtoys

import androidx.room.Embedded

data class DetailInfoForEntity(
    @Embedded
    var entity: Entity,
    @Embedded
    var txnHistoryEntity: TxnHistoryEntity
    ) {

}