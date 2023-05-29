import {
    Button, Col,
    Form, FormInstance,
    Input, Row,
    Select, Spin,
} from 'antd';
import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import SessionManager from "../auth/SessionManager";

const formItemLayout = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
    },
};

const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};

const ProfileForm: React.FC = () => {
    const [form] = Form.useForm();
    const formRef = React.useRef<FormInstance>(null);
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [userData, setUserData] = useState<any>(null);

    useEffect(() => {

        const checkAccess = async () => {
            try {
                const token = sessionStorage.getItem('accessToken');
                const response = await axios.get('http://localhost:8080/user/view_user', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                if (response.status === 200) {
                    setTimeout(() => {
                        const fetchedData = {
                            userId: response.data.userId,
                            username: response.data.userName,
                            role: response.data.role,
                            status: response.data.status,
                            lastLogin: response.data.lastLogin,
                            accountCreated: response.data.accountCreated,
                            firstName: response.data.firstName,
                            lastName: response.data.lastName,
                            email: response.data.email,
                            mobile: response.data.mobile,
                        };
                        setUserData(fetchedData);
                        setLoading(false);
                    }, 500);
                } else {
                    sessionStorage.clear();
                    alert(response.data)
                    console.log(response)
                    navigate('/auth/profile');
                }
            } catch (error) {
                // Request failed or unauthorized
                sessionStorage.clear();
                console.error(error);
            }
        };

        checkAccess();

    }, []);

    if (loading) {
        return <Spin size="large" />;
    }

    const onFinish = (values: any) => {
        const token = sessionStorage.getItem('accessToken');
        axios.post('http://localhost:8080/user/update_user', values, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => {
                // Handle the response from the server
                if(response.data.statusCodeValue === 200) {
                    alert(response.data.body);
                    // navigate('/dashboard/profile');
                }
                else {
                    sessionStorage.clear();
                    alert(response.data)
                    console.log(response)
                    // navigate('/dashboard/profile');
                }
            })
            .catch(error => {
                sessionStorage.clear();
                alert(error.response.data);
                console.log(error.response)
                navigate('/auth/login');
            });
    };

    const prefixSelector = (
        <Form.Item name="prefix" noStyle>
            <Select style={{ width: 70 }}>
                <span style={{ marginRight: '8px' }}>+94</span>
            </Select>
        </Form.Item>
    );

    const onReset = () => {
        formRef.current?.resetFields();
    };

    return (
        <Form
            {...formItemLayout}
            form={form}
            ref={formRef}
            name="update-profile"
            onFinish={onFinish}
            initialValues={{ ...userData ,prefix: '94' }}
            style={{ maxWidth: 600 }}
            scrollToFirstError
        >

            <div style={{textAlign:"left"}}>
                <Row gutter={32}>
                    <Col span={10}>
                        <Form.Item label="User ID">
                            <span>{userData.userId}</span>
                        </Form.Item>
                    </Col>
                    <Col span={14}>
                        <Form.Item label="Username">
                            <span>{userData.username}</span>
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={32}>
                    <Col span={10}>
                        <Form.Item label="Role">
                            <span>{userData.role}</span>
                        </Form.Item>
                    </Col>
                    <Col span={14}>
                        <Form.Item label="Status">
                            <span>{userData.status === 1 ? "Active" : "Inactive"}</span>
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={32}>
                    <Col span={10}>
                        <Form.Item label="Last Login">
                            <span>{userData.lastLogin}</span>
                        </Form.Item>
                    </Col>
                    <Col span={14}>
                        <Form.Item label="Account Created">
                            <span>{userData.accountCreated}</span>
                        </Form.Item>
                    </Col>
                </Row>
            </div>
            <Form.Item
                name="firstName"
                label="First Name"
                tooltip="Enter your given name"
                rules={[{ required: true, message: 'Please input your first name!' }]}
            >
                <Input />
            </Form.Item>

            <Form.Item
                name="lastName"
                label="Last Name"
                tooltip="Enter your sir name"
                rules={[{ required: false, message: 'Please input your last name!' }]}
            >
                <Input />
            </Form.Item>

            <Form.Item
                name="email"
                label="E-mail"
                rules={[
                    {
                        type: 'email',
                        message: 'The input is not valid E-mail!',
                    },
                    {
                        required: true,
                        message: 'Please input your E-mail!',
                    },
                ]}
            >
                <Input />
            </Form.Item>

            <Form.Item
                name="mobile"
                label="Mobile Number"
                rules={[
                    {
                        required: true,
                        message: 'Please input your phone number!'
                    },
                    {
                        len: 9,
                        message: 'Mobile number should be of length 9'
                    }
                ]}
            >
                <Input addonBefore={prefixSelector} style={{ width: '100%' }} />
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit" style={{ marginRight: '8px' }}>
                    Update
                </Button>
                <Button htmlType="button" onClick={onReset}>
                    Reset
                </Button>
            </Form.Item>

        </Form>
    );
};

export default ProfileForm;