package main

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import specification.ReportInterface
import java.sql.DriverManager
import java.util.*

class MainTest {

    @Test
    fun testDatabaseConnection() {
        try {
            val jdbcUrl = "jdbc:mysql://localhost:3306/komponente?autoReconnect=true&useSSL=false"
            val username = "root"
            val password = ""
            val connection = DriverManager.getConnection(jdbcUrl, username, password)

            // The connection is valid (let's test)
            assertTrue(connection.isValid(2), "Connection should be valid")

            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
            assertTrue(false, "An exception occurred while connecting to the database: ${e.message}")
        }
    }

    @Test
    fun testServiceLoader() {
        val serviceLoader = ServiceLoader.load(ReportInterface::class.java)
        val reportGenerators = serviceLoader.associateBy { it.implName }
        assertTrue(reportGenerators.isNotEmpty(), "There should be at least one report generator available")
    }
}
