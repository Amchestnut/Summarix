package model

/**
 * Configuration for generating a customizable report. This class holds various settings and options
 * that define the structure, content, and styling of the report output.
 *
 * @property reportType The type of report to generate (e.g., "PDF", "EXCEL", "CSV", "TXT").
 * @property title An optional title for the report.
 * @property header Boolean flag indicating if a header row should be included in the report.
 * @property columns A list of indices specifying the columns to include in the report.
 * @property rows The selection of rows to include in the report, specified as a string (e.g., "all").
 * @property summary A map where keys are summary labels, and values are summary operations
 *                   (e.g., "total_score" -> SUM of a specific column).
 * @property computedColumns An optional list of `ComputedColumn` objects defining new columns to be
 *                           calculated based on arithmetic operations applied to existing columns.
 * @property destination The file path or location where the generated report will be saved.
 * @property titleStyle Optional styling configuration for the report title, using the `Style` class.
 * @property headerStyle Optional styling configuration for the header row.
 * @property rowStyle Optional styling configuration for data rows.
 * @property summaryStyle Optional styling configuration for the summary section.
 *
 * Example usage:
 * ```
 * val reportConfig = ReportConfig(
 *     reportType = "PDF",
 *     title = "Student Performance Report",
 *     header = true,
 *     columns = listOf(1, 2, 3, 4),
 *     rows = "all",
 *     summary = mapOf("total_score" to SummaryOperation("SUM", 5)),
 *     computedColumns = listOf(ComputedColumn("TotalScore", "+", listOf(3, 4))),
 *     destination = "outputs/StudentReport.pdf",
 *     titleStyle = Style(bold = true, fontSize = 18),
 *     headerStyle = Style(bold = true, color = "#0000FF"),
 *     rowStyle = Style(fontSize = 12),
 *     summaryStyle = Style(bold = true, italic = true, color = "#FF0000")
 * )
 * ```
 */
data class ReportConfig(
    val reportType: String,                    // Type of report to generate
    val title: String?,                        // Optional title of the report
    val header: Boolean,                       // Flag to include a header row
    val columns: List<Int>,                    // Indices of columns to include
    val rows: String,                          // Specification of rows to include
    val summary: Map<String, SummaryOperation>, // Map of summary labels to operations
    val computedColumns: List<ComputedColumn>? = null, // Optional list of computed columns
    val destination: String,                   // File path for saving the report
    val titleStyle: Style? = null,             // Optional title style
    val headerStyle: Style? = null,            // Optional header style
    val rowStyle: Style? = null,               // Optional row style
    val summaryStyle: Style? = null            // Optional summary style
)
