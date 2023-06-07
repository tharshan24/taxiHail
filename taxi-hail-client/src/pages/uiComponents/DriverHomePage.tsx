import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {Button, message, Modal, Table} from "antd";
import {UUID} from "crypto";

const DriverHomePage: React.FC = () => {

    const navigate = useNavigate();

    console.log(sessionStorage.getItem("role"))
    if (sessionStorage.getItem("role") !== "DRIVER") {
        // navigate("/");
        window.location.href = window.location.origin + "/";
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

    useEffect( () => {
        checkRide();
    },[])

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
                ride.status = rideStatusLabels[ride.status] || 'Unknown';
                ride.pickupLocation = ride.pickupLocationLatitude + " , " + ride.pickupLocationLongitude;
                ride.destinationLocation = ride.destinationLocationLatitude + " , " + ride.destinationLocationLongitude;
                ride.amount = ride.amount === null ? "TBD" : "Rs. " + ride.amount;
            });

            setRideRequests((prevRides: any) => ridesData);
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

    const checkRide = async () => {
        try {
            const token = sessionStorage.getItem('accessToken');
            const response = await axios.get('http://localhost:8080/ride/check_ride', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const data = response.data;
            if (data.status === 1) {
                message.warning(data.message);
                console.log(data.message)
                sessionStorage.setItem("inRide",'1');
                navigate("/dashboard/current-rides");
            }
            else {
                sessionStorage.setItem("inRide",'0');
                // message.info(data.message);
                console.log(data.message)
            }
            setLoading(false);
        } catch (error) {
            setLoading(false);
            alert(error);
            console.error(error);
        }
    }

    const acceptRide = async (rideId: UUID) => {
        try {
            const token = sessionStorage.getItem('accessToken');
            const response = await axios.get(`http://localhost:8080/ride/accept_ride/${rideId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const data = response.data;
            if (data.status === 200) {
                message.success('Ride Accepted');
                await sessionStorage.setItem("inRide", "1");
                await sessionStorage.setItem("ride", rideId.toString());
                console.log(sessionStorage.getItem("ride"))
                navigate("/dashboard/current-rides");
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

    const handleAccept = (record: any) => {
        Modal.confirm({
            title: 'Accept Ride',
            content: 'Are you sure you want to accept this ride?',
            onOk: async () => {
                await acceptRide(record.rideId)
                console.log('Accepting ride:', record.key);
            },
            onCancel: () => {
                console.log('Accept ride canceled');
            },
        });
    };

    const rejectRide = async (rideId: UUID) => {
        try {
            const token = sessionStorage.getItem('accessToken');
            const response = await axios.get(`http://localhost:8080/ride/reject_ride/${rideId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const data = response.data;
            if (data.status === 200) {
                message.success('Ride Rejected');
                fetchRideRequests();
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

    const handleReject = (record: any) => {
        Modal.confirm({
            title: 'Reject Ride',
            content: 'Are you sure you want to reject this ride?',
            onOk: async () => {
                await rejectRide(record.rideId);
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