import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
    // para serializar Json y otros
    kotlin("plugin.serialization") version "1.7.20"

    // Para ktorfit que usa KSP
    // Plugin KSP para generar código en tiempo de compilación ktorfit
    id("com.google.devtools.ksp") version "1.7.21-1.0.8"

    // SQLdelight
    id("com.squareup.sqldelight") version "1.5.4"
}

group = "js.mendoza.acosta"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // Para hacer el logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    // Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // Serializa Json con Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    // Ktorfit, es decir Ktor client modificado para parecerse a Retrofit
    ksp("de.jensklingenberg.ktorfit:ktorfit-ksp:1.0.0-beta16")
    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:1.0.0-beta16")
    // Para serializar en Json con Ktor
    implementation("io.ktor:ktor-client-serialization:2.1.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")
    // KMongo
    implementation("org.litote.kmongo:kmongo:4.7.1")
    // Cache
    implementation("io.github.reactivecircus.cache4k:cache4k:0.9.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

// Donde vamos a generar el código
buildscript {
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.4")
    }
}

sqldelight {
    // Debemos colocarlo en el main
    database("AppDatabase") {
        // Como se llama el paquete donde esta el código
        packageName = "database"
    }
}