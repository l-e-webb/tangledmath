package com.tangledwebgames.tangledmath.random

import kotlin.random.Random

/**
 * Returns a random item from the first components of [options], with each weighted by the
 * corresponding second component.
 */
fun <T> Random.weightedRandomChoice(
    vararg options: Pair<T, Int>
): T {
    return weightedRandomChoice(options.toMap())
}

/**
 * Returns a randomly selected key from [map], where each key is given a weight equal to it's
 * associated value.
 */
fun <T> Random.weightedRandomChoice(
    options: Map<T, Int>
): T {
    if (options.isEmpty()) {
        throw IllegalArgumentException("Cannot take a weighted random choice of an empty map.")
    }
    val total = options.values.sum()
    val choiceCutoff = nextInt(until = total)

    var runningSum = 0
    for ((item, weight) in options) {
        runningSum += weight
        if (runningSum > choiceCutoff) {
            return item
        }
    }
    return options.keys.random()
}

/**
 * Returns a randomly selected item from this collection, where each item is weighted by the return
 * value of [weightSelector] applied to it.
 */
fun <T> Collection<T>.weightedRandomChoiceBy(
    random: Random = Random.Default,
    weightSelector: (T) -> Int
): T = random.weightedRandomChoice(associateWith(weightSelector))

/**
 * Returns a randomly selected key from this map, where each key is given a weight equal to it's
 * associated value.
 */
fun <T> Map<T, Int>.weightedRandomChoice(random: Random = Random.Default): T {
    return random.weightedRandomChoice(this)
}

/**
 * Returns a random item from the first components of [options], with each weighted by the
 * corresponding second component.
 */
@JvmName(name = "weightedRandomChoiceFloat")
fun <T> Random.weightedRandomChoice(
    vararg options: Pair<T, Float>
): T = weightedRandomChoice(options.toMap())

/**
 * Returns a randomly selected key from [map], where each key is given a weight equal to it's
 * associated value.
 */
@JvmName(name = "weightedRandomChoiceFloat")
fun <T> Random.weightedRandomChoice(
    options: Map<T, Float>
): T {
    if (options.isEmpty()) {
        throw IllegalArgumentException("Cannot take a weighted random choice of an empty map.")
    }
    val total = options.values.sum()
    val choiceCutoff = nextFloat() * total

    var runningSum = 0.0
    for ((item, weight) in options) {
        runningSum += weight
        if (runningSum > choiceCutoff) {
            return item
        }
    }
    return options.keys.random()
}

/**
 * Returns a randomly selected item from this collection, where each item is weighted by the return
 * value of [weightSelector] applied to it.
 */
fun <T> Collection<T>.weightedRandomChoiceByFloat(
    random: Random = Random.Default,
    weightSelector: (T) -> Float
): T = random.weightedRandomChoice(associateWith(weightSelector))

/**
 * Returns a randomly selected key from this map, where each key is given a weight equal to it's
 * associated value.
 */
@JvmName(name = "weightedRandomChoiceFloat")
fun <T> Map<T, Float>.weightedRandomChoice(random: Random = Random.Default): T {
    return random.weightedRandomChoice(this)
}