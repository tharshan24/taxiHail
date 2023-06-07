// noinspection DuplicatedCode

import {Button, Form, FormInstance, Input, message, Select, Spin} from 'antd';
import {CarOutlined} from '@ant-design/icons';
import {useNavigate} from 'react-router-dom';
import axios from "axios";
import React, {useEffect, useState} from "react";


const {Option} = Select;
export const AddVehicleForm: React.FC = () => {

    const formRef = React.useRef<FormInstance>(null);
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [vehicleTypes, setVehicleTypes] = useState<any>(null);

    useEffect(() => {
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
            setLoading(false)
        } catch (error) {
            console.error(error);
        }
    };

    const tailLayout = {
        wrapperCol: {offset: 8, span: 16},
    };

    const onFinish = (values: any) => {
        values.vehicleType = {vehicleTypeId: values.vehicleType}
        const token = sessionStorage.getItem('accessToken');
        axios
            .post('http://localhost:8080/vehicle/add_vehicle', values, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then(response => {
                if (response.data.status === 200) {
                    message.success(response.data.message)
                    navigate("/dashboard/home")
                } else {
                    message.error(response.data.message);
                }
            })
            .catch(error => {
                // sessionStorage.clear();
                message.error(error.response.data.message);
            });
    };


    const onReset = () => {
        formRef.current?.resetFields();
    };

    if (loading) {
        return <Spin size="large"/>;
    }

    return (
        <>
            <Form
                name="add_vehicle"
                ref={formRef}
                onFinish={onFinish}
            >
                <Form.Item
                    label="Vehicle Type"
                    name="vehicleType"
                    rules={
                        [
                            {
                                required: true,
                                message: 'Please Select Vehicle Type!'
                            }
                        ]
                    }
                >
                    <Select placeholder="Select Vehicle Type">
                        {vehicleTypes.map((vehicleType: any) => (
                            <Option key={vehicleType.vehicleTypeId} value={vehicleType.vehicleTypeId}>
                                {vehicleType.vehicleType + " -> \"" + vehicleType.description + "\""}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Vehicle No"
                    name="vehicleNo"
                    rules={[{required: true, message: 'Please input your Vehicle No!'}]}
                >
                    <Input
                        prefix={<CarOutlined className="site-form-item-icon"/>}
                        placeholder="XXX-YYYY"
                    />
                </Form.Item>

                <Form.Item {...tailLayout}>
                    <Button type="primary" htmlType="submit" className="login-form-button" style={{marginRight: '8px'}}>
                        Add
                    </Button>
                    <Button htmlType="button" onClick={onReset}>
                        Reset
                    </Button>
                </Form.Item>
            </Form>
        </>
    );
}