import React, { useState, useEffect } from 'react';
import { Form, Select, Button, Spin, message } from 'antd';
import axios from 'axios';

const { Option } = Select

const PassengerHomePage: React.FC = () => {

    const [vehicleTypes, setVehicleTypes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [form] = Form.useForm();

    useEffect(() => {
        // Fetch vehicle types from the backend
        fetchVehicleTypes();
    }, []);

    const fetchVehicleTypes = async () => {
        try {
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
            console.error(error);
        }
    };

    const handleRequestRide = async (values: any) => {
        setLoading(true);
        try {
            // Make API request to backend for ride request
            const response = await axios.post('http://localhost:8080/rides/request', values);
            if (response.data.success) {
                // Navigate to current rides page
                // Replace the path below with the appropriate path for your current rides page
                // navigate('/current-rides');
                message.success('Ride requested successfully!');
            } else {
                message.error('Failed to request ride. Please try again.');
            }
            form.resetFields();
        } catch (error) {
            console.error(error);
            message.error('An error occurred. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    const handleCancelRequest = () => {
        // Logic to cancel the ride request
        // Implement the necessary logic to cancel the request or stop ongoing processes
        form.resetFields();
        message.info('Ride request cancelled.');
    };

    return (
        <div className="request-ride-container">
            <Form form={form} onFinish={handleRequestRide}>
                <Form.Item label="Vehicle Type" name="vehicleType" rules={[{ required: true, message: 'Please select a vehicle type' }]}>
                    <Select placeholder="Select vehicle type">
                        {vehicleTypes.map((vehicleType:any) => (
                            <Option key={vehicleType.vehicleTypeId} value={vehicleType.vehicleTypeId}>
                                {vehicleType.vehicleType + " -> \"" + vehicleType.description + "\""}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item label="Pickup Location" name="pickupLocation" rules={[{ required: true, message: 'Please select a pickup location' }]}>
                    <Select placeholder="Select pickup location">
                        {/* Generate and map random pickup locations */}
                    </Select>
                </Form.Item>
                <Form.Item label="Destination Location" name="destinationLocation" rules={[{ required: true, message: 'Please select a destination location' }]}>
                    <Select placeholder="Select destination location">
                        {/* Generate and map random destination locations */}
                    </Select>
                </Form.Item>
                <Form.Item>
                    {loading ? (
                        <div className="spin-container">
                            <Spin />
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