package com.example.userservice.vo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Greeting {

    @Value("${greeting.message}")
    private String message;

    public Greeting(@Value("${greeting.message}") String message) {
        this.message = message;
    }
}
