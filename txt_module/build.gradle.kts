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
}

tasks.test {
    useJUnitPlatform()
}

// didnt add "publishing", lets see if it works without that (P.S.) IT WORKS, BUT NEED TO ADD 'JAVA-LIBRARY' and 'PUBLISH' in plugins

//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            from(components["java"]) // If you're using the 'java' or 'kotlin' plugin
//
//            groupId = "org.example"
//            artifactId = "cetvrtaImpl"
//            version = "1.0.0"
//        }
//    }
//}
