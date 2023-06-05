import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios/index";
import {Button, message, Modal, Table} from "antd";

const DriverHomePage: React.FC = () => {

    const navigate = useNavigate();

    console.log(sessionStorage.getItem("role"))
    if (sessionStorage.getItem("role") !== "DRIVER") {
        navigate("/")
    }

    const [rideRequests, setRideRequests] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);

    const rideStatusLabels: any = {
        0: 'Cancelled',
        1: 'Pending',
        2: 'Driver Connected',
        3: 'In Ride',
        4: 'Completed',
    };

    const fetchRideRequests = async () => {
        // setLoading(true);
        try {
            const token = sessionStorage.getItem("accessToken");
            const response = await axios.get('http://localhost:8080/ride/ride_requests', {
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

            setRideRequests((prevRides: any) => [...prevRides, ...ridesData]);
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
        fetchRideRequests();
        const interval = setInterval(fetchRideRequests, 5000); // Fetch current rides every 5 seconds
        return () => clearInterval(interval);
    }, []);

    const handleAccept = (record: any) => {
        Modal.confirm({
            title: 'Accept Ride',
            content: 'Are you sure you want to accept this ride?',
            onOk: () => {
                // Call a function with the row key
                console.log('Accepting ride:', record.key);
            },
            onCancel: () => {
                console.log('Accept ride canceled');
            },
        });
    };

    const handleReject = (record: any) => {
        Modal.confirm({
            title: 'Reject Ride',
            content: 'Are you sure you want to reject this ride?',
            onOk: () => {
                // Call a function with the row key
                console.log('Rejecting ride:', record.key);
            },
            onCancel: () => {
                console.log('Reject ride canceled');
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
            key: 'action',
            render: (text: string, record: any) => (
                <div>
                    <Button onClick={() => handleAccept(record)}>Accept</Button>
                    <Button onClick={() => handleReject(record)}>Reject</Button>
                </div>
            ),
        },
    ];

    return (
        <div>
            {loading ? (
                <div>Driver Loading...</div>
            ) : rideRequests.length > 0 ? (
                <Table dataSource={rideRequests} columns={columns}/>
            ) : (
                <div>No ride requests currently.</div>
            )}
        </div>
    );

}

export default DriverHomePage;