package excel

import model.ReportConfig
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

class ExcelReportImplementationTest {

    private val excelReport = ExcelReportImplementation()

    @Test
    fun testGenerateReport() {
        val data = mapOf(
            "Name" to listOf("Andrija", "Boban"),
            "Age" to listOf("23", "25"),
            "Email" to listOf("andrija@gmail.com", "boban@gmail.com")
        )
        val config = ReportConfig(
            reportType = "EXCEL",
            title = "Student Report",
            header = true,
            columns = listOf(1, 2, 3),
            rows = "all",
            summary = emptyMap(),
            destination = "test_output.xlsx"
        )

        excelReport.generateReport(data, config.destination, config.header, config.title, null, config)

        val file = File(config.destination)
        assertTrue(file.exists(), "Excel report file should be generated")

        // Validate the content of the Excel file
        FileInputStream(file).use { fis ->
            val workbook = XSSFWorkbook(fis)
            val sheet = workbook.getSheetAt(0)
            assertEquals("Student Report", sheet.getRow(0).getCell(0).stringCellValue)
            assertEquals("Name", sheet.getRow(1).getCell(0).stringCellValue)
            assertEquals("Andrija", sheet.getRow(2).getCell(0).stringCellValue)
            assertEquals("23", sheet.getRow(2).getCell(1).stringCellValue)
            workbook.close()
        }

        file.delete()
    }
}
