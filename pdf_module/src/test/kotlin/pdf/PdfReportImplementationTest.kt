package pdf

import model.ReportConfig
import model.SummaryOperation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class PdfReportImplementationTest {

    private val pdfReport = PdfReportImplementation()

    @Test
    fun testGenerateReportWithSummary() {
        val data = mapOf(
            "Name" to listOf("Andrija", "Boban"),
            "Age" to listOf("23", "25"),
            "Email" to listOf("andrija@gmail.com", "boban@gmail.com")
        )
        val summary = mapOf("Total Students" to SummaryOperation("COUNT", 1))
        val config = ReportConfig(
            reportType = "PDF",
            title = "Student Report",
            header = true,
            columns = listOf(1, 2, 3),
            rows = "all",
            summary = summary,
            destination = "test_output.pdf"
        )

        pdfReport.generateReport(data, config.destination, config.header, config.title, null, config)

        val file = File(config.destination)
        assertTrue(file.exists(), "PDF report file should be generated")

        // Validate the file size to ensure content is written
        assertTrue(file.length() > 0, "PDF file should not be empty")

        file.delete()
    }

}
