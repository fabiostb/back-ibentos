package com.ibento;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class IbentoInstant {

    public Instant now() {
        return Instant.now();
    }
}
