package model

/**
 * Represents styling options for text elements in a report, such as titles, headers, rows, or summaries.
 * Allows customization of font properties like boldness, italics, underline, color, and size.
 *
 * @property bold Whether the text should be displayed in bold.
 *                - `true` to apply bold styling.
 *                - `false` or `null` (default) for normal weight.
 * @property italic Whether the text should be displayed in italics.
 *                  - `true` to apply italic styling.
 *                  - `false` or `null` (default) for regular style.
 * @property underline Whether the text should be underlined.
 *                     - `true` to underline the text.
 *                     - `false` or `null` (default) for no underline.
 * @property color Hexadecimal color code for the text (e.g., "#FF0000" for red).
 *                 - If `null`, the default color is applied.
 * @property fontSize Font size for the text, specified in points.
 *                    - If `null`, a default font size is applied.
 *
 * Example usage:
 * ```
 * val titleStyle = Style(bold = true, color = "#FF5733", fontSize = 18)
 * val headerStyle = Style(bold = true, underline = true, color = "#0000FF", fontSize = 14)
 * val rowStyle = Style(italic = true, fontSize = 12)
 * ```
 */
data class Style(
    val bold: Boolean? = null,         // Bold styling for the text
    val italic: Boolean? = null,       // Italic styling for the text
    val underline: Boolean? = null,    // Underline styling for the text
    val color: String? = null,         // Hex color code for the text color
    val fontSize: Int? = null          // Font size in points
)
