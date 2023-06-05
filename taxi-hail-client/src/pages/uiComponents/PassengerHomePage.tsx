import React, {useState, useEffect} from 'react';
import {Form, Select, Button, Spin, message} from 'antd';
import axios from 'axios';
import {Navigate, useNavigate} from "react-router-dom";

const {Option} = Select;

let pickupLocations = [
    {
        "latitude": 6.897547175256828,
        "longitude": 79.85352970947218
    },
    {
        "latitude": 6.9006237548655545,
        "longitude": 79.85431828154104
    },
    {
        "latitude": 6.901038408628013,
        "longitude": 79.8555960241876
    }
]

let destinationLocations = [
    {
        "latitude": 6.9012910120150766,
        "longitude": 79.85616477132682
    },
    {
        "latitude": 6.901047883144368,
        "longitude": 79.85337531765845
    },
    {
        "latitude": 6.900564889362727,
        "longitude": 79.85297139350564
    }
]

const PassengerHomePage: React.FC = () => {
    const [vehicleTypes, setVehicleTypes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [reqLoading, setReqLoading] = useState(false);
    const [form] = Form.useForm();
    const navigate = useNavigate();

    useEffect(() => {
        checkRide();
    })

    useEffect(() => {
        fetchVehicleTypes();
    }, []);

    useEffect(() => {
        const handlePageLeave = (event: BeforeUnloadEvent) => {
            if (reqLoading) {
                event.preventDefault();
                const confirmed = window.confirm('Are you sure you want to leave?');
                if (confirmed) {
                    handleCancelRequest();
                    message.info('Ride request cancelled.');
                    navigate('/'); // Replace with the appropriate path for navigation
                } else {
                    event.returnValue = '';
                }
            }
        };

        window.addEventListener('beforeunload', handlePageLeave);

        return () => {
            window.removeEventListener('beforeunload', handlePageLeave);
        };
    }, [reqLoading]);

    const checkRide = async () => {

    }

    const fetchVehicleTypes = async () => {
        try {
            console.log(sessionStorage.getItem("role"))
            if (sessionStorage.getItem("role") !== "PASSENGER") {
                navigate("/");
            }
            const token = sessionStorage.getItem('accessToken');
            const response = await axios.get('http://localhost:8080/vehicle_type/get_vehicle_types', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const data = response.data.body;
            setVehicleTypes(data);
            setLoading(false);
        } catch (error) {
            setLoading(false)
            alert(error);
            console.error(error);
        }
    };

    const handleRequestRide = async (values: any) => {

        setReqLoading(true);
        try {
            const response = await axios.post('http://localhost:8080/ride/request', values);
            if (response.data.statusCode == 200) {
                message.success('Ride requested successfully!');
                navigate("/dashboard/current-rides");
            } else {
                console.log(response.data)
                message.error('Failed to request ride. Please try again.');
                setReqLoading(false);
            }
            form.resetFields();
        } catch (error) {
            console.error(error);
            message.error('An error occurred. Please try again.');
            setReqLoading(false);
        }
    };

    const handleCancelRequest = () => {
        form.resetFields();
        message.info('Ride request cancelled.');
    };

    return (
        <div className="request-ride-container">
            <Form form={form} onFinish={handleRequestRide}>
                <Form.Item label="Vehicle Type" name="vehicleType"
                           rules={[{required: true, message: 'Please select a vehicle type'}]}>
                    <Select placeholder="Select vehicle type">
                        {vehicleTypes.map((vehicleType: any) => (
                            <Option key={vehicleType.vehicleTypeId} value={vehicleType.vehicleTypeId}>
                                {vehicleType.vehicleType + " -> \"" + vehicleType.description + "\""}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item label="Pickup Location" name="pickupLocation"
                           rules={[{required: true, message: 'Please select a pickup location'}]}>
                    <Select placeholder="Select pickup location">
                        {pickupLocations.map((pickupLocation: any) => (
                            <Option value={`${pickupLocation.latitude},${pickupLocation.longitude}`}>
                                {`${pickupLocation.latitude},${pickupLocation.longitude}`}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item label="Destination Location" name="destinationLocation"
                           rules={[{required: true, message: 'Please select a destination location'}]}>
                    <Select placeholder="Select destination location">
                        {destinationLocations.map((destinationLocation: any) => (
                            <Option value={`${destinationLocation.latitude},${destinationLocation.longitude}`}>
                                {`${destinationLocation.latitude},${destinationLocation.longitude}`}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item>
                    {reqLoading ? (
                        <div className="spin-container">
                            <Spin/>
                            <Button onClick={handleCancelRequest}>Cancel</Button>
                        </div>
                    ) : (
                        <Button type="primary" htmlType="submit">Request Ride</Button>
                    )}
                </Form.Item>
            </Form>
        </div>
    );
};

export default PassengerHomePage;
