package com.zabster.mvvmscelet.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zabster.mvvmscelet.db.daos.SomeDao
import com.zabster.mvvmscelet.db.entities.SomeDBModel

@Database(entities = [SomeDBModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun someDao(): SomeDao
}