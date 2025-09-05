plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("io.spring.dependency-management")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.4")
    }
}

dependencies {
    // 도메인 모듈 의존성
    implementation(project(":chat-domain"))

    // 데이터 접근을 위한 JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Redis 캐시 및 Pub/Sub
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // WebSocket (WebSocketSessionManager에서 사용)
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // Jackson (Redis 직렬화 및 WebSocket 메시지 처리용)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // 데이터베이스 드라이버 (런타임)
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
}