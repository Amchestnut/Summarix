plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "1.8.10"
    `java-library`
    `maven-publish`
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.mockito:mockito-core:4.0.0") // Core Mockito library
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0") // Mockito-Kotlin extensions
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc{
    /// da bi se ovaj javadoc izvrsio, mora prvo da se izvrsi ovaj dokkaJavadoc, kao nekako rekurzivno
    dependsOn(tasks.dokkaJavadoc)
    doLast{
        println("Javadoc task completed with Dokka output.")
    }
}

tasks.dokkaJavadoc{
    outputDirectory.set(file("build/dokka/javadoc"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "org.example"
            artifactId = "spec_module"
            version = "1.0.0"
        }
    }
}