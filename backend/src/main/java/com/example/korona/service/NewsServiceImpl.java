package com.example.korona.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.korona.dto.NewsDTO;
import com.example.korona.exception.NewsParsingException;
import com.example.korona.mapper.NewsMapper;
import com.example.korona.model.News;
import com.example.korona.repository.NewsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CityService cityService;

    public NewsServiceImpl(NewsRepository newsRepository, NewsMapper newsMapper, CityService cityService) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.cityService = cityService;
    }

    public void addNews(String content) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setContent(content);

        String dateStr = getDate(content);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        newsDTO.setDate(date);

        newsDTO.setCity(getCity(content));
        newsDTO.setDeath(getDeathCount(content));
        newsDTO.setInfected(getInfectedCount(content));
        newsDTO.setDischarged(getDischargedCount(content));

        newsRepository.save(newsMapper.toEntity(newsDTO));
    }

    public List<NewsDTO> getAllNews() {
        List<News> news = newsRepository.findAll();
        return news.stream().map(newsMapper::toDTO).collect(Collectors.toList());
    }

    public List<NewsDTO> getNewsGroupedByDate() {
        return newsRepository.findNewsGroupedByDate();
    }

    public List<NewsDTO> getNewsGroupedByCity(String city) {
        return newsRepository.findNewsGroupedByCity(city);
    }

    private String getCity(String text) {
        for (String city : cityService.getCities()) {
            if (text.toLowerCase().contains(city.toLowerCase())) {
                return city;
            }
        }
        throw new NewsParsingException("Haber metninde şehir bulunamadı");
    }

    private String getDate(String text) {
        Pattern pattern = Pattern.compile("\\b\\d{2}\\.\\d{2}\\.\\d{4}\\b");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            log.info("Bulunan tarih: " + matcher.group());
            return matcher.group();
        }
        log.warn("Tarih bulunamadı!");
        throw new NewsParsingException("Haber metninde tarih bulunamadı");
    }

    private Integer extractCountByKeyword(String text, String keyword, String logMessage) {
        // Tarihi sil
        Pattern datePattern = Pattern.compile("\\b\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\b");
        Matcher dateMatcher = datePattern.matcher(text);
        String textWithoutDate = dateMatcher.replaceAll("");

        String[] sentences = textWithoutDate.split("\\.");

        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(keyword.toLowerCase())) {

                Pattern numberPattern = Pattern.compile("\\b\\d+\\b");
                Matcher matcher = numberPattern.matcher(sentence);
                if (matcher.find()) {
                    return Integer.parseInt(matcher.group());
                }
            }
        }
        log.warn(logMessage + " bulunamadı!");
        throw new NewsParsingException("Haber metninde " + logMessage.toLowerCase() + " bulunamadı");
    }

    private Integer getDeathCount(String text) {
        return extractCountByKeyword(text, "vefat", "Vefat sayısı");
    }

    private Integer getInfectedCount(String text) {
        return extractCountByKeyword(text, "vaka", "Vaka sayısı");
    }

    private Integer getDischargedCount(String text) {
        return extractCountByKeyword(text, "taburcu", "Taburcu sayısı");
    }
}
