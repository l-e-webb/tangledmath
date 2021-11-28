package com.tangledwebgames.tangledmath

fun Int.isEven(): Boolean = (this % 2) == 0
fun Int.isOdd(): Boolean = !isEven()
fun Int.isPositive(): Boolean = this > 0
fun Int.isNegative(): Boolean = this < 0
fun Int.isNonPositive(): Boolean = this <= 0
fun Int.isNonNegative(): Boolean = this >= 0
fun Int?.isNullOrZero(): Boolean = this?.let { it == 0 } ?: true
fun Int?.isNotNullOrZero(): Boolean = !isNullOrZero()