package com.zabster.mvvmscelet.ui.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zabster.mvvmscelet.db.AppDatabase
import com.zabster.mvvmscelet.ui.abstracts.BaseViewModel
import com.zabster.mvvmscelet.utils.formatSomeModel

class FirstViewModel(db: AppDatabase): BaseViewModel() {

    private val _dates = db.someDao().getAll()
    val someStringData: LiveData<String> =
        Transformations.map(_dates) { m -> formatSomeModel(m) }

}