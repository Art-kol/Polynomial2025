package ru.smak.math

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PolynomialTest {
    @Test
    fun getCoeffs() {
        assertEquals(mapOf(0 to 0.0), Polynomial().coeffs)

        assertEquals(mapOf(0 to 1.0), Polynomial(1).coeffs)
        assertEquals(mapOf(2 to 1.0), Polynomial(0,0,1).coeffs)

        assertEquals(mapOf(0 to 0.0), Polynomial(mapOf(-1 to 1.0)).coeffs)

        assertEquals(mapOf(0 to 1.0, 2 to -2.0, 3 to 3.0), Polynomial(listOf(1.0, 0.0, -2.0, 3.0)).coeffs)

    }

    @Test
    fun toStringTest() {
        assertEquals("0.0", Polynomial().toString())

        assertEquals("x", Polynomial(0, 1).toString())
        assertEquals("-x", Polynomial(0, -1).toString())

        assertEquals("1.0", Polynomial(1).toString())
        assertEquals("-1.0", Polynomial(-1).toString())

        assertEquals("2.5", Polynomial(2.5).toString())
        assertEquals("-2.5", Polynomial(-2.5).toString())

        assertEquals("x^10", Polynomial(mapOf(10 to 1.0)).toString())
        assertEquals("-x^10", Polynomial(mapOf(10 to -1.0)).toString())

        assertEquals("3.0x^3-2.0x^2+1.0", Polynomial(1, 0, -2, 3).toString())

        assertEquals("x", Polynomial(0, 1, 0).toString())
    }

}