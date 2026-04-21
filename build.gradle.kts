plugins {
    kotlin("jvm") version "2.3.10"
}

group = "dev.cirosanchez"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(platform("org.mongodb:mongodb-driver-bom:5.6.4"))
    implementation("org.mongodb:mongodb-driver-kotlin-sync")

    implementation("org.mongodb:bson-kotlinx")
}

kotlin {
    jvmToolchain(11)
}

tasks.test {
    useJUnitPlatform()
}