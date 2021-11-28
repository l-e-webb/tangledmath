package com.tangledwebgames.tangledmath.rational

import com.tangledwebgames.tangledmath.greatestCommonDivisor
import kotlinx.serialization.Serializable

/**
 * Object representing a rational number; that is, a ratio of integers, [numerator] to
 * [denominator].
 *
 * The rational number system is a discrete, countable number system, just like the integers.
 * Counterintuitively, the set of rational numbers is set-isomorphic to the set of integers. Thus,
 * rational arithmetic can be expressed completely with integer arithmetic. This class, and
 * associated extensions, are an implementation of the rational number system using integers. They
 * allow for floating-point-error-free calculations using ratios.
 *
 * Notable caveats:
 *  - Naturally, the rational API cannot express irrational numbers such as pi or `sqrt(2)`, though
 *  floating point representations cannot truly express these quantities either.
 *  - Both [numerator] and [denominator] are subject to the innate size-limit on [Integer]s.
 *  Adding or multiplying rational numbers can result in quickly increasing component values if
 *  the resulting fractions cannot be reduced.
 *  - Operations on rational numbers automatically return values that are in simplest form, which
 *  enforces the following rules:
 *    - [denominator] is positive
 *    - [numerator] is not divisible by [denominator]
 *    - [numerator] and [denominator] have the smallest possible absolute value of all equivalent
 *    fractions
 *    - Note that the above imply that if [numerator] is 0, [denominator] must be 1
 *  - When instantiating [Rational] objects, it is recommended to use the [rational] factory function,
 *  which enforces the rules above.
 *
 *  @constructor Creates a new [Rational] object. It is recommended to use the [rational] factory
 *  function rather than the constructor directly, which ensures fractions are in simplest form.
 *  @throws [IllegalArgumentException] if [denominator] is 0.
 */
@Serializable
data class Rational(
    /**
     * The numerator of this rational number.
     */
    val numerator: Int,
    /**
     * The denominator of this rational number. Cannot be 0.
     */
    val denominator: Int = 1
) : Number(), Comparable<Rational> {

    companion object {
        val ZERO = Rational(numerator = 0, denominator = 1)
        val ONE = Rational(numerator = 1, denominator = 1)


        /**
         * Builder for [Rational] objects which ensures they are reduced fractions and that the
         * denominator is positive. It is recommended to use this function to instantiate [Rational]
         * objects rather than the constructor to enforce these rules.
         * @throws [IllegalArgumentException] if [denominator] is 0.
         */
        fun rational(numerator: Int, denominator: Int = 1): Rational {
            if (denominator == 0) {
                throw IllegalArgumentException("Denominator of rational number cannot be zero.")
            }
            var num = numerator
            var denom = denominator
            val gcd = greatestCommonDivisor(numerator, denominator)
            num /= gcd
            denom /= gcd
            if (denom < 0) {
                num = -numerator
                denom = -denominator
            }
            return Rational(num, denom)
        }

        /**
         * Returns a [Rational] number in simplest form, equivalent to the fraction [numerator] / [denominator].
         * Note that this function is equivalent to [rational].
         */
        fun reduce(numerator: Int, denominator: Int) = rational(numerator, denominator)
    }

    init {
        if (denominator == 0) {
            throw IllegalArgumentException("Denominator of rational number cannot be zero.")
        }
    }

    /**
     * Returns whether this [Rational] is an integer value (i.e. with denominator 1).
     */
    fun isIntegral(): Boolean {
        return denominator == 1 || numerator == 0
    }

    /**
     * Returns a reduced version of this [Rational] number, guaranteeing the following:
     *  - [denominator] is positive
     *  - [numerator] is not divisible by [denominator]
     *  - [numerator] and [denominator] both have the smallest possible absolute value for any
     *  equivalent fraction.
     *
     *  This operation is automatically applied to [Rational]s created with the [rational] function,
     *  and to those returned by other methods of this class.
     */
    fun reduce(): Rational {
        return reduce(numerator, denominator)
    }

    /**
     * Returns a reciprocal fraction, where [numerator] is the denominator and [denominator] is the
     * numerator.
     * @throws IllegalArgumentException if [numerator] is 0.
     */
    fun reciprocal(): Rational = rational(
        numerator = denominator,
        denominator = numerator
    )

    operator fun plus(other: Rational): Rational {
        return if (denominator == other.denominator) {
            rational(
                numerator = numerator + other.numerator,
                denominator = denominator
            )
        } else {
            rational(
                numerator = numerator * other.denominator + other.numerator * denominator,
                denominator = denominator * other.denominator
            )
        }
    }

    operator fun minus(other: Rational): Rational {
        return this + other * -1
    }

    operator fun plus(other: Int): Rational {
        return this + other.toRational()
    }

    operator fun minus(other: Int): Rational {
        return this - other.toRational()
    }

    operator fun times(other: Rational): Rational {
        return rational(
            numerator = numerator * other.numerator,
            denominator = denominator * other.denominator
        )
    }

    operator fun times(other: Int): Rational {
        return rational(
            numerator = numerator * other,
            denominator = denominator
        )
    }

    operator fun times(other: Float): Float {
        return numerator * other / denominator
    }

    operator fun div(other: Rational): Rational {
        return this * other.reciprocal()
    }

    operator fun div(other: Int): Rational {
        return this * rational(1, other)
    }

    override fun compareTo(other: Rational): Int {
        return if (isIntegral() && other.isIntegral()) {
            numerator.compareTo(other.numerator)
        } else {
            (numerator * other.denominator).compareTo(other.numerator * denominator)
        }
    }

    override fun toByte(): Byte {
        return toInt().toByte()
    }

    override fun toChar(): Char {
        return if (isIntegral()) {
            numerator.toChar()
        } else {
            toInt().toChar()
        }
    }

    /**
     * Returns a double-approximation of this [Rational]. Will introduce floating-point errors if
     * this is a repeating fraction (e.g. 1/3), or if the decimal representation of this fraction
     * is longer than the number of digits in a [Double].
     */
    override fun toDouble(): Double {
        return numerator.toDouble() / denominator
    }

    /**
     * Returns a float-approximation of this [Rational]. Will introduce floating-point errors if
     * this is a repeating fraction (e.g. 1/3), or if the decimal representation of this fraction
     * is longer than the number of digits in a [Float].
     */
    override fun toFloat(): Float {
        return numerator.toFloat() / denominator
    }

    /**
     * Returns the largest [Int] that is less than or equal to this [Rational].
     */
    override fun toInt(): Int {
        return numerator / denominator
    }

    /**
     * Returns the largest [Long] that is less than or equal to this [Rational]. Note that
     * [Rational] objects have the same max size as [Integer]s.
     */
    override fun toLong(): Long {
        return numerator.toLong() / denominator.toLong()
    }

    override fun toShort(): Short {
        return toInt().toShort()
    }
}