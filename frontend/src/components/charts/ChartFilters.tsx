import React from 'react';

interface ChartFiltersProps {
    cities: string[];
    selectedCity: string;
    isCumulative: boolean;
    onCityChange: (city: string) => void;
    onCumulativeChange: (isCumulative: boolean) => void;
}

const ChartFilters: React.FC<ChartFiltersProps> = ({
    cities,
    selectedCity,
    isCumulative,
    onCityChange,
    onCumulativeChange
}) => {
    const handleCityChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        onCityChange(e.target.value);
    };

    const handleCumulativeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        onCumulativeChange(e.target.checked);
    };

    return (
        <div style={{
            margin: '0 0 20px 0',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            gap: '30px',
            flexWrap: 'wrap'
        }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                <label style={{ fontWeight: 'bold', fontSize: '14px' }}>Şehir:</label>
                <select
                    onChange={handleCityChange}
                    value={selectedCity}
                    style={{
                        padding: '8px 12px',
                        fontSize: '14px',
                        border: '2px solid #ddd',
                        borderRadius: '6px',
                        outline: 'none',
                        minWidth: '120px',
                        cursor: 'pointer'
                    }}
                >
                    <option value="Tümü">Tümü</option>
                    {cities.map(city => (
                        <option key={city} value={city}>{city}</option>
                    ))}
                </select>
            </div>

            <label style={{
                display: 'flex',
                alignItems: 'center',
                gap: '8px',
                fontSize: '14px',
                cursor: 'pointer',
                userSelect: 'none'
            }}>
                <input
                    type="checkbox"
                    checked={isCumulative}
                    onChange={handleCumulativeChange}
                    style={{
                        width: '16px',
                        height: '16px',
                        cursor: 'pointer'
                    }}
                />
                <span style={{ fontWeight: 'bold' }}>Kümülatif Göster</span>
            </label>
        </div>
    );
};

export default ChartFilters; 