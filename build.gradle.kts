import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.50"
	kotlin("plugin.spring") version "1.3.50"
	kotlin("plugin.allopen") version "1.3.61"
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
	compile("org.springframework.boot:spring-boot-starter-web")
	compile("org.jetbrains.kotlin:kotlin-reflect")
	compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	compile("org.springframework.boot:spring-boot-starter-data-jdbc")
	compile("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.rest-assured:spring-mock-mvc:3.0.0")
// https://mvnrepository.com/artifact/com.ninja-squad/springmockk
	testImplementation("com.ninja-squad:springmockk:2.0.0")

	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("io.mockk:mockk:1.9.3")
	testImplementation("com.h2database:h2:1.4.200")

	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
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
