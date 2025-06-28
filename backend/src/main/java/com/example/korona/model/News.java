package com.example.korona.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "news") 
@Builder
public class News {

    @Id
    private String id;

    private String content;

    private LocalDateTime createdAt;
    private LocalDate date;
    private String city;
    private Integer death;
    private Integer infected;
    private Integer discharged;
}