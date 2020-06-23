package com.example.rishabhtoys

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class DetailEntityViewModel(application: Application) : AndroidViewModel(application) {

    var repository: Repository = Repository(application)


    fun getDetailEntityInfo(entityId: Int?) {
        repository.getDetailInfoForEntity(entityId!!)
    }

    fun getData() : LiveData<List<DetailInfoForEntity>>? {
        return repository.getEntityDetailList()
    }

}