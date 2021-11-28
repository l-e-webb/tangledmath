package com.tangledwebgames.tangledmath

import com.tangledwebgames.tangledmath.rational.Rational
import com.tangledwebgames.tangledmath.rational.div
import com.tangledwebgames.tangledmath.rational.roundDown
import com.tangledwebgames.tangledmath.rational.roundUp
import kotlin.math.max
import kotlin.math.min
import kotlin.ranges.IntProgression.Companion.fromClosedRange

infix fun IntRange.overlaps(other: IntRange): Boolean {
    return first in other || last in other || contains(other.first) || contains(other.last)
}

operator fun IntRange.contains(other: IntRange): Boolean {
    return first <= other.first && last >= other.last
}

infix fun Int.forWidth(width: Int): IntRange = this until this + width

infix fun IntRange.intersectRange(other: IntRange): IntRange {
    val first = max(first, other.first)
    return first..min(last, other.last).coerceAtLeast(first)
}

fun IntRange.offsetFrom(other: IntRange): Int {
    return when {
        overlaps(other) -> 0
        last < other.first -> other.first - last
        else -> first - other.last
    }
}

val IntRange.size: Int
    get() = last - first + 1

fun IntRange.asProgression(step: Int): IntProgression = fromClosedRange(first, last, step)

fun Int.aspectRange(minAspect: Rational, maxAspect: Rational): IntRange {
    if (minAspect > maxAspect) {
        throw IllegalArgumentException("minAspect $minAspect was larger than maxAspect $maxAspect")
    }
    val min = this.div(maxAspect).roundUp()
    val max = this.div(minAspect).roundDown()
    return min..max
}

fun Int.aspectRange(aspect: Rational): IntRange {
    return if (aspect > Rational.ONE) {
        aspectRange(aspect.reciprocal(), aspect)
    } else {
        aspectRange(aspect, aspect.reciprocal())
    }
}