package com.eliasjuniornino.budgetplanner.extensions

fun Double.toMoneyStr(currencySymbol: String = "R$"): String {
    val absValue = kotlin.math.abs(this)
    val intPart = absValue.toInt()
    val decimalPart = ((absValue - intPart) * 100).toInt()

    val intPartStr = intPart.toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()

    val decimalStr = decimalPart.toString().padStart(2, '0')

    val formatted = "$currencySymbol $intPartStr,$decimalStr"
    return if (this < 0) "-$formatted" else formatted
}