package com.example.practiceapp.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.clipToStatusBar() {
    val statusBarHeight = getStatusBarHeight(context)
    layoutParams.height += statusBarHeight
    setPadding(0, statusBarHeight, 0, 0)
}

fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Activity.hideKeyboard() {
    val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        inputManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
