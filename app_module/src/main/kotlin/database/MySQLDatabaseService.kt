package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class MySQLDatabaseService : DatabaseService {
    private var connection: Connection? = null

    override fun connect(): Connection {
        if (connection == null || connection!!.isClosed){
            val jdbcUrl = "jdbc:mysql://localhost:3306/komponente?autoReconnect=true&useSSL=false"
            val username = "root"
            val password = ""
            connection = DriverManager.getConnection(jdbcUrl, username, password)
        }
        return connection!!
    }

    override fun executeQuery(query: String): ResultSet {
        if (connection == null || connection!!.isClosed){
            connect()
        }
        val statement = connection!!.createStatement()
        return statement.executeQuery(query)
    }

    override fun close() {
        connection?.close()
    }

}