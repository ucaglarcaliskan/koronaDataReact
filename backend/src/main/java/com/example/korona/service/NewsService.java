package com.example.korona.service;

import java.util.List;

import com.example.korona.dto.NewsDTO;

public interface NewsService {

    public void addNews(String content);

    public List<NewsDTO> getAllNews();

    public List<NewsDTO> getNewsGroupedByDate();    
    
    public List<NewsDTO> getNewsGroupedByCity(String city);
}
