package pdf

import com.lowagie.text.*
import com.lowagie.text.pdf.*
import model.ReportConfig
import model.Style
import specification.ReportInterface
import java.awt.Color
import java.io.FileOutputStream

class PdfReportImplementation : ReportInterface {
    override val implName: String = "PDF"
    private lateinit var config: ReportConfig

    override fun generateReport(
        data: Map<String, List<String>>,
        destination: String,
        header: Boolean,
        title: String?,
        summary: String?,
        config: ReportConfig
    ) {
        this.config = config
        val document = Document()

        try {
            PdfWriter.getInstance(document, FileOutputStream(destination))
            document.open()

            // Title
            title?.let {
                val titleFont = createFont(config.titleStyle)
                val titleParagraph = Paragraph(it, titleFont)
                titleParagraph.alignment = Element.ALIGN_CENTER
                document.add(titleParagraph)
                document.add(Chunk.NEWLINE)
            }

            // Table creation
            val columns = data.keys.toList()
            val numColumns = columns.size
            val table = PdfPTable(numColumns)
            table.widthPercentage = 100f

            // Header Row
            if (header) {
                val headerFont = createFont(config.headerStyle)
                columns.forEach { column ->
                    val headerCell = PdfPCell(Phrase(column, headerFont))
                    headerCell.horizontalAlignment = Element.ALIGN_CENTER
                    table.addCell(headerCell)
                }
            }

            // Data Rows
            val rowFont = createFont(config.rowStyle)
            val numRows = data.values.first().size
            for (i in 0 until numRows) {
                columns.forEach { column ->
                    val cellData = data[column]?.get(i) ?: ""
                    table.addCell(PdfPCell(Phrase(cellData, rowFont)))
                }
            }

            document.add(table)

            // Summary
            summary?.let {
                document.add(Chunk.NEWLINE)
                val summaryFont = createFont(config.summaryStyle)
                val summaryParagraph = Paragraph(it, summaryFont)
                document.add(summaryParagraph)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            document.close()
        }
    }

    // Helper method to create a Font from Style config
    private fun createFont(styleConfig: Style?): Font {
        styleConfig?.let {
            var fontStyle = when {
                it.bold == true && it.italic == true -> Font.BOLDITALIC
                it.bold == true -> Font.BOLD
                it.italic == true -> Font.ITALIC
                else -> Font.NORMAL
            }

            if (it.underline == true) {
                fontStyle = fontStyle or Font.UNDERLINE
            }

            val fontSize = it.fontSize?.toFloat() ?: 12f
            val color = getColor(it.color)
            return Font(Font.HELVETICA, fontSize, fontStyle, color)
        }
        return Font(Font.HELVETICA, 12f, Font.NORMAL, Color.BLACK)
    }

    // Convert hex color code to Color
    private fun getColor(colorHex: String?): Color {
        return try {
            colorHex?.let { Color.decode(it) } ?: Color.BLACK
        } catch (e: Exception) {
            Color.BLACK
        }
    }

    // Additional methods (if needed)
    override fun generateTitle(title: String): String = title
    override fun generateHeader(columns: List<String>): String = columns.joinToString(" | ")
    override fun generateRow(data: Map<String, Any>, rowIndex: Int?): String = data.values.joinToString(" | ")

    override fun generateSummary(summaryData: Map<String, Any>): String {
        val summaryBuilder = StringBuilder("Summary:\n")
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
