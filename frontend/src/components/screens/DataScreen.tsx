import React from 'react';
import { NewsInput } from '../forms';
import { ChartFilters, CovidChart } from '../charts';
import { Accordion } from '../ui';
import { useChartData } from '../../hooks/useChartData';

const DataScreen: React.FC = () => {
  const {
    chartData,
    cities,
    selectedCity,
    isCumulative,
    handleCityChange,
    handleCumulativeChange,
    refreshData
  } = useChartData();

  return (
    <div style={{ fontFamily: 'sans-serif', padding: '20px', maxWidth: '1200px', margin: '0 auto' }}>
      <h1 style={{ textAlign: 'center', marginBottom: '30px', color: '#333' }}>
        COVID-19 Veri Analizi
      </h1>

      <Accordion title="📝 Haber Girişi" defaultOpen={true}>
        <NewsInput onNewsAdded={refreshData} />
      </Accordion>

      <Accordion title="📊 Grafik" defaultOpen={true}>
        <ChartFilters
          cities={cities}
          selectedCity={selectedCity}
          isCumulative={isCumulative}
          onCityChange={handleCityChange}
          onCumulativeChange={handleCumulativeChange}
        />

        <CovidChart
          data={chartData}
          isCumulative={isCumulative}
        />
      </Accordion>
    </div>
  );
};

export default DataScreen;