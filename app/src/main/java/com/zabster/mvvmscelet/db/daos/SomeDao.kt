package com.zabster.mvvmscelet.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.zabster.mvvmscelet.db.entities.SomeDBModel

@Dao
abstract class SomeDao: BaseDao<SomeDBModel>() {

    @Query("SELECT * FROM some_table_name")
    abstract fun getAll(): LiveData<List<SomeDBModel>>

}