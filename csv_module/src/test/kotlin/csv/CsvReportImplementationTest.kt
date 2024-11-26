package csv

import model.ReportConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class CsvReportImplementationTest {

    private val csvReport = CsvReportImplementation()

    @Test
    fun testGenerateHeader() {
        val columns = listOf("Name", "Age", "Email")
        val expected = "Name,Age,Email"
        val result = csvReport.generateHeader(columns)
        assertEquals(expected, result)
    }

    @Test
    fun testGenerateRow() {
        val data = mapOf("Name" to "Andrija", "Age" to 22, "Email" to "andrija@gmail.com")
        val expected = "Andrija,22,andrija@gmail.com"
        val result = csvReport.generateRow(data)
        assertEquals(expected, result)
    }

    @Test
    fun testGenerateReport() {
        val data = mapOf(
            "Name" to listOf("Andrija", "Boban"),
            "Age" to listOf("23", "25"),
            "Email" to listOf("andrija@gmail.com", "boban@gmail.com")
        )
        val config = ReportConfig(
            reportType = "CSV",
            title = null,
            header = true,
            columns = listOf(1, 2, 3),
            rows = "all",
            summary = emptyMap(),
            destination = "test_output.csv"
        )

        csvReport.generateReport(data, config.destination, config.header, config.title, null, config)

        val file = File(config.destination)
        assertTrue(file.exists(), "Report file should be generated")

        val content = file.readText()
        assertTrue(content.contains("Name,Age,Email"))
        assertTrue(content.contains("Andrija,23,andrija@gmail.com"))
        assertTrue(content.contains("Boban,25,boban@gmail.com"))

        file.delete()
    }
}
