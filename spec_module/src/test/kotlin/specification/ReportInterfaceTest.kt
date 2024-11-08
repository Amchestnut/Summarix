package specification

import model.ReportConfig
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.mock
import java.sql.ResultSet
import java.sql.ResultSetMetaData

class ReportInterfaceTest {

    private lateinit var resultSet: ResultSet
    private lateinit var metaData: ResultSetMetaData

    private lateinit var reportInterface: ReportInterface

    @BeforeEach
    fun setup() {
        // Initializing mocks for ResultSet and ResultSetMetaData (for database copy)
        resultSet = mock()
        metaData = mock()

        // Initializing the ReportInterface with a mock implementation
        reportInterface = object : ReportInterface {
            override val implName: String = "MockReport"

            override fun generateTitle(title: String) = "Title: $title"

            override fun generateHeader(columns: List<String>) = "Header: ${columns.joinToString(", ")}"

            override fun generateRow(data: Map<String, Any>, rowIndex: Int?) = "Row: $data"

            override fun generateSummary(summaryData: Map<String, Any>) = "Summary: $summaryData"
            override fun generateReport(
                data: Map<String, List<String>>,
                destination: String,
                header: Boolean,
                title: String?,
                summary: String?,
                config: ReportConfig
            ) {
                println("Report generated with title: $title, destination: $destination, header: $header")
            }

            override fun setBold(contentIdentifier: Any) {
                TODO("Not yet implemented")
            }

            override fun setItalic(contentIdentifier: Any) {
                TODO("Not yet implemented")
            }

            override fun setColor(contentIdentifier: Any, color: String) {
                TODO("Not yet implemented")
            }

            override fun setUnderline(contentIdentifier: Any) {
                TODO("Not yet implemented")
            }


        }
    }

    // to use this test, need to make 2 function in ReportInterface PUBLIC, instead of private

//    @Test
//    fun `test prepareData with selected columns`() {
//        // Mock ResultSetMetaData to simulate a database table with columns
//        whenever(metaData.columnCount).thenReturn(5)
//        whenever(metaData.getColumnName(2)).thenReturn("name")
//        whenever(metaData.getColumnName(3)).thenReturn("surname")
//        whenever(metaData.getColumnName(4)).thenReturn("index_number")
//        whenever(metaData.getColumnName(5)).thenReturn("points")
//
//        whenever(resultSet.metaData).thenReturn(metaData)
//        whenever(resultSet.next()).thenReturn(true, true, false) // simulate 2 rows
//        whenever(resultSet.getString("name")).thenReturn("John", "Jane")
//        whenever(resultSet.getString("surname")).thenReturn("Doe", "Smith")
//        whenever(resultSet.getString("index_number")).thenReturn("S123", "S456")
//        whenever(resultSet.getString("points")).thenReturn("85", "78")
//
//        val selectedColumns = listOf(2, 3, 4, 5)
//        val result = reportInterface.prepareData(resultSet, selectedColumns)
//
//        val expectedData = mapOf(
//            "name" to listOf("John", "Jane"),
//            "surname" to listOf("Doe", "Smith"),
//            "index_number" to listOf("S123", "S456"),
//            "points" to listOf("85", "78")
//        )
//
//        assertEquals(expectedData, result)
//    }
//
//
//
//    @Test
//    fun `test generateSummaryData with valid operations`() {
//        val data = mapOf(
//            "points" to listOf("85", "78", "90"),
//            "credits" to listOf("3", "4", "5")
//        )
//        val config = ReportConfig(
//            reportType = "PDF",
//            title = "Student Report",
//            header = true,
//            columns = listOf(1, 2),
//            rows = "all",
//            summary = mapOf(
//                "total_points" to SummaryOperation("SUM", 1),
//                "average_credits" to SummaryOperation("AVG", 2)
//            ),
//            destination = "outputs/StudentReport.pdf"
//        )
//
//        val summary = reportInterface.generateSummaryData(config, data)
//        println("Generated summary: $summary")
//
//        // Update the assertions to match the map-like output
//        assertTrue(summary.contains("total_points=253.0"), "Expected summary to contain total_points=253.0")  // 85 + 78 + 90 = 253
//        assertTrue(summary.contains("average_credits=4.0"), "Expected summary to contain average_credits=4.0")  // (3 + 4 + 5) / 3 = 4.0
//
//    }
//
//    @Test
//    fun `test generateSummaryData with missing column throws exception`() {
//        val data = mapOf(
//            "points" to listOf("85", "78", "90")
//        )
//        val config = ReportConfig(
//            reportType = "PDF",
//            title = "Student Report",
//            header = true,
//            columns = listOf(1),
//            rows = "all",
//            summary = mapOf(
//                "total_credits" to SummaryOperation("SUM", 2)
//            ),
//            destination = "outputs/StudentReport.pdf"
//        )
//
//        assertThrows(ColumnNotFoundException::class.java) {
//            reportInterface.generateSummaryData(config, data)
//        }
//    }
}
