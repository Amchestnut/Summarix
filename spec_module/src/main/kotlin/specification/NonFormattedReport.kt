package specification

import specification.ReportInterface

abstract class NonFormattedReport : ReportInterface {

//    override fun generateTitle(title: String): String {
//        // Non-formatted title, plain text
//        return title
//    }
//
//    override fun generateHeader(columns: List<String>): String {
//        // Simple text header with no formatting
//        return columns.joinToString(", ")
//    }
//
//    override fun generateRow(data: Map<String, Any>, rowIndex: Int?): String {
//        val rowNumber = rowIndex?.let { "$it. " } ?: ""
//        return rowNumber + data.values.joinToString(", ") { it.toString() }
//    }
//
//    override fun generateSummary(summaryData: Map<String, Any>): String {
//        return summaryData.entries.joinToString("\n") { (label, value) ->
//            "$label: $value"
//        }
//    }

}