plugins {
    id("java")
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.savelevvn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Зависимости SQLITE
    implementation("org.xerial:sqlite-jdbc:3.48.0.0")
    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-community-dialects
    implementation("org.hibernate.orm:hibernate-community-dialects")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Зависимости Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // Зависимости lombok
    compileOnly ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")
    testCompileOnly ("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.36")

}

tasks.test {
    useJUnitPlatform()
}

// Задача для увеличения минорной версии
tasks.register("incrementMinorVersion") {
    doLast {
        val versionParts = version.toString().split(".")
        val majorVersion = versionParts[0].toInt()
        val minorVersion = versionParts[1].toInt()
        val newVersion = "$majorVersion.${minorVersion + 1}-SNAPSHOT"

        // Обновляем версию в файле build.gradle.kts
        val buildFile = file("build.gradle.kts")
        val updatedContent = buildFile.readText().replace(
            "version = \"${version}\"",
            "version = \"$newVersion\""
        )
        buildFile.writeText(updatedContent)

        println("Version incremented to: $newVersion")
    }
}

// Настройка задачи, чтобы она выполнялась после сборки
tasks.build {
    finalizedBy("incrementMinorVersion")
}

tasks.register("commitVersionChange") {
    doLast {
        exec {
            commandLine("git", "add", "build.gradle.kts")
            commandLine("git", "commit", "-m", "Auto-increment version to $version")
        }
    }
}