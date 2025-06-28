package com.example.korona.controller;

import com.example.korona.dto.NewsDTO;
import com.example.korona.exception.NewsParsingException;
import com.example.korona.model.News;
import com.example.korona.repository.NewsRepository;
import com.example.korona.service.CityService;
import com.example.korona.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class NewsController {

    private final NewsService newsService;
    private final CityService cityService;

    @Autowired
    public NewsController(NewsService newsService, CityService cityService) {
        this.newsService = newsService;
        this.cityService = cityService;
    }

    public static class NewsTextRequest {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @PostMapping("/news")
    public ResponseEntity<?> addNews(@RequestBody NewsTextRequest request) {
        if (request.getText() == null || request.getText().isBlank()) {
            return ResponseEntity.badRequest().body("Metin boş olamaz");
        }

        try {
            newsService.addNews(request.getText());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NewsParsingException e) {
            log.warn("Haber parsing hatası: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Beklenmeyen hata: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Beklenmeyen bir hata oluştu");
        }
    }

    @GetMapping("/news")
    public ResponseEntity<List<NewsDTO>> getNews(@RequestParam(required = false) String city) {
        List<NewsDTO> news = new ArrayList<>();
        if (city == null) {
            news = newsService.getNewsGroupedByDate();
            log.info("Tüm şehirler için gelen veri: {}", news.toString());

        } else {
            news = newsService.getNewsGroupedByCity(city);
            log.info("{} şehri için bulunan veri: {}", city, news.toString());
        }
        return ResponseEntity.ok(news);
    }
}
