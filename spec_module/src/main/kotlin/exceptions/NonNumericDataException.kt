package exceptions

class NonNumericDataException(columnName: String) : Exception("Column '$columnName' contains non-numeric data.")