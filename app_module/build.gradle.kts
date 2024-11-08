plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"   // Shadow plugin is used to create a "FAT Jar" that includes all dependencies in one single JAR file!
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()        // We dont need this because we already use in dependencies: "project(:)", meaning they are all in the same project.
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation(project(":spec_module"))
    implementation("com.google.code.gson:gson:2.10.1")

    runtimeOnly(project(":csv_module"))
    runtimeOnly(project(":excel_module"))
    runtimeOnly(project(":pdf_module"))
    runtimeOnly(project(":txt_module"))
    runtimeOnly("com.mysql:mysql-connector-j:8.4.0")        // MySQL Connector for JDBC
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("main.MainKt")
}

// Tasks in Gradle are used to define what actions need to be taken during the build lifecycle, like compiling, testing, packaging, etc.
tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveFileName.set("summarix.jar") // Optional: Set the desired jar name
        mergeServiceFiles() // Merge META-INF/services

        manifest {
            attributes["Main-Class"] = "main.MainKt" // We add in manifest the path to the start of executing, which is our MAIN
        }
    }
}


// This shadow is very important for the JAR file
// We use it bcz is helpful for deployment because it packages our APP and ALL ITS DEPENDENCIES into a 1 single JAR.  (we dont need to worry about separate JAR files for dependencies - when deploing the app.

// META-INF is a special directory in 1 JAR file that can contain METADATA about the JAR, such as config files or resources
// When we use ServiceLoader to load implementations of an interface (like DatabaseService), java looks in "META-INF/services" for a list of implementations.  VERY HELPFUL

// MANIFEST contains metadatada about the JAR file, like the version, author, and the entry point of the APP
// Attributes inside manifest specifies addition metadata, such as the main class for the APP (the main class in the entry point for JAVA RUNTIME, when we run the JAR)
