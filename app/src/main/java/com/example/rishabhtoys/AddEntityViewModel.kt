package com.example.rishabhtoys

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class AddEntityViewModel(application: Application) : AndroidViewModel(application) {

    var repository:Repository = Repository(application)

    init {
        Log.e("Rishabh","ViewModel created")
    }

    suspend fun insertEntity(entity: Entity){
        repository.insert(entity)
    }

}