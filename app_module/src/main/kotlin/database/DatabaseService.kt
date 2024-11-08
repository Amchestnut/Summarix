package database

import java.sql.Connection
import java.sql.ResultSet

interface DatabaseService {
    fun connect(): Connection
    fun executeQuery(query: String): ResultSet
    fun close()
}