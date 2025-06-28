package com.example.korona.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Şehir Servisi Testleri")
class CityServiceImplTest {

    private CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        cityService = new CityServiceImpl();
    }

    @Test
    @DisplayName("Tüm Türkiye şehirlerini döndürmeli")
    void testGetCities_ShouldReturnAllTurkishCities() {

        List<String> cities = cityService.getCities();

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        assertEquals(81, cities.size()); 
    }
}