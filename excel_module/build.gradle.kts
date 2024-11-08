plugins {
    kotlin("jvm")
    `java-library`
    `maven-publish`
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
    implementation("org.apache.poi:poi:5.2.0")        // Core POI library
    implementation("org.apache.poi:poi-ooxml:5.2.0")  // OOXML support for .xlsx files
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // If you're using the 'java' or 'kotlin' plugin

            groupId = "org.example"
            artifactId = "cetvrtaImpl"
            version = "1.0.0"
        }
    }
}

