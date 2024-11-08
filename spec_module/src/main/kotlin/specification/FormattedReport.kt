package specification

import specification.ReportInterface

abstract class FormattedReport : ReportInterface {
//    override fun generateTitle(title: String): String {
//        // Format title with styling, e.g., bold or underline
//        return setBold(title)
//    }
//
//    override fun generateHeader(columns: List<String>): String {
//        return columns.joinToString(" | "){
//            setBold(it)
//        }
//    }
//
//    override fun generateRow(data: Map<String, Any>, rowIndex: Int?): String {
//        val rowNumber = rowIndex?.let {
//            "$it. "
//        }
//            ?: ""
//        return rowNumber + data.values.joinToString(" | "){ value ->
//            value.toString()
//        }
//    }
//
//    override fun generateSummary(summaryData: Map<String, Any>): String {
//        // Apply summary calculations like SUM, AVERAGE
//        return summaryData.entries.joinToString("\n") { (label, value) ->
//            "$label: $value"
//        }
//    }
//
//    /**
//     * Sets text to bold for the specified content.
//     */
//    abstract fun setBold(content: String): String
//
//    /**
//     * Sets text to italic for the specified content.
//     */
//    abstract fun setItalic(content: String): String
//
//    /**
//     * Sets text color for the specified content.
//     * @param color A color code (e.g., "#FF0000" for red).
//     */
//    abstract fun setColor(content: String, color: String): String
//
//    /**
//     * Sets text to underline for the specified content.
//     */
//    abstract fun setUnderline(content: String): String
}