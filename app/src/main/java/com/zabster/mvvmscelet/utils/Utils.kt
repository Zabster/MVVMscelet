package com.zabster.mvvmscelet.utils

import android.annotation.SuppressLint
import com.zabster.mvvmscelet.db.entities.SomeDBModel
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String =
    SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()

fun formatSomeModel(someDBModel: List<SomeDBModel>): String {
    val str: StringBuilder = StringBuilder()
    someDBModel.map { m ->
        with(str) {
            append(m.name)
            append("\n")
            append("---")
            append(convertLongToDateString(m.date))
            append("\n")
            append("\n")
        }
    }
    return str.toString()
}
