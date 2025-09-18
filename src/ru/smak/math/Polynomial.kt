package ru.smak.math

import kotlin.math.absoluteValue
import kotlin.math.sign

class Polynomial(coeffs: Map<Int, Double>) {
    private val _coeffs: MutableMap<Int, Double>

    val coeffs: Map<Int, Double>
        get() = _coeffs

    constructor() : this(mapOf(0 to 0.0))

    constructor(coeffs: List<Double>) : this(
        mutableMapOf<Int, Double>().also {
            coeffs.forEachIndexed { i, v ->
                it[i] = v
            }
        }
    )

    constructor(vararg coeffs: Double) : this(coeffs.toList())
    constructor(vararg coeffs: Int): this(coeffs.map { it.toDouble() })

    init{
        _coeffs = coeffs.filter{ (k, v) -> k >= 0 && v neq 0.0 }
            .toMutableMap().also{
                if (it.isEmpty()) it[0] = 0.0
            }
    }

    override fun toString() = buildString {
        val maxPow = coeffs.keys.max()
        coeffs.toSortedMap().reversed().forEach { (k, v) ->
            append( if (v.sign < 0) "-" else if (k != maxPow) "+" else "")
            append(if (v.absoluteValue neq 1.0 || k == 0) v.absoluteValue else "")
            if (k != 0) append("x")
            if (k > 1) append("^", k)
        }
    }
}

