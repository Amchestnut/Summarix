package specification

import exceptions.ColumnNotFoundException
import exceptions.NonNumericDataException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CalculationTest {

    private val calculation = object : Calculation {}

    @Test
    fun testSumWithValidData() {
        val data = listOf(1, 2, 3)
        val result = calculation.sum(data, "Numbers")
        assertEquals(6.0, result)
    }

    @Test
    fun testSumWithNonNumericData() {
        val data = listOf(1, "a", 3)
        assertThrows(NonNumericDataException::class.java) {
            calculation.sum(data, "MixedData")
        }
    }

    @Test
    fun testAverageWithValidData() {
        val data = listOf(2, 4, 6)
        val result = calculation.average(data, "Numbers")
        assertEquals(4.0, result)
    }

    @Test
    fun testAverageWithNonNumericData() {
        val data = listOf(2, "b", 6)
        assertThrows(NonNumericDataException::class.java) {
            calculation.average(data, "MixedData")
        }
    }

    @Test
    fun testCount() {
        val data = listOf("a", "b", "c")
        val result = calculation.count(data)
        assertEquals(3, result)
    }

    @Test
    fun testCountIf() {
        val data = listOf(1, 2, 3, 4, 5)
        val result = calculation.countIf(data) { (it as Int) > 2 }
        assertEquals(3, result)
    }

    @Test
    fun testSumIf() {
        val data = listOf(1, 2, 3, 4, 5)
        val result = calculation.sumIf(data, "Numbers") { (it as Int) % 2 == 0 }
        assertEquals(6.0, result)
    }

    @Test
    fun testAverageIf() {
        val data = listOf(1, 2, 3, 4, 5)
        val result = calculation.averageIf(data, "Numbers") { (it as Int) % 2 != 0 }
        assertEquals(3.0, result)
    }
}
