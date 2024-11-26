package main

import com.google.gson.Gson
import database.MySQLDatabaseService
import specification.ReportInterface
import java.util.ServiceLoader

import model.ReportConfig
import java.io.FileReader

fun main() {
    val gson = Gson()
    val config = gson.fromJson(FileReader("inputs/input23.json"), ReportConfig::class.java)      // Change the input test here

    // Connect to the database
    val databaseService = MySQLDatabaseService()
    val connection = databaseService.connect()
    println("Database connection successful: ${connection.isValid(0)}")

    // Execute a query to fetch student data
    val query = "SELECT * FROM students"
    val resultSet = databaseService.executeQuery(query)

    // Load Report Generators via ServiceLoader
    val serviceLoader = ServiceLoader.load(ReportInterface::class.java)
    val reportGenerators = serviceLoader.associateBy { it.implName }
    println("Available report types: ${reportGenerators.keys}")
    val reportGenerator = reportGenerators[config.reportType]

    if (reportGenerator != null) {
        // Generate the report using ReportConfig and ResultSet
        reportGenerator.generateReport(config, resultSet)
        println("${config.reportType} report generated successfully at ${config.destination}")
    } else {
        println("Report generator for type ${config.reportType} not found.")
    }

    // Close the database connection
    databaseService.close()
}
