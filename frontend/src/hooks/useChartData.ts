import { useState, useEffect } from 'react';
import axios from 'axios';
import type { NewsDTO, ChartData } from '../types';

export const useChartData = () => {
    const [chartData, setChartData] = useState<ChartData[]>([]);
    const [originalData, setOriginalData] = useState<ChartData[]>([]);
    const [cities, setCities] = useState<string[]>([]);
    const [selectedCity, setSelectedCity] = useState<string>('Tümü');
    const [isCumulative, setIsCumulative] = useState<boolean>(false);

    const calculateCumulativeData = (data: ChartData[]): ChartData[] => {
        let cumulativeInfected = 0;
        let cumulativeDeath = 0;
        let cumulativeDischarged = 0;

        return data.map(item => {
            cumulativeInfected += item.infected;
            cumulativeDeath += item.death;
            cumulativeDischarged += item.discharged;

            return {
                date: item.date,
                infected: cumulativeInfected,
                death: cumulativeDeath,
                discharged: cumulativeDischarged
            };
        });
    };

    useEffect(() => {
        if (originalData.length > 0) {
            if (isCumulative) {
                setChartData(calculateCumulativeData(originalData));
            } else {
                setChartData(originalData);
            }
        }
    }, [isCumulative, originalData]);

    const fetchChartData = async (city?: string) => {
        try {
            const url = city && city !== 'Tümü'
                ? `http://localhost:8080/api/news?city=${encodeURIComponent(city)}`
                : 'http://localhost:8080/api/news';
            const response = await axios.get<NewsDTO[]>(url);
            console.log("Response: {}", response.data);

            const chartData: ChartData[] = response.data.map(item => ({
                date: item.date,
                infected: item.infected || 0,
                death: item.death || 0,
                discharged: item.discharged || 0
            }));

            const sortedData = chartData.sort((a, b) => {
                const dateA = new Date(a.date.split('.').reverse().join('-'));
                const dateB = new Date(b.date.split('.').reverse().join('-'));
                return dateA.getTime() - dateB.getTime();
            });

            setOriginalData(sortedData);

            if (isCumulative) {
                setChartData(calculateCumulativeData(sortedData));
            } else {
                setChartData(sortedData);
            }
        } catch (error) {
            console.error('Grafik verileri alınırken hata oluştu:', error);
        }
    };

    const fetchCities = async () => {
        try {
            const response = await axios.get<string[]>('http://localhost:8080/api/cities');
            setCities(response.data);
        } catch (error) {
            console.error('Şehirler alınırken hata oluştu:', error);
        }
    };

    const handleCityChange = async (city: string) => {
        setSelectedCity(city);
        await fetchChartData(city);
    };

    const handleCumulativeChange = (cumulative: boolean) => {
        setIsCumulative(cumulative);
    };

    const refreshData = async () => {
        await fetchChartData(selectedCity);
    };

    useEffect(() => {
        fetchChartData();
        fetchCities();
    }, []);

    return {
        chartData,
        cities,
        selectedCity,
        isCumulative,
        handleCityChange,
        handleCumulativeChange,
        refreshData
    };
}; 