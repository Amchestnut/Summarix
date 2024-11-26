package txt

import model.ReportConfig
import specification.ReportInterface
import java.io.File

class TxtReportImplementation : ReportInterface {
    override val implName: String = "TXT"

    override fun generateReport(
        data: Map<String, List<String>>,
        destination: String,
        header: Boolean,
        title: String?,
        summary: String?,
        config: ReportConfig
    ) {
        val columns = data.keys.toList()
        val numRows = data.values.first().size

        // Calculate the maximum width for each column based on the data
        val columnWidths = columns.map { column ->
            val headerWidth = column.length
            val maxDataWidth = data[column]?.maxOf { it.length } ?: 0
            maxOf(headerWidth, maxDataWidth)
        }

        // Open the destination file for writing
        File(destination).printWriter().use { writer ->
            // Write the title if provided
            title?.let {
                writer.println(it)
                writer.println()
            }

            // Write the header if needed
            if (header) {
                val headerRow = columns.mapIndexed { index, column ->
                    column.padEnd(columnWidths[index])
                }.joinToString("   ")
                writer.println(headerRow)

                // Write a dashed line under the header
                val divider = columns.mapIndexed { index, _ ->
                    "-".repeat(columnWidths[index])
                }.joinToString("- -")
                writer.println(divider)
            }

            // Write each row of data
            for (i in 0 until numRows) {
                val row = columns.mapIndexed { index, column ->
                    data[column]?.get(i)?.padEnd(columnWidths[index]) ?: ""
                }.joinToString("   ")
                writer.println(row)
            }

            // Write the summary if provided
            summary?.let {
                writer.println()
                writer.println("Summary: $it")
            }
        }
    }

    override fun generateTitle(title: String): String = title

    override fun generateHeader(columns: List<String>): String {
        return columns.joinToString(" | ")
    }

    override fun generateRow(data: Map<String, Any>, rowIndex: Int?): String {
        val rowNumber = rowIndex?.let { "$it. " } ?: ""
        return rowNumber + data.values.joinToString(" | ") { it.toString() }
    }

    override fun generateSummary(summaryData: Map<String, Any>): String {
        val summaryBuilder = StringBuilder("\n")
        summaryData.forEach { (label, value) ->
            summaryBuilder.append("$label: $value\n")
        }
        return summaryBuilder.toString().trimEnd()
    }

    override fun setBold(contentIdentifier: Any) {
    }

    override fun setItalic(contentIdentifier: Any) {
    }

    override fun setColor(contentIdentifier: Any, color: String) {
    }

    override fun setUnderline(contentIdentifier: Any) {
    }


}