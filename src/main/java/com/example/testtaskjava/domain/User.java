package com.example.testtaskjava.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
public class User {

    private final String ip;
    private LocalDateTime start;
    private Long count;
}
