package exceptions

import exceptions.ColumnNotFoundException
import exceptions.NonNumericDataException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExceptionsTest {

    @Test
    fun testColumnNotFoundExceptionMessage() {
        val exception = ColumnNotFoundException("TestColumn")
        assertEquals("Column 'TestColumn' does not exist.", exception.message)
    }

    @Test
    fun testNonNumericDataExceptionMessage() {
        val exception = NonNumericDataException("TestColumn")
        assertEquals("Column 'TestColumn' contains non-numeric data.", exception.message)
    }
}