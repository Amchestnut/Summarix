plugins {
    kotlin("jvm")   // ovaj plugin omogucava da pisemo kotlin koji se kompajlira na JVM
    `java-library`  // this plugin provides additional features for library projects. It can separate what is exposed to users (API) and what is internal (IMPLEMENTATION)
    `maven-publish` // this plugin helps publish artifact (like JAR FILES!!!) to maven repos, allowing other projects to import them as dependencies
}

group = "org.example"
version = "1.0-SNAPSHOT"        // should set later, so we have: "org.example:module-name:1.0.0"

repositories {
    mavenCentral()              // primary source for libraries and dependencies ofc
    mavenLocal()                // Really great because we can use other libraries in development that are not yet published to a central repository
}

dependencies {
    implementation("com.github.librepdf:openpdf:1.3.29")        // LIBRE PDF - Great for creating and manipulating PDF's
    implementation(project(":spec_module"))                     // this PDF_module depends on spec_module for shared functionality (interfaces, abstract classes...)
    testImplementation("org.jetbrains.kotlin:kotlin-test")      // need for writing tests
}

tasks.test {
    useJUnitPlatform()          // Use "JUnit" platform for testing
}

// Configures the module for publication in a Maven repository
publishing {
    publications {
        create<MavenPublication>("mavenJava"){  // Creates a new publications named "mavenJava"
            from(components["java"])

            groupId = "org.example"
            artifactId = "pdf_module"
            version = "1.0.0"
        }
    }
}

//kotlin {          i dont think we need this now
//    jvmToolchain(21)
//}