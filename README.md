# **Summarix: Advanced Multi-Format Report Generator**  

**Summarix** is a powerful, modular report generation library written in **Kotlin**. It takes a database and enables seamless generation of reports using database data in multiple formats **(PDF, XLSX, CSV, TXT)** with advanced customization options such as **computed columns, summary rows, and user-defined styles**. Designed with **extensibility** and **scalability** in mind, Summarix is a versatile tool for businesses, developers, and data analysts seeking to automate report generation.

<p align="center">
  <img src="outputs/excel_report.png" alt="Excel Report" width="400" />
  <img src="outputs/pdf_report.png" alt="Pdf Report" width="360" />
</p>

## **Features**
- **Multi-Format Support**: Generate reports in **PDF, Excel, CSV, and TXT** formats.  
- **Customizable Styling**:  
  - **Title**: Set font size, bold, italic, underline, and color.  
  - **Header**: Customize font size, boldness, and color.  
  - **Rows**: Style individual rows with different options.  
  - **Summary**: Format summary text with rich styles.  
- **Computed Columns**: Define custom columns with operations (+, -, *, /) based on existing data.  
- **Summaries**: Perform operations like SUM, AVG, COUNT, or conditional calculations (SUMIF, AVGIF, etc.).  
- **Dynamic Data Handling**: Automatically processes **SQL result sets** or **JSON input** for report generation.  
- **Extensibility**: Built with modular architecture, supporting easy addition of new report formats.  

## Real-World Applications
- **Business Reporting**: Automate monthly, quarterly, or yearly reports for stakeholders.  
- **Data Analytics**: Generate detailed reports with computed columns and summaries for analysis.  
- **Education**: Create student performance reports for schools or training programs.  
- **Freelancing**: Use Summarix to provide customized reports to clients in multiple formats.  

## Getting Started
**Installation**  
Clone the repository and integrate it into your Kotlin/Gradle project:  
```
git clone https://github.com/your-username/summarix.git
```

**Dependencies**  
Ensure the following dependencies are added to your build.gradle.kts:  
```
implementation("com.google.code.gson:gson:2.10.1")
runtimeOnly("com.mysql:mysql-connector-j:8.4.0")
```

## How to Use  
**Example Input**  
Provide configuration via JSON:  

```
{
  "reportType": "PDF",
  "title": "Student Report",
  "titleStyle": {
    "bold": true,
    "italic": false,
    "color": "#FF0000",
    "fontSize": 18
  },
  "headerStyle": {
    "bold": true,
    "color": "#0000FF",
    "fontSize": 12
  },
  "rowStyle": {
    "italic": true,
    "fontSize": 15
  },
  "summaryStyle": {
    "bold": true,
    "italic": true,
    "color": "#00FF00",
    "fontSize": 14,
    "underline": true
  },
  "header": true,
  "columns": [3, 4, 5, 6],
  "rows": "all",
  "computedColumns": [
    {
      "columnName": "TotalPoints",
      "operation": "*",
      "columns": [3, 4]
    }
  ],
  "summary": {
    "total_points": {
      "operation": "SUM",
      "column": 4
    }
  },
  "destination": "outputs/StudentReport.pdf"
}
```


**Code Example**
```
val gson = Gson()
val config = gson.fromJson(FileReader("input.json"), ReportConfig::class.java)

val databaseService = MySQLDatabaseService()
val resultSet = databaseService.executeQuery("SELECT * FROM students")

val reportGenerator = ServiceLoader.load(ReportInterface::class.java).find { it.implName == config.reportType }
reportGenerator?.generateReport(config, resultSet)
```

**Output**
Generates a report in the desired format at the specified location.

## Tech Stack
- **Languages**: *Kotlin, SQL*  
- **Libraries**: *Gson, MySQL Connector, LibrePDF/OpenPDF (PDF generation), Apache POI (Excel)*  
- **Build Tool**: *Gradle*  


## Contributing
**Contributions are welcome! Please follow these steps:**  
- Fork the repository.  
- Create a new feature branch.  
- Commit your changes.  
- Submit a pull request.  


## License
This project is licensed under the MIT License. See LICENSE for details.  
