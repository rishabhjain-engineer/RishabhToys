package com.example.rishabhtoys

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity::class] , version = 1, exportSchema = false)
abstract class RishabhToysDB : RoomDatabase() {

    abstract fun entityDao() : EntityDao

    companion object{

        @Volatile
        var INSTANCE : RishabhToysDB? = null

        fun getDatabase(context : Context) : RishabhToysDB? {

            if(INSTANCE == null){
                synchronized(RishabhToysDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext , RishabhToysDB::class.java, "rishabhToysDb")
                        .build()
                }
            }
            return INSTANCE
        }
    }

}