import React, { useEffect, useState } from 'react';
import axios from "../../api/axios";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import './DashChart.css';

const DashChart = ({ petFeederId }) => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchChartData = async () => {

        const startDate = new Date();
        startDate.setDate(startDate.getDate() - 6);

        try {
            const response = await axios.post(
                '/petFeederHistory/getHistoryByDateInterval',
                {
                    petFeederId: petFeederId,
                    startDate: startDate.toISOString().split('T')[0],
                    endDate: new Date().toISOString().split('T')[0],
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );

            const processedData = processChartData(response.data);
            setData(processedData);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching chart data:', error);
        }
    };

    const processChartData = (rawData) => {

        const groupedData = rawData.reduce((result, entry) => {
            const date = entry.date.split('T')[0];
            if (!result[date]) {
                result[date] = { date, foodHumiditySum: 0, waterTemperatureSum: 0, count: 0 };
            }
            result[date].foodHumiditySum += entry.foodHumidity;
            result[date].waterTemperatureSum += entry.waterTemperature;
            result[date].count += 1;
            return result;
        }, {});


        const processedData = Object.values(groupedData).map(entry => ({
            date: entry.date,
            foodHumidity: entry.foodHumiditySum / entry.count,
            waterTemperature: entry.waterTemperatureSum / entry.count,
        }));

        return processedData;
    };

    useEffect(() => {
        fetchChartData();
    }, []); // Empty dependency array ensures this effect runs once when the component mounts (warning in terminal is nessecary)

    const renderLineChart = (
        <ResponsiveContainer width="100%" height={413}>
            <LineChart data={data} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
                <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="foodHumidity" stroke="#8884d8" name="Food Humidity" />
                <Line type="monotone" dataKey="waterTemperature" stroke="#82ca9d" name="Water Temperature" />
            </LineChart>
        </ResponsiveContainer>
    );

    return (
        <div className="dashchartt">
            <h3 className='chartTitle ms-4 mb-4'>Food Humidity and Water Temperature</h3>
            {loading ? (
                <p className='text-white'>Loading...</p>
            ) : (
                renderLineChart
            )}
        </div>
    );
};

export default DashChart;
