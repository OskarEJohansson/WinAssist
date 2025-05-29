plugins {
    kotlin("jvm") version "2.1.20" apply false
}

group = "dev.OskarJohansson"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}