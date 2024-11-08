package model

/**
 * Represents a summary operation to be applied to a specific column in a report.
 * This operation calculates a summary value (e.g., SUM, AVG) for the selected column
 * and, optionally, applies a condition (e.g., COUNTIF, SUMIF).
 *
 * @property operation The type of operation to perform on the column.
 *                     Examples include:
 *                     - "SUM": Sum of all values in the column.
 *                     - "AVG": Average of all values in the column.
 *                     - "COUNT": Count of all values in the column.
 *                     - "SUMIF" or "COUNTIF": Conditional sum or count, requiring `condition`.
 * @property column The index of the column on which to perform the operation.
 * @property condition An optional condition string for conditional operations
 *                     (e.g., ">5" for counting values greater than 5).
 *
 * Example usage:
 * ```
 * val totalPoints = SummaryOperation(operation = "SUM", column = 5)
 * val averagePoints = SummaryOperation(operation = "AVG", column = 5)
 * val countPassed = SummaryOperation(operation = "COUNTIF", column = 6, condition = ">=50")
 * ```
 */
data class SummaryOperation(
    val operation: String,  // Type of operation (e.g., "SUM", "AVG", "COUNTIF")
    val column: Int,        // Column index on which to apply the operation
    val condition: String? = null  // Optional condition for conditional operations
)