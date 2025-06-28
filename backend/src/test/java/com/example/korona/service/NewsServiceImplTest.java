package com.example.korona.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.korona.dto.NewsDTO;
import com.example.korona.exception.NewsParsingException;
import com.example.korona.mapper.NewsMapper;
import com.example.korona.model.News;
import com.example.korona.repository.NewsRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Haber Servisi Testleri")
class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private CityService cityService;

    @InjectMocks
    private NewsServiceImpl newsService;

    private List<String> mockCities;
    private News mockNews;
    private NewsDTO mockNewsDTO;

    @BeforeEach
    void setUp() {
        mockCities = Arrays.asList("İstanbul", "Ankara", "İzmir", "Bursa");

        mockNews = new News();
        mockNews.setContent("Test content");
        mockNews.setDate(LocalDate.of(2023, 12, 25));
        mockNews.setCity("İstanbul");
        mockNews.setDeath(5);
        mockNews.setInfected(100);
        mockNews.setDischarged(80);

        mockNewsDTO = new NewsDTO();
        mockNewsDTO.setContent("Test content");
        mockNewsDTO.setDate(LocalDate.of(2023, 12, 25));
        mockNewsDTO.setCity("İstanbul");
        mockNewsDTO.setDeath(5);
        mockNewsDTO.setInfected(100);
        mockNewsDTO.setDischarged(80);
    }

    @Test
    @DisplayName("Geçerli haber içeriği ile haber kaydedilmeli")
    void testAddNews_WithValidContent_ShouldSaveNews() {
        String content = "25.12.2023 tarihinde İstanbul'da 100 vaka, 5 vefat, 80 taburcu edildi.";
        when(cityService.getCities()).thenReturn(mockCities);
        when(newsMapper.toEntity(any(NewsDTO.class))).thenReturn(mockNews);
        when(newsRepository.save(any(News.class))).thenReturn(mockNews);

        assertDoesNotThrow(() -> newsService.addNews(content));

    }

    @Test
    @DisplayName("Tarih olmayan haberde hata fırlatılmalı")
    void testAddNews_WithInvalidDate_ShouldThrowException() {

        String content = "İstanbul'da 100 vaka, 5 vefat, 80 taburcu edildi.";

        assertThrows(NewsParsingException.class, () -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Geçersiz şehir adında hata fırlatılmalı")
    void testAddNews_WithInvalidCity_ShouldThrowException() {

        String content = "25.12.2023 tarihinde BilinmeyenŞehir'de 100 vaka, 5 vefat, 80 taburcu edildi.";
        when(cityService.getCities()).thenReturn(mockCities);

        assertThrows(NewsParsingException.class, () -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Vaka sayısı olmayan haberde hata fırlatılmalı")
    void testAddNews_WithMissingInfectedCount_ShouldThrowException() {

        String content = "25.12.2023 tarihinde İstanbul'da 5 vefat, 80 taburcu edildi."; // Vaka sayısı yok
        when(cityService.getCities()).thenReturn(mockCities);

        assertThrows(NewsParsingException.class, () -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Vefat sayısı olmayan haberde hata fırlatılmalı")
    void testAddNews_WithMissingDeathCount_ShouldThrowException() {

        String content = "25.12.2023 tarihinde İstanbul'da 100 vaka, 80 taburcu edildi."; // Vefat sayısı yok
        when(cityService.getCities()).thenReturn(mockCities);

        assertThrows(NewsParsingException.class, () -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Taburcu sayısı olmayan haberde hata fırlatılmalı")
    void testAddNews_WithMissingDischargedCount_ShouldThrowException() {

        String content = "25.12.2023 tarihinde İstanbul'da 100 vaka, 5 vefat edildi."; // Taburcu sayısı yok
        when(cityService.getCities()).thenReturn(mockCities);

        assertThrows(NewsParsingException.class, () -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Farklı tarih formatlarında çalışmalı")
    void testAddNews_WithDifferentDateFormats_ShouldWork() {

        String content = "01.01.2024 tarihinde Ankara'da 50 vaka, 2 vefat, 40 taburcu edildi.";
        when(cityService.getCities()).thenReturn(mockCities);
        when(newsMapper.toEntity(any(NewsDTO.class))).thenReturn(mockNews);
        when(newsRepository.save(any(News.class))).thenReturn(mockNews);

        assertDoesNotThrow(() -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Büyük-küçük harf duyarsız şehir ismi ile çalışmalı")
    void testAddNews_WithCaseInsensitiveCity_ShouldWork() {

        String content = "25.12.2023 tarihinde ankara'da 100 vaka, 5 vefat, 80 taburcu edildi.";
        when(cityService.getCities()).thenReturn(mockCities);
        when(newsMapper.toEntity(any(NewsDTO.class))).thenReturn(mockNews);
        when(newsRepository.save(any(News.class))).thenReturn(mockNews);

        assertDoesNotThrow(() -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Farklı anahtar kelime varyasyonları ile çalışmalı")
    void testAddNews_WithDifferentKeywordVariations_ShouldWork() {

        String content = "25.12.2023 tarihinde İstanbul'da 100 VAKA tespit edildi. 5 kişi vefat etti. 80 hasta taburcu oldu.";
        when(cityService.getCities()).thenReturn(mockCities);
        when(newsMapper.toEntity(any(NewsDTO.class))).thenReturn(mockNews);
        when(newsRepository.save(any(News.class))).thenReturn(mockNews);

        assertDoesNotThrow(() -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Tüm haberleri DTO olarak döndürmeli")
    void testGetAllNews_ShouldReturnAllNewsAsDTOs() {

        List<News> newsList = Arrays.asList(mockNews);
        when(newsRepository.findAll()).thenReturn(newsList);
        when(newsMapper.toDTO(mockNews)).thenReturn(mockNewsDTO);

        List<NewsDTO> result = newsService.getAllNews();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockNewsDTO, result.get(0));

        verify(newsRepository).findAll();
        verify(newsMapper).toDTO(mockNews);
    }

    @Test
    @DisplayName("Boş repository'de boş liste döndürmeli")
    void testGetAllNews_WithEmptyRepository_ShouldReturnEmptyList() {

        when(newsRepository.findAll()).thenReturn(Arrays.asList());

        List<NewsDTO> result = newsService.getAllNews();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(newsRepository).findAll();
    }

    @Test
    @DisplayName("Tarihe göre gruplu haberleri döndürmeli")
    void testGetNewsGroupedByDate_ShouldReturnGroupedNews() {

        List<NewsDTO> expectedNews = Arrays.asList(mockNewsDTO);
        when(newsRepository.findNewsGroupedByDate()).thenReturn(expectedNews);

        List<NewsDTO> result = newsService.getNewsGroupedByDate();

        assertNotNull(result);
        assertEquals(expectedNews, result);
        verify(newsRepository).findNewsGroupedByDate();
    }

    @Test
    @DisplayName("Şehre göre gruplu haberleri döndürmeli")
    void testGetNewsGroupedByCity_ShouldReturnGroupedNewsByCity() {

        String city = "İstanbul";
        List<NewsDTO> expectedNews = Arrays.asList(mockNewsDTO);
        when(newsRepository.findNewsGroupedByCity(city)).thenReturn(expectedNews);

        List<NewsDTO> result = newsService.getNewsGroupedByCity(city);

        assertNotNull(result);
        assertEquals(expectedNews, result);
        verify(newsRepository).findNewsGroupedByCity(city);
    }

    @Test
    @DisplayName("Karmaşık haber içeriğini doğru ayrıştırmalı")
    void testAddNews_WithComplexNewsContent_ShouldParseCorrectly() {
        String content = "Sağlık Bakanlığı açıkladı: 15.03.2023 tarihinde Bursa ilinde yapılan testlerde " +
                "toplam 250 yeni vaka tespit edilmiştir. Maalesef 12 vatandaşımız vefat etmiştir. " +
                "Aynı zamanda 180 hasta taburcu edilmiştir. Vatandaşlarımızın dikkatli olması gerekmektedir.";

        when(cityService.getCities()).thenReturn(mockCities);
        when(newsMapper.toEntity(any(NewsDTO.class))).thenReturn(mockNews);
        when(newsRepository.save(any(News.class))).thenReturn(mockNews);

        assertDoesNotThrow(() -> newsService.addNews(content));

        verify(cityService).getCities();
        verify(newsMapper).toEntity(any(NewsDTO.class));
        verify(newsRepository).save(any(News.class));
    }

    @Test
    @DisplayName("Birden fazla tarih içeren metinde ilk geçerli tarihi kullanmalı")
    void testAddNews_WithMultipleDatesInText_ShouldUseFirstValidDate() {
        String content = "Geçen ay 01.01.2023 tarihinde başlayan süreçte, bugün 25.12.2023 tarihinde " +
                "İstanbul'da 100 vaka, 5 vefat, 80 taburcu edildi.";

        when(cityService.getCities()).thenReturn(mockCities);
        when(newsMapper.toEntity(any(NewsDTO.class))).thenReturn(mockNews);
        when(newsRepository.save(any(News.class))).thenReturn(mockNews);

        assertDoesNotThrow(() -> newsService.addNews(content));
    }

    @Test
    @DisplayName("Farklı cümlelerdeki sayıları doğru ayrıştırmalı")
    void testAddNews_WithNumbersInDifferentSentences_ShouldParseCorrectly() {
        String content = "25.12.2023 tarihli rapor. İstanbul'da durum kritik. " +
                "Vaka sayısı 150 olarak açıklandı. " +
                "Vefat eden hasta sayısı 8 kişi. " +
                "Taburcu olan hasta sayısı ise 120 kişidir.";

        when(cityService.getCities()).thenReturn(mockCities);
        when(newsMapper.toEntity(any(NewsDTO.class))).thenReturn(mockNews);
        when(newsRepository.save(any(News.class))).thenReturn(mockNews);

        assertDoesNotThrow(() -> newsService.addNews(content));
    }
}