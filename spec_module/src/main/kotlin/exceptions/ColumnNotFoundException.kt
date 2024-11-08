package exceptions

class ColumnNotFoundException(columnName: String) : Exception("Column '$columnName' does not exist.")
