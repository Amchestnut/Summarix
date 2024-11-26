package specification

import exceptions.ColumnNotFoundException
import exceptions.NonNumericDataException

/**
 * Interface for performing basic calculations and conditional operations on data.
 */
interface Calculation {

    /**
     * Checks if the provided data is non-empty; throws an exception if empty.
     * @param data The list of data to check.
     * @param columnName The name of the column being validated.
     */
    private fun validateData(data: List<Any>, columnName: String) {
        if (data.isEmpty()) {
            throw ColumnNotFoundException(columnName)
        }
    }

    /**
     * Calculates the sum of numeric values in the specified data column.
     * @param data List of values to sum.
     * @param columnName The name of the column being summed.
     * @return The sum of the values.
     * @throws NonNumericDataException if any data is non-numeric.
     */
    fun sum(data: List<Any>, columnName: String): Double {
        validateData(data, columnName)
        val numbers = data.filterIsInstance<Number>().map { it.toDouble() }
        if (numbers.size != data.size) {
            throw NonNumericDataException(columnName)
        }
        return numbers.sum()
    }

    /**
     * Calculates the average of numeric values in the specified data column.
     * @param data List of values to average.
     * @param columnName The name of the column.
     * @return The average of the values.
     * @throws NonNumericDataException if any data is non-numeric.
     */
    fun average(data: List<Any>, columnName: String): Double {
        validateData(data, columnName)
        val numbers = data.filterIsInstance<Number>().map { it.toDouble() }
        if (numbers.size != data.size) {
            throw NonNumericDataException(columnName)
        }
        return numbers.average()
    }

    /**
     * Counts the total number of items in the provided data list.
     * @param data The list to count.
     * @return The count of items.
     */
    fun count(data: List<Any>): Int = data.size

    /**
     * Counts the number of items in the data list that meet the specified condition.
     * @param data The list to evaluate.
     * @param condition A predicate that each item is tested against.
     * @return The count of items that satisfy the condition.
     */
    fun countIf(data: List<Any>, condition: (Any) -> Boolean): Int = data.count(condition)

    /**
     * Calculates the sum of items that meet the specified condition.
     * @param data List of values.
     * @param columnName The name of the column.
     * @param condition A predicate that each item is tested against.
     * @return The sum of values meeting the condition.
     * @throws NonNumericDataException if filtered data contains non-numeric values.
     */
    fun sumIf(data: List<Any>, columnName: String, condition: (Any) -> Boolean): Double {
        validateData(data, columnName)
        val filteredNumbers = data.filter(condition).mapNotNull { it.toString().toDoubleOrNull() }
        if (filteredNumbers.isEmpty()) {
            throw NonNumericDataException(columnName)
        }
        return filteredNumbers.sum()
    }

    /**
     * Calculates the average of items that meet the specified condition.
     * @param data List of values.
     * @param columnName The name of the column.
     * @param condition A predicate that each item is tested against.
     * @return The average of values meeting the condition.
     * @throws NonNumericDataException if filtered data contains non-numeric values.
     */
    fun averageIf(data: List<Any>, columnName: String, condition: (Any) -> Boolean): Double {
        validateData(data, columnName)
        val filteredNumbers = data.filter(condition).mapNotNull { it.toString().toDoubleOrNull() }
        if (filteredNumbers.isEmpty()) {
            throw NonNumericDataException(columnName)
        }
        return filteredNumbers.average()
    }
}
