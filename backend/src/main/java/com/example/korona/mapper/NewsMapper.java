package com.example.korona.mapper;

import com.example.korona.dto.NewsDTO;
import com.example.korona.model.News;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsMapper {

    public NewsDTO toDTO(News news) {
        if (news == null) {
            return null;
        }

        return NewsDTO.builder()
                .id(news.getId())
                .content(news.getContent())
                .createdAt(news.getCreatedAt())
                .date(news.getDate())
                .city(news.getCity())
                .death(news.getDeath())
                .infected(news.getInfected())
                .discharged(news.getDischarged())
                .build();
    }

    public News toEntity(NewsDTO newsDTO) {
        if (newsDTO == null) {
            return null;
        }

        return News.builder()
                .id(newsDTO.getId())
                .content(newsDTO.getContent())
                .createdAt(newsDTO.getCreatedAt())
                .date(newsDTO.getDate())
                .city(newsDTO.getCity())
                .death(newsDTO.getDeath())
                .infected(newsDTO.getInfected())
                .discharged(newsDTO.getDischarged())
                .build();
    }

   
    public List<NewsDTO> toDTOList(List<News> newsList) {
        if (newsList == null) {
            return null;
        }

        return newsList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<News> toEntityList(List<NewsDTO> newsDTOList) {
        if (newsDTOList == null) {
            return null;
        }

        return newsDTOList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}