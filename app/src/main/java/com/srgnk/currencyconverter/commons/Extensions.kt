package com.srgnk.currencyconverter.commons

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.*

fun Date.formattedDate(): String {
    val formatter = SimpleDateFormat("dd.MM.yy  HH:mm", Locale.ROOT)
    return formatter.format(this)
}

fun Fragment.getNavigationResult(key: String = SELECTED_CURRENCY) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

fun Fragment.setNavigationResult(result: String, key: String = SELECTED_CURRENCY) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}