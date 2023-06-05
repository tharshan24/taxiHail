import React, { useState, useEffect } from 'react';
import {message, Table} from 'antd';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const CurrentRidesPage = () => {

    const navigate = useNavigate();

    console.log(sessionStorage.getItem("role"))
    if(sessionStorage.getItem("role") !== "PASSENGER") {
        navigate("/")
    }

    const [currentRides, setCurrentRides] = useState<any[]>([]);
    const [loading, setLoading] = useState(false);

    const fetchCurrentRides = async () => {
        setLoading(true);
        try {
            const token = sessionStorage.getItem("accessToken");
            const response = await axios.get('http://localhost:8080/ride/current_rides', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const ridesData = response.data;
            setCurrentRides((prevRides: any) => [...prevRides, ...ridesData]);
        } catch (error) {
            console.error('Error fetching current rides:', error);
            message.error("Error fetching current rides");
            navigate("/");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCurrentRides();
        const interval = setInterval(fetchCurrentRides, 5000); // Fetch current rides every 5 seconds
        return () => clearInterval(interval);
    }, []);

    const columns = [
        {
            title: 'Ride ID',
            dataIndex: 'rideId',
            key: 'rideId',
        },
        {
            title: 'Pickup',
            dataIndex: 'pickupLocation',
            key: 'pickupLocation',
        },
        {
            title: 'Destination',
            dataIndex: 'destinationLocation',
            key: 'destinationLocation',
        },
        {
            title: 'Driver',
            dataIndex: 'driver',
            key: 'driver',
        },
        {
            title: 'Vehicle',
            dataIndex: 'vehicle',
            key: 'vehicle',
        },
        {
            title: 'Vehicle Number',
            dataIndex: 'vehicleNumber',
            key: 'vehicleNumber',
        },
        {
            title: 'Amount',
            dataIndex: 'amount',
            key: 'amount',
        },
        {
            title: 'Status',
            dataIndex: 'status',
            key: 'status',
        },
    ];

    return (
        <div>
            {loading ? (
                <div>Loading...</div>
            ) : currentRides.length > 0 ? (
                <Table dataSource={currentRides} columns={columns} />
            ) : (
                <div>No rides currently.</div>
            )}
        </div>
    );
};

export default CurrentRidesPage;
