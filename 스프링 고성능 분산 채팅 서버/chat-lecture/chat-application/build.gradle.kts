plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.4")
    }
}

dependencies {
    // 모든 하위 모듈 포함 (조립자 역할)
//    implementation(project(":chat-api"))
//    implementation(project(":chat-domain"))
//    implementation(project(":chat-persistence"))
//    implementation(project(":chat-websocket"))

    // 메인 애플리케이션 실행에 필요한 의존성만
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
}
