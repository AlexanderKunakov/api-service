plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.7.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // TEST
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/src/main/resources/openapi.yaml")
    outputDir.set(layout.buildDirectory.dir("generated").get().asFile.absolutePath)
    apiPackage.set("com.example.demo.api")
    modelPackage.set("com.example.demo.model")
    configOptions.put("reactive", "true")
    configOptions.put("annotationLibrary", "swagger2")
    configOptions.put("interfaceOnly", "true")
    configOptions.put("requestMappingMode", "api_interface")
    configOptions.put("library", "spring-boot")
    configOptions.put("useSpringBoot3", "true")
    configOptions.put("useBeanValidation", "false") // todo sometimes it's needed
    configOptions.put("documentationProvider", "none") // todo might be useful tho
}

sourceSets {
    main {
        java {
            srcDir(layout.buildDirectory.dir("generated/src/main/kotlin"))
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
