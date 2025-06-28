import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import type { ChartData } from '../../types';

interface CovidChartProps {
    data: ChartData[];
    isCumulative: boolean;
}

const CovidChart: React.FC<CovidChartProps> = ({ data, isCumulative }) => {
    return (
        <div style={{ width: '100%', height: 600, margin: '20px 0' }}>
            <h3 style={{
                textAlign: 'center',
                marginBottom: '20px',
                color: '#333',
                fontSize: '18px'
            }}>
                COVID-19 Veri Grafiği {isCumulative ? '(Kümülatif)' : '(Günlük)'}
            </h3>

            {data.length === 0 ? (
                <div style={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '400px',
                    backgroundColor: '#f8f9fa',
                    borderRadius: '8px',
                    border: '2px dashed #ddd'
                }}>
                    <p style={{ color: '#666', fontSize: '16px' }}>
                        Henüz veri bulunmuyor. Lütfen haber ekleyin.
                    </p>
                </div>
            ) : (
                <ResponsiveContainer>
                    <LineChart
                        data={data}
                        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                    >
                        <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
                        <XAxis
                            dataKey="date"
                            tick={{ fontSize: 12 }}
                            stroke="#666"
                        />
                        <YAxis
                            domain={[0, 'dataMax + 1']}
                            tick={{ fontSize: 12 }}
                            stroke="#666"
                        />
                        <Tooltip
                            contentStyle={{
                                backgroundColor: '#fff',
                                border: '1px solid #ddd',
                                borderRadius: '8px',
                                boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
                            }}
                        />
                        <Legend />
                        <Line
                            type="monotone"
                            dataKey="infected"
                            name="Vaka"
                            stroke="#8884d8"
                            strokeWidth={3}
                            dot={{ r: 4, fill: '#8884d8' }}
                            activeDot={{ r: 6, fill: '#8884d8' }}
                        />
                        <Line
                            type="monotone"
                            dataKey="death"
                            name="Vefat"
                            stroke="#ff4d4f"
                            strokeWidth={3}
                            dot={{ r: 4, fill: '#ff4d4f' }}
                            activeDot={{ r: 6, fill: '#ff4d4f' }}
                        />
                        <Line
                            type="monotone"
                            dataKey="discharged"
                            name="Taburcu"
                            stroke="#4caf50"
                            strokeWidth={3}
                            dot={{ r: 4, fill: '#4caf50' }}
                            activeDot={{ r: 6, fill: '#4caf50' }}
                        />
                    </LineChart>
                </ResponsiveContainer>
            )}
        </div>
    );
};

export default CovidChart; 