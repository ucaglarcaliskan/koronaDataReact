package com.example.korona.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDTO {
    private String id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDate date;
    
    private String city;
    
    private Integer death;
    
    private Integer infected;
    
    private Integer discharged;

    @Override
    public String toString() {
        return "NewsDTO [id=" + id + ", content=" + content + ", createdAt=" + createdAt + ", date=" + date + ", city=" + city + ", death=" + death + ", infected=" + infected + ", discharged=" + discharged + "]";
    }
}

