package com.example.rishabhtoys

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DetailEntityViewModel : ViewModel() {

    lateinit var entityData : LiveData<Entity>

    fun getEntity() : LiveData<Entity> {
        return entityData
    }


}