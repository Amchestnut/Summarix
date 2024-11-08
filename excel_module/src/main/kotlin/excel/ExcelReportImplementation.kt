package excel

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.usermodel.FontUnderline
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import model.ReportConfig
import model.Style
import specification.ReportInterface
import java.io.FileOutputStream
import java.io.IOException

class ExcelReportImplementation : ReportInterface {
    override val implName: String = "EXCEL"
    private lateinit var workbook: Workbook
    private val cellReferences = mutableMapOf<Any, Cell>()

    override fun generateReport(
        data: Map<String, List<String>>,
        destination: String,
        header: Boolean,
        title: String?,
        summary: String?,
        config: ReportConfig
    ) {
        workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Report")
        var rowIndex = 0

        // Title Row
        title?.let {
            val titleRow = sheet.createRow(rowIndex++)
            val titleCell = titleRow.createCell(0)
            titleCell.setCellValue(it)
            cellReferences["title"] = titleCell

            // Apply styles
            applyStyles("title", config.titleStyle)
            sheet.addMergedRegion(CellRangeAddress(0, 0, 0, data.keys.size - 1))
        }

        // Header Row
        if (header) {
            val headerRow = sheet.createRow(rowIndex++)
            data.keys.forEachIndexed { index, columnName ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(columnName)
                val contentId = "header_$columnName"
                cellReferences[contentId] = cell

                // Apply styles
                applyStyles(contentId, config.headerStyle)
            }
        }

        // Data Rows
        val numRows = data.values.first().size
        for (i in 0 until numRows) {
            val dataRow = sheet.createRow(rowIndex++)
            data.keys.forEachIndexed { colIndex, columnName ->
                val cell = dataRow.createCell(colIndex)
                cell.setCellValue(data[columnName]?.get(i) ?: "")
                val contentId = "row_${i}_$columnName"
                cellReferences[contentId] = cell

                // Apply styles
                applyStyles(contentId, config.rowStyle)
            }
        }

        // Summary Row
        summary?.let {
            val summaryRow = sheet.createRow(rowIndex++)
            val summaryCell = summaryRow.createCell(0)
            summaryCell.setCellValue("Summary: $it")
            cellReferences["summary"] = summaryCell

            // Apply styles
            applyStyles("summary", config.summaryStyle)
            sheet.addMergedRegion(CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, data.keys.size - 1))
        }

        // Auto-size columns
        data.keys.indices.forEach { sheet.autoSizeColumn(it) }

        // Write to file
        try {
            FileOutputStream(destination).use { outputStream ->
                workbook.write(outputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            workbook.close()
        }
    }

    // Helper method to apply styles using the setBold, setItalic, etc., methods
    private fun applyStyles(contentIdentifier: Any, styleConfig: Style?) {
        val cell = cellReferences[contentIdentifier]
        cell?.let {
            val style = workbook.createCellStyle()  // Always create a new CellStyle for each cell
            it.cellStyle = style

            styleConfig?.let { config ->
                val font = workbook.createFont()  // Create a new Font for this style

                // Apply font styles based on configuration
                if (config.bold == true) font.bold = true
                if (config.italic == true) font.italic = true
                if (config.underline == true) font.underline = FontUnderline.SINGLE.byteValue
                config.color?.let { color -> font.color = getColorIndex(color) }
                config.fontSize?.let { size -> font.fontHeightInPoints = size.toShort() }

                style.setFont(font)  // Set the font to the cell style
            }
        }
    }

    // Implement styling methods
    override fun setBold(contentIdentifier: Any) {
        val cell = cellReferences[contentIdentifier]
        cell?.apply {
            val style = cell.cellStyle
            val font = getOrCreateFont(style)
            font.bold = true
            style.setFont(font)
        }
    }

    override fun setItalic(contentIdentifier: Any) {
        val cell = cellReferences[contentIdentifier]
        cell?.apply {
            val style = cell.cellStyle
            val font = getOrCreateFont(style)
            font.italic = true
            style.setFont(font)
        }
    }

    override fun setUnderline(contentIdentifier: Any) {
        val cell = cellReferences[contentIdentifier]
        cell?.apply {
            val style = cell.cellStyle
            val font = getOrCreateFont(style)
            font.underline = FontUnderline.SINGLE.byteValue
            style.setFont(font)
        }
    }

    override fun setColor(contentIdentifier: Any, color: String) {
        val cell = cellReferences[contentIdentifier]
        cell?.apply {
            val style = cell.cellStyle
            val font = getOrCreateFont(style)
            font.color = getColorIndex(color)
            style.setFont(font)
        }
    }

    private fun setFontSize(contentIdentifier: Any, fontSize: Int) {
        val cell = cellReferences[contentIdentifier]
        cell?.apply {
            val style = cell.cellStyle
            val font = getOrCreateFont(style)
            font.fontHeightInPoints = fontSize.toShort()
            style.setFont(font)
        }
    }

    private fun getOrCreateFont(style: CellStyle): Font {
        val fontIndex = style.fontIndexAsInt
        return if (fontIndex >= 0) {
            workbook.getFontAt(fontIndex.toShort().toInt())
        } else {
            workbook.createFont().also { style.setFont(it) }
        }
    }


    private fun getColorIndex(colorHex: String): Short {
        return when (colorHex.uppercase()) {
            "#000000", "BLACK" -> IndexedColors.BLACK.index
            "#FFFFFF", "WHITE" -> IndexedColors.WHITE.index
            "#FF0000", "RED" -> IndexedColors.RED.index
            "#00FF00", "GREEN" -> IndexedColors.GREEN.index
            "#0000FF", "BLUE" -> IndexedColors.BLUE.index
            "#FFFF00", "YELLOW" -> IndexedColors.YELLOW.index
            // Extend with more colors if needed
            else -> IndexedColors.BLACK.index // Default color
        }
    }

    // Implement other required methods
    override fun generateTitle(title: String): String = title

    override fun generateHeader(columns: List<String>): String {
        return columns.joinToString(" | ")
    }

    override fun generateRow(data: Map<String, Any>, rowIndex: Int?): String {
        val rowNumber = rowIndex?.let { "$it. " } ?: ""
        return rowNumber + data.values.joinToString(" | ") { it.toString() }
    }

    override fun generateSummary(summaryData: Map<String, Any>): String {
        val summaryBuilder = StringBuilder("\n")
        summaryData.forEach { (label, value) ->
            summaryBuilder.append("$label: $value\n")
        }
        return summaryBuilder.toString().trimEnd()
    }
}
