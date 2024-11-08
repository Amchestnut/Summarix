plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("com.google.code.gson:gson:2.10.1")
    // Implementation: Means that other modules depending on this module will also inherit the SPEC_MODULE dependency
    implementation(project(":spec_module"))     // The ":" at the beginning tells Gradle to look for spec_module starting from the ROOT of the entire project structure.  Without ":" gradle would interpret "spec_module" as relative path

    // Runtime only:   Means that pdf_module & excel_module are only needed at RUNTIME, and are NOT NEEDED AT COMPILE TIME.
    runtimeOnly(project(":pdf_module"))
    runtimeOnly(project(":excel_module"))
    runtimeOnly(project(":csv_module"))
    runtimeOnly(project(":txt_module"))
}

tasks.test {
    useJUnitPlatform()
}