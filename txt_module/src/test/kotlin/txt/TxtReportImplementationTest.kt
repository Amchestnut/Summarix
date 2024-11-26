package txt

import model.ReportConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class TxtReportImplementationTest {

    private val txtReport = TxtReportImplementation()

    @Test
    fun testGenerateTitle() {
        val title = "Sample Report Title"
        val result = txtReport.generateTitle(title)
        assertEquals(title, result)
    }

    @Test
    fun testGenerateHeader() {
        val columns = listOf("Name", "Age", "Email")
        val expected = "Name | Age | Email"
        val result = txtReport.generateHeader(columns)
        assertEquals(expected, result)
    }

    @Test
    fun testGenerateRow() {
        val data = mapOf("Name" to "John Doe", "Age" to 30, "Email" to "john@example.com")
        val expected = "John Doe | 30 | john@example.com"
        val result = txtReport.generateRow(data)
        assertEquals(expected, result)
    }

    @Test
    fun testGenerateSummary() {
        val summaryData = mapOf("Total Students" to 50, "Average Age" to 22.5)
        val expected = "Total Students: 50\nAverage Age: 22.5"
        val result = txtReport.generateSummary(summaryData)
        assertEquals(expected.trim(), result.trim())
    }

    @Test
    fun testGenerateReport() {
        val data = mapOf(
            "Name" to listOf("Alice", "Bob"),
            "Age" to listOf("23", "25"),
            "Email" to listOf("alice@example.com", "bob@example.com")
        )
        val config = ReportConfig(
            reportType = "TXT",
            title = "Student Report",
            header = true,
            columns = listOf(1, 2, 3),
            rows = "all",
            summary = emptyMap(),
            destination = "test_output.txt"
        )

        txtReport.generateReport(data, config.destination, config.header, config.title, null, config)

        val file = File(config.destination)
        assertTrue(file.exists(), "Report file should be generated")

        val content = file.readText()
        assertTrue(content.contains("Student Report"))
        assertTrue(content.contains("Name"))
        assertTrue(content.contains("Alice"))
        assertTrue(content.contains("Bob"))

        file.delete()
    }

    @Test
    fun testSetBoldDoesNotThrow() {
        assertDoesNotThrow {
            txtReport.setBold("Title")
        }
    }

    @Test
    fun testSetItalicDoesNotThrow() {
        assertDoesNotThrow {
            txtReport.setItalic("Subtitle")
        }
    }

    @Test
    fun testSetColorDoesNotThrow() {
        assertDoesNotThrow {
            txtReport.setColor("Header", "#FF0000")
        }
    }

    @Test
    fun testSetUnderlineDoesNotThrow() {
        assertDoesNotThrow {
            txtReport.setUnderline("Important")
        }
    }
}
