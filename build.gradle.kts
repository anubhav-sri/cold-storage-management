import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//buildscript {
//    dependencies {
//        classpath "org.jetbrains.kotlin:kotlin-noarg:$kotlin_version"
//    }
//}
//
//apply plugin: "kotlin-jpa"
plugins {
    id("application")
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    kotlin("plugin.allopen") version "1.3.61"
    kotlin("kapt") version "1.3.61"
    kotlin("plugin.jpa") version "1.3.61"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

group = "com.saikrishna"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.5.3")
    implementation("org.postgresql:postgresql:42.2.9")
    implementation("org.projectlombok:lombok:1.18.12")
    implementation("org.springframework.boot:spring-boot-starter-security")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.rest-assured:spring-mock-mvc:3.0.0")
    testImplementation("com.ninja-squad:springmockk:2.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.mockk:mockk:1.9.3")
    implementation("com.h2database:h2:1.4.200")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.flywaydb:flyway-core:6.1.3")
    implementation("com.auth0:java-jwt:3.10.3")

}

application {
    mainClassName = "com.saikrishna.wms.ApplicationKt"
}
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
