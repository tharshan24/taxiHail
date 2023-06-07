import React, {useEffect, useState} from 'react';
import {Button, message, Modal, Space, Table} from 'antd';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import {UUID} from "crypto";

const PassengerRides = () => {

    const navigate = useNavigate();

    console.log(sessionStorage.getItem("role"))
    if (sessionStorage.getItem("role") !== "PASSENGER") {
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
                ride.status = rideStatusLabels[ride.status] || 'Unknown';
                ride.pickupLocation = ride.pickupLocationLatitude + " , " + ride.pickupLocationLongitude;
                ride.destinationLocation = ride.destinationLocationLatitude + " , " + ride.destinationLocationLongitude;
                ride.amount = ride.amount === null ? "TBD" : "Rs. " + ride.amount;
                ride.driver = ride.driver === null ? "Waiting for Driver" : ride.driver;
                ride.vehicleType = ride.vehicleType === null ? "Waiting for Driver" : ride.vehicleType;
                ride.vehicleNo = ride.vehicleNo === null ? "Waiting for Driver" : ride.vehicleNo;
            });

            setCurrentRides((prevRides: any) => ridesData);
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

    const cancelRequest = async (rideId: UUID) => {
        try {
            const token = sessionStorage.getItem('accessToken');
            const response = await axios.get(`http://localhost:8080/ride/cancel_ride/${rideId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const data = response.data;
            if (data.status === 200) {
                message.success('Ride request cancelled.');
                fetchCurrentRides();
            }
            else {
                message.error(data.message);
            }
            setLoading(false);
        } catch (error) {
            setLoading(false);
            alert(error);
            console.error(error);
        }
    };

    const handleCancelRequest = (record: any) => {
        Modal.confirm({
            title: 'Cancel Ride',
            content: 'Are you sure you want to cancel this ride?',
            onOk: async () => {
                await cancelRequest(record.rideId)
                console.log('Cancelling ride:', record.key);
            },
            onCancel: () => {
                console.log('Ride not cancelled');
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
            title: 'Ride Type',
            dataIndex: 'vehicleType',
            key: 'vehicleType',
        },
        {
            title: 'Vehicle Number',
            dataIndex: 'vehicleNo',
            key: 'vehicleNo',
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
                if(record.status === "Pending" || record.status === "Driver Connected") {
                    return (
                        <Button type="primary" onClick={() => handleCancelRequest(record)}>
                            Cancel Ride
                        </Button>
                    );
                }
                else {
                    return (
                        <Space>
                            No Action
                        </Space>
                    );
                }
            },
        },
    ];

    return (
        <div>
            {loading ? (
                <div>Loading...</div>
            ) : currentRides.length > 0 ? (
                <Table dataSource={currentRides} columns={columns}/>
            ) : (
                <div>No rides currently.</div>
            )}
        </div>
    );
};

export default PassengerRides;
