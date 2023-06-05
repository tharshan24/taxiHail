import React, {useEffect, useState} from 'react';
import {Button, message, Modal, Table} from 'antd';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const DriverRides = () => {

    const navigate = useNavigate();

    console.log(sessionStorage.getItem("role"))
    if (sessionStorage.getItem("role") !== "DRIVER") {
        navigate("/")
    }

    const [currentRides, setCurrentRides] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);

    const rideStatusLabels: any = {
        0: 'Cancelled',
        1: 'Pending',
        2: 'Driver Connected',
        3: 'In Ride',
        4: 'Completed',
    };

    const fetchCurrentRides = async () => {
        // setLoading(true);
        try {
            const token = sessionStorage.getItem("accessToken");
            const response = await axios.get('http://localhost:8080/ride/current_rides', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const ridesData = response.data;

            await ridesData.forEach((ride: any) => {
                ride.status = rideStatusLabels[ride.rideStatus] || 'Unknown';
                ride.picupLocation = ride.pickupLocationLatitude + " , " + ride.pickupLocationLongitude;
                ride.destinationLocation = ride.destinationLocationLatitude + " , " + ride.destinationLocationLongitude;
                ride.amount = ride.amount === null ? "TBD" : ride.amount;
            });

            setCurrentRides((prevRides: any) => [...prevRides, ...ridesData]);
            // alert(currentRides);
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

    const handleStartRide = (record: any) => {
        Modal.confirm({
            title: 'Start Ride',
            content: 'Are you sure you want to start this ride?',
            onOk: () => {
                // Call a function with the row key
                console.log('Starting ride:', record.key);
            },
            onCancel: () => {
                console.log('Start ride canceled');
            },
        });
    };

    const handleCompleteRide = (record: any) => {
        Modal.confirm({
            title: 'Complete Ride',
            content: 'Are you sure you want to complete this ride?',
            onOk: () => {
                // Call a function with the row key
                console.log('Completing ride:', record.key);
            },
            onCancel: () => {
                console.log('Complete ride canceled');
            },
        });
    };

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
        {
            title: 'Action',
            dataIndex: 'action',
            key: 'action',
            render: (_: any, record: any) => {
                if (record.status === 'Driver Connected') {
                    return (
                        <Button type="primary" onClick={() => handleStartRide(record)}>
                            Start Ride
                        </Button>
                    );
                } else if (record.status === 'In Ride') {
                    return (
                        <Button type="primary" onClick={() => handleCompleteRide(record)}>
                            Complete Ride
                        </Button>
                    );
                }
                return null;
            },
        },
    ];

    return (
        <div>
            {loading ? (
                <div>Driver Loading...</div>
            ) : currentRides.length > 0 ? (
                <Table dataSource={currentRides} columns={columns}/>
            ) : (
                <div>No driver rides currently.</div>
            )}
        </div>
    );
};

export default DriverRides;