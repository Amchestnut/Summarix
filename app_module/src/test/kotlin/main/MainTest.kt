package main

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.sql.DriverManager

class MainTest {

    @Test
    fun testDatabaseConnection() {
        try {
            val jdbcUrl = "jdbc:mysql://localhost:3306/komponente?autoReconnect=true&useSSL=false"
            val username = "root"
            val password = ""
            val connection = DriverManager.getConnection(jdbcUrl, username, password)

            // Assert that the connection is valid
            assertTrue(connection.isValid(2), "Connection should be valid")

            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
            assertTrue(false, "An exception occurred while connecting to the database: ${e.message}")
        }
    }
}
