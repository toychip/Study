package com.chat.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.chat.application",
//        "com.chat.domain",        domain 모델과 서비스 인터페이스
//        "com.chat.persistence",   repository와 서비스 클래스 구현체
//        "com.chat.api",           REST API
//        "com.chat.websocket",     ws 통신 관련 component
    ]
)
class ChatApplication

fun main(args: Array<String>) {
    runApplication<ChatApplication>(*args)
}