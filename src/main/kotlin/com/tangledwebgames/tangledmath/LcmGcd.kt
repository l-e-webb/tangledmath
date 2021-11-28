package com.tangledwebgames.tangledmath

import kotlin.math.absoluteValue

/**
 * Returns the least common multiple of [a] and [b]. By convention, the returned
 * value is always non-negative.
 */
fun leastCommonMultiple(a: Int, b: Int): Int {
    if (a.isNegative() || b.isNegative()) {
        return leastCommonMultiple(a.absoluteValue, b.absoluteValue)
    }

    return when {
        a == 0 || b == 0 -> 0
        a == 1 -> b
        b == 1 -> a
        else -> {
            a * b / greatestCommonDivisor(a, b)
        }
    }
}

/**
 * See [leastCommonMultiple].
 */
fun lcm(a: Int, b: Int): Int = leastCommonMultiple(a, b)

/**
 * Returns the greatest common divisor of [a] and [b]. By convention, the
 * returned value is always non-negative. Returns 0 only if [a] and [b] are
 * both 0. (This is a formalism; 0 is divisible by every integer.)
 */
fun greatestCommonDivisor(a: Int, b: Int): Int {
    if (a.isNegative() || b.isNegative()) {
        return greatestCommonDivisor(a.absoluteValue, b.absoluteValue)
    }

    return when {
        a == 0 -> {
            b
        }
        b == 0 -> {
            a
        }
        a == b -> {
            a
        }
        a == 1 || b == 1 -> {
            1
        }
        a > b -> {
            greatestCommonDivisor(a - b, b)
        }
        else -> {
            greatestCommonDivisor(a, b - a)
        }
    }
}

/**
 * See [greatestCommonDivisor].
 */
fun gcd(a: Int, b: Int): Int = greatestCommonDivisor(a, b)