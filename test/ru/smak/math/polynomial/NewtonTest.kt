package ru.smak.math.polynomial

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class NewtonTest {
    @Test
    fun createEmptyNewton() {
        assertEquals(Polynomial(0.0), Newton())
    }

    @Test
    fun createNewton() {
        assertEquals(Polynomial(0.0, 0.0, 1.0), Newton(mapOf(-1.0 to 1.0, 0.0 to 0.0, 1.0 to 1.0)))
        assertEquals(Polynomial(1, 0, 0, 1), Newton(mapOf(-1.0 to 0.0, 0.0 to 1.0, 1.0 to 2.0, 2.0 to 9.0)))
    }


    // !!!
    @Test
    fun testAddDot() {
        val testAddPol = Newton()
        if (testAddPol.addDot(1.0, 2.0)){
            assertEquals(Polynomial(2.0), testAddPol)
        }
        if (testAddPol.addDot(3.0, 4.0)){
            assertEquals(Polynomial(1, 1), testAddPol)
        }
        // Exception:
        if (testAddPol.addDot(1.0, 11.5)){
            assertEquals(Polynomial(1, 1), testAddPol)
        }

    }

    @Test
    fun testRemoveDot() {
        val testRemPol = Newton(mapOf(-1.0 to 0.0, 0.0 to 1.0, 1.0 to 2.0, 2.0 to 9.0, 3.0 to 5.0))


        if (testRemPol.removeDot(3.0)){
            assertEquals(Polynomial(1, 0, 0, 1), testRemPol)
        }
        if (testRemPol.removeDot(1.0)){
            assertEquals(Polynomial(1, 2, 1), testRemPol)
        }
        // Exception:
        if (testRemPol.removeDot(9.0)){
            assertEquals(Polynomial(1, 1), testRemPol)
        }
    }

    @Test
    fun testCopy() {
        val testCopPol = Newton(mapOf(-1.0 to 0.0, 0.0 to 1.0, 1.0 to 2.0, 2.0 to 9.0))

        val copiedDots = testCopPol.dotsCopy()

        assertEquals(mapOf(-1.0 to 0.0, 0.0 to 1.0, 1.0 to 2.0, 2.0 to 9.0), copiedDots)
    }
}