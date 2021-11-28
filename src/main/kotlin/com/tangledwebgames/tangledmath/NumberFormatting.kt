package com.tangledwebgames.tangledmath

import com.tangledwebgames.tangledmath.rational.Rational

fun Int.toStringWithSign(): String = if (this >= 0) {
    "+$this"
} else {
    this.toString()
}

fun Rational.toStringAsPercentage(withSign: Boolean = false): String {
    val p = (numerator * 100) / denominator
    return if (withSign) {
        p.toStringWithSign()
    } else {
        p.toString()
    } + "%"
}