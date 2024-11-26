package specification

import model.ComputedColumn
import model.ReportConfig
import model.Style
import model.SummaryOperation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ReportInterfaceTest {

    @Test
    fun testComputedColumnInitialization() {
        val computedColumn = ComputedColumn("TotalScore", "+", listOf(1, 2))
        assertEquals("TotalScore", computedColumn.columnName)
        assertEquals("+", computedColumn.operation)
        assertEquals(listOf(1, 2), computedColumn.columns)
    }

    @Test
    fun testReportConfigInitialization() {
        val summaryOperation = SummaryOperation("SUM", 3)
        val reportConfig = ReportConfig(
            reportType = "PDF",
            title = "Performance Report",
            header = true,
            columns = listOf(1, 2, 3),
            rows = "all",
            summary = mapOf("Total" to summaryOperation),
            destination = "output.pdf"
        )

        assertEquals("PDF", reportConfig.reportType)
        assertEquals("Performance Report", reportConfig.title)
        assertTrue(reportConfig.header)
        assertEquals(listOf(1, 2, 3), reportConfig.columns)
        assertEquals("all", reportConfig.rows)
        assertEquals(1, reportConfig.summary.size)
        assertEquals("output.pdf", reportConfig.destination)
    }

    @Test
    fun testStyleInitialization() {
        val style = Style(bold = true, italic = false, color = "#FF0000", fontSize = 12)
        assertTrue(style.bold!!)
        assertFalse(style.italic!!)
        assertEquals("#FF0000", style.color)
        assertEquals(12, style.fontSize)
    }

    @Test
    fun testSummaryOperationInitialization() {
        val summaryOperation = SummaryOperation("COUNTIF", 4, ">70")
        assertEquals("COUNTIF", summaryOperation.operation)
        assertEquals(4, summaryOperation.column)
        assertEquals(">70", summaryOperation.condition)
    }

}
