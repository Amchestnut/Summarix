package specification

import exceptions.ColumnNotFoundException
import exceptions.NonNumericDataException
import model.ComputedColumn
import model.ReportConfig
import java.sql.ResultSet
import java.sql.ResultSetMetaData

/**
 * Interface for generating customizable reports in multiple formats (PDF, EXCEL, CSV, TXT) with advanced options.
 * Supports customization for title, headers, rows, summaries, computed columns, and conditional calculations.
 */
interface ReportInterface: Calculation {
    /**
     * The name of the implementation (PDF, EXCEL, CSV, TXT)
     */
    val implName: String


    /**
     * Generates a report title.
     * @param title The title text for the report.
     * @return Formatted title as a string.
     */
    fun generateTitle(title: String): String


    /**
     * Generates a formatted header row for the report.
     * @param columns List of column names.
     * @return The formatted header row string.
     */
    fun generateHeader(columns: List<String>): String


    /**
     * Generates a row in the report.
     * @param data Map of column names to values.
     * @param rowIndex Optional row index for reference.
     * @return Formatted row as a string.
     */
    fun generateRow(data: Map<String, Any>, rowIndex: Int? = null): String


    /**
     * Generates a formatted summary row for the report.
     * @param summaryData Map of summary labels to calculated values.
     * @return The formatted summary string.
     */
    fun generateSummary(summaryData: Map<String, Any>): String

    /**
     * Generates and saves a complete report based on the provided data and configuration.
     * @param data Map of column names to lists of values.
     * @param destination File path or output location.
     * @param header Boolean flag indicating whether to include a header row.
     * @param title Optional report title.
     * @param summary Optional report summary.
     * @param config ReportConfig providing options for styling, formatting, and layout.
     */
    fun generateReport(
        data: Map<String, List<String>>,
        destination: String,
        header: Boolean,
        title: String? = null,
        summary: String? = null,
        config: ReportConfig        // we pass this because we need our stiling options (we could send only stiling ofc)
    )


    /**
     * Generates and saves a complete report using SQL data and configuration that has been set in json.
     * @param config ReportConfig specifying the report's layout, styling, and content options.
     * @param resultSet SQL ResultSet containing the data.
     */
    fun generateReport(config: ReportConfig, resultSet: ResultSet) {
        // Prepare data with ONLY the selected columns
        val (preparedData, columnOrder) = prepareData(resultSet, config.columns)

        // Process computed columns if any
        val updatedData = if (config.computedColumns != null && config.computedColumns.isNotEmpty()) {
            addComputedColumns(preparedData, columnOrder, config.computedColumns)
        } else {
            preparedData
        }

        // Generate the summary data
        val summaryData = generateSummaryData(config, updatedData, columnOrder)

        // Call generateReport with the prepared data and summary
        generateReport(updatedData, config.destination, config.header, config.title, summaryData, config)
    }


    /**
     * Prepares data from the ResultSet based on selected columns.
     * @param resultSet SQL result set to read from.
     * @param selectedColumns List of column indices to include.
     * @return Pair containing the report data and column order.
     */
    private fun prepareData(
        resultSet: ResultSet,
        selectedColumns: List<Int>
    ): Pair<Map<String, List<String>>,  MutableList<String>> {  // Return MutableList<String>
        val reportData = LinkedHashMap<String, MutableList<String>>()  // Use LinkedHashMap to preserve order
        val metaData: ResultSetMetaData = resultSet.metaData

        // Map selected column indices to actual column names
        val columnNames = selectedColumns.map { index -> metaData.getColumnName(index) }.toMutableList()  // Make mutable

        // Initialize map with column names
        columnNames.forEach { columnName ->
            reportData[columnName] = mutableListOf()
        }

        // Populate report data with values for the selected columns
        while (resultSet.next()) {
            columnNames.forEach { columnName ->
                reportData[columnName]!!.add(resultSet.getString(columnName))
            }
        }

        return Pair(reportData, columnNames)
    }


    /**
     * Generates summary data based on the configuration.
     * @param config Report configuration settings.
     * @param data Map of column names to values.
     * @param columnOrder List of columns in specified order.
     * @return Formatted summary data as a string.
     */
    private fun generateSummaryData(
        config: ReportConfig,
        data: Map<String, List<String>>,
        columnOrder: List<String>
    ): String {
        val summaryData = mutableMapOf<String, Any>()

        config.summary.forEach { (label, operation) ->
            val columnName = columnOrder.getOrNull(operation.column - 1)
                ?: throw ColumnNotFoundException("Column with index ${operation.column} does not exist in data.")

            val columnData = data[columnName] ?: throw ColumnNotFoundException("Column '$columnName' does not exist in data.")

            if (columnData.isEmpty()) {
                throw ColumnNotFoundException("Column '$columnName' has no values.")
            }

            if (operation.condition != null) {
                val condition = parseCondition(operation.condition)

                try {
                    summaryData[label] = when (operation.operation) {
                        "SUMIF" -> sumIf(columnData, columnName, condition)
                        "AVGIF" -> averageIf(columnData, columnName, condition)
                        "COUNTIF" -> countIf(columnData, condition)
                        else -> throw IllegalArgumentException("Unsupported conditional operation: ${operation.operation}")
                    }
                } catch (e: NonNumericDataException) {
                    throw e  // Re-throw to handle it as needed
                }
            } else {
                val numericData = columnData.mapNotNull { it.toDoubleOrNull() }
                if (numericData.isEmpty()) throw NonNumericDataException(columnName)

                summaryData[label] = when (operation.operation) {
                    "SUM" -> sum(numericData, columnName)
                    "AVG" -> average(numericData, columnName)
                    "COUNT" -> count(columnData)
                    else -> throw IllegalArgumentException("Unsupported operation: ${operation.operation}")
                }
            }
        }

        return generateSummary(summaryData)
    }


    /**
     * Adds computed columns to the report data.
     * @param data Existing data map.
     * @param columnOrder Current order of columns.
     * @param computedColumns List of computed columns.
     * @return Updated data map with computed columns.
     */
    private fun addComputedColumns(
        data: Map<String, List<String>>,
        columnOrder: MutableList<String>,
        computedColumns: List<ComputedColumn>
    ): Map<String, List<String>> {
        val updatedData = LinkedHashMap(data) // Copy to modify
        computedColumns.forEach { computedColumn ->
            val operation = computedColumn.operation
            val targetColumnIndices = computedColumn.columns
            val targetColumnNames = targetColumnIndices.map { index ->
                columnOrder.getOrNull(index - 1)
                    ?: throw ColumnNotFoundException("Column with index $index does not exist in data.")
            }

            // Get the data for the target columns
            val targetColumnsData = targetColumnNames.map { columnName ->
                data[columnName] ?: throw ColumnNotFoundException("Column '$columnName' does not exist in data.")
            }

            // Ensure all columns have the same number of rows
            val rowCount = targetColumnsData.first().size
            if (targetColumnsData.any { it.size != rowCount }) {
                throw IllegalArgumentException("All columns must have the same number of rows")
            }

            // Compute the new column values
            val computedValues = (0 until rowCount).map { rowIndex ->
                val values = targetColumnsData.map { columnData ->
                    val cellValue = columnData[rowIndex]
                    cellValue.toDoubleOrNull() ?: throw NonNumericDataException("Non-numeric data in column '$cellValue'")
                }
                performOperation(values, operation, targetColumnNames)
            }.map { it.toString() } // Convert results back to String

            // Add the new column to the data
            updatedData[computedColumn.columnName] = computedValues
            columnOrder.add(computedColumn.columnName)
        }

        return updatedData
    }


    /**
     * Performs a mathematical operation on a list of values.
     * @param values List of values to operate on.
     * @param operation Operation type (e.g., "+", "-", "*", "/").
     * @param columnNames Names of the columns involved in the operation.
     * @return Result of the operation.
     */
    private fun performOperation(values: List<Double>, operation: String, columnNames: List<String>): Double {
        return when (operation) {
            "+" -> values.sum()
            "*" -> values.reduce { acc, v -> acc * v }
            "-" -> {
                if (values.size == 2) values[0] - values[1]
                else throw IllegalArgumentException("Subtraction requires exactly two columns")
            }
            "/" -> {
                if (values.size == 2) {
                    if (values[1] != 0.0) values[0] / values[1]
                    else throw ArithmeticException("Division by zero in column ${columnNames[1]}")
                } else throw IllegalArgumentException("Division requires exactly two columns")
            }
            else -> throw IllegalArgumentException("Unsupported operation: $operation")
        }
    }


    /**
     * Parses a condition into a lambda expression.
     * @param condition String representation of the condition (example: ">5").
     * @return Lambda function representing the condition.
     */
    private fun parseCondition(condition: String): (Any) -> Boolean {
        val operator = condition[0]
        val value = condition.substring(1).toDoubleOrNull()
            ?: throw IllegalArgumentException("Invalid condition format: $condition")

        return when (operator) {
            '>' -> { item -> item.toString().toDoubleOrNull()?.let { it > value } ?: false }
            '<' -> { item -> item.toString().toDoubleOrNull()?.let { it < value } ?: false }
            '=' -> { item -> item.toString().toDoubleOrNull()?.let { it == value } ?: false }
            else -> throw IllegalArgumentException("Unsupported condition operator: $operator")
        }
    }


    /**
     * Sets bold formatting for the specified content.
     * @param contentIdentifier Identifier for the target content.
     */
    fun setBold(contentIdentifier: Any)

    /**
     * Sets italic formatting for the specified content.
     * @param contentIdentifier Identifier for the target content.
     */
    fun setItalic(contentIdentifier: Any)

    /**
     * Sets the color for the specified content.
     * @param contentIdentifier Identifier for the target content.
     * @param color A hex color code (e.g., "#FF0000").
     */
    fun setColor(contentIdentifier: Any, color: String)

    /**
     * Underlines the specified content.
     * @param contentIdentifier Identifier for the target content.
     */
    fun setUnderline(contentIdentifier: Any)

}