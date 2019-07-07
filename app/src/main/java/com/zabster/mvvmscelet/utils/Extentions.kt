package com.zabster.mvvmscelet.utils

import android.view.View

fun View?.visible() {
    this?.visibility = View.VISIBLE
    this?.isEnabled = true
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
    this?.isEnabled = false
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.setVisability(isVisible: Boolean) {
    if (this == null) return
    if (isVisible) visible()
    else gone()
}

fun View?.setInvisability(isVisible: Boolean) {
    if (this == null) return
    if (isVisible) visible()
    else invisible()
}