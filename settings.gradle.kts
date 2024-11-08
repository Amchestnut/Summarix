plugins {
    // This plugic helps gradle automatically find, download and configure JDK toolchains.   Particularly uselful in project where you want to ensure that all developers use a specific JDK version without requiring them to install it manually
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "Summarix"

include("app_module")
include("spec_module")
//include("test_module")

include("csv_module")
include("pdf_module")
include("excel_module")
include("txt_module")
//include("database_module")
