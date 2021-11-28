package com.tangledwebgames.tangledmath.rational

import com.tangledwebgames.tangledmath.rational.Rational.Companion.rational

/* Rounding */

/**
 * Returns the largest integer less than or equal to this [Rational]. Equivalent
 * to [toInt].
 */
fun Rational.roundDown(): Int = toInt()

/**
 * Returns the smallest integer greater than or equal to this [Rational].
 */
fun Rational.roundUp(): Int = if (isIntegral()) {
    toInt()
} else {
    roundDown() + 1
}

/**
 * Returns the nearest integer to this [Rational]. If this [Rational] is
 * exactly halfway between two integers (e.g. 1 / 2), rounds Down.
 */
fun Rational.round(): Int {
    val partialFraction: Rational = this - roundDown()
    return if (partialFraction > 1 over 2) {
        roundUp()
    } else {
        roundDown()
    }
}

/* Iteration */

/**
 * Folds this sequence into a single [Rational]. Begins with [initial], then
 * applies [rationalizer] to each element in the sequence and multiplies the
 * result with the accumulated value.
 */
inline fun <T> Sequence<T>.rationalFold(
    initial: Rational = Rational.ONE,
    rationalizer: (T) -> Rational?
) = fold(initial) { ratio, item ->
    rationalizer(item)?.let {
        it * ratio
    } ?: ratio
}

/**
 * Folds this iterable into a single [Rational]. Begins with [initial], then
 * applies [rationalizer] to each element in the iterable and multiplies the
 * result with the accumulated value.
 */
inline fun <T> Iterable<T>.rationalFold(
    initial: Rational = Rational.ONE,
    rationalizer: (T) -> Rational?
) = asSequence().rationalFold(initial, rationalizer)

fun Sequence<Rational>.sum(): Rational = reduceOrNull { acc, rational ->
    acc + rational
} ?: Rational.ZERO


fun Iterable<Rational>.sum(): Rational = reduceOrNull { acc, rational ->
    acc + rational
} ?: Rational.ZERO

fun Sequence<Rational>.product(): Rational = reduceOrNull { acc, rational ->
    acc * rational
} ?: Rational.ONE

fun Iterable<Rational>.product(): Rational = reduceOrNull { acc, rational ->
    acc * rational
} ?: Rational.ONE


/* Integer operation extensions */

fun Int.toRational(): Rational = rational(this, 1)

/**
 * Returns the integer approximation of this times [ratio].
 */
fun Int.ratioOf(ratio: Rational) = (ratio * this).toInt()

/**
 * Infix fun for natural expression of rational values, e.g.
 *      val oneHalf = 1 over 2
 */
infix fun Int.over(other: Int) = rational(this, other)

/**
 * Creates a [Rational] number equivalent to [percent] / 100. Note that the
 * result will be in simplest form, so the denominator is not guaranteed to be
 * 100.
 */
fun percentage(percent: Int): Rational = rational(percent, 100)

/**
 * See [percentage].
 */
fun Int.percent(): Rational = percentage(this)

operator fun Int.plus(other: Rational) = other + this

operator fun Int.times(other: Rational) = other * this

operator fun Int.div(other: Rational) = other / this

