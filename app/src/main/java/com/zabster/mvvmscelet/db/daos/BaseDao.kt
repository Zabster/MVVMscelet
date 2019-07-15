package com.zabster.mvvmscelet.db.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

abstract class BaseDao<T> {
    @Insert
    abstract fun insert(data: T)

    @Insert
    abstract fun insertList(datas: List<T>)

    @Update
    abstract fun update(data: T)

    @Delete
    abstract fun delete(data: T)
}