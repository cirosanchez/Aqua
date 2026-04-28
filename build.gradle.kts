plugins {
    kotlin("jvm") version "2.3.10"
    id("com.vanniktech.maven.publish") version "0.33.0"
}

group = "dev.cirosanchez.aqua"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    // Unit testing
    testImplementation(kotlin("test"))

    // MongoDB
    implementation(platform("org.mongodb:mongodb-driver-bom:5.6.4"))
    implementation("org.mongodb:mongodb-driver-kotlin-sync")
    implementation("org.mongodb:bson-kotlinx")

    // Kotlinx Async Library
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0-rc01")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

mavenPublishing {
    coordinates("dev.cirosanchez.aqua", "aqua", "0.0.1")

    pom {
        name.set("Aqua")
        description.set("In-memory persistent cache backed by asynchronous MongoDB")
        inceptionYear.set("2026")
        url.set("https://github.com/cirosanchez/Aqua/")
        licenses {
            license {
                name.set("MIT")
            }
        }
        developers {
            developer {
                id.set("cirosanchez")
                name.set("Ciro Sánchez")
                url.set("https://github.com/cirosanchez/")
            }
        }
        scm {
            url.set("https://github.com/cirosanchez/Aqua/")
            connection.set("scm:git:git://github.com/cirosanchez/Aqua.git")
            developerConnection.set("scm:git:ssh://git@github.com/cirosanchez/Aqua.git")
        }
    }
}

