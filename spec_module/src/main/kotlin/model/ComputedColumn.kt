package model

/**
 * Represents a column in a report whose values are calculated based on an arithmetic operation
 * applied to other columns in the report.
 *
 * @property columnName The name of the new computed column to be added to the report.
 * @property operation The arithmetic operation to be performed on the specified columns.
 *                     Supported operations: "+", "-", "*", "/".
 * @property columns A list of indices representing the columns involved in the computation.
 *                   These indices correspond to the positions of columns in the dataset.
 *
 * Example usage:
 * ```
 * val computedColumn = ComputedColumn(
 *     columnName = "TotalScore",
 *     operation = "+",
 *     columns = listOf(1, 2, 3)  // Adds the values of columns at indices 1, 2, and 3.
 * )
 * ```
 */
data class ComputedColumn(
    val columnName: String,     // Name of the (new!) computed column
    val operation: String,      // "+", "-", "*", or "/"
    val columns: List<Int>      // Indices of columns to use in the computation
)
