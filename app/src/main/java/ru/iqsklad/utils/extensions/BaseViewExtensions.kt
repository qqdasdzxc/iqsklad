package ru.iqsklad.utils.extensions

import android.view.View

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.hideAsGone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}