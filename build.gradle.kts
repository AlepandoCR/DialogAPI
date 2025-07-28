plugins {
    kotlin("jvm") version "2.1.21"
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
}

group = "alepando.dev"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.opencollab.dev/main/") {
        name = "opencollab-snapshot"
    }
    maven("https://repo.viaversion.com")
}

dependencies {
    paperweight.paperDevBundle("1.21.7-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("com.viaversion:viaversion-api:5.4.1")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }
}
