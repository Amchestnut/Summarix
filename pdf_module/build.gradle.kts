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
    implementation("com.github.librepdf:openpdf:1.3.29")
    implementation(project(":spec_module"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava"){
            from(components["java"])

            groupId = "org.example"
            artifactId = "pdf_module"
            version = "1.0.0"
        }
    }
}
