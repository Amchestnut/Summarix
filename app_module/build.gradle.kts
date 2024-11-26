plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation(project(":spec_module"))
    implementation("com.google.code.gson:gson:2.10.1")

    runtimeOnly(project(":csv_module"))
    runtimeOnly(project(":excel_module"))
    runtimeOnly(project(":pdf_module"))
    runtimeOnly(project(":txt_module"))
    runtimeOnly("com.mysql:mysql-connector-j:8.4.0") // MySQL Connector for JDBC
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("main.MainKt")
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveFileName.set("summarix.jar")
        mergeServiceFiles()

        manifest {
            attributes["Main-Class"] = "main.MainKt"
        }
    }
}