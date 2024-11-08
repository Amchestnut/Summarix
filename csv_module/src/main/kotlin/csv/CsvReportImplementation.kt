package csv

import model.ReportConfig
import specification.ReportInterface
import java.io.File

class CsvReportImplementation : ReportInterface {
    override val implName: String = "CSV"

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

        // Write to CSV file
        File(destination).printWriter().use { writer ->
            if(header)
                writer.println(columns.joinToString(","))  // Write the header
            for (i in 0 until numRows) {
                val row = columns.map { column -> data[column]?.get(i) ?: "" }
                writer.println(row.joinToString(","))   // Write each row
            }
        }
    }

    override fun generateTitle(title: String): String {
        // CSV format does not support titles, so we return an empty string
        return ""
    }

    override fun generateHeader(columns: List<String>): String {
        return columns.joinToString(",")
    }

    override fun generateRow(data: Map<String, Any>, rowIndex: Int?): String {
        return data.values.joinToString(",")
    }

    override fun generateSummary(summaryData: Map<String, Any>): String {
        // CSV does not support a formatted summary, so we return an empty string
        return ""
    }

    // Formatting methods are not relevant for CSV, so we return content as it is
    override fun setBold(contentIdentifier: Any) {
    }

    override fun setItalic(contentIdentifier: Any) {
    }

    override fun setColor(contentIdentifier: Any, color: String) {
    }

    override fun setUnderline(contentIdentifier: Any) {
    }
}