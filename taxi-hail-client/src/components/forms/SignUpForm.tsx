import {
    Button,
    Form, FormInstance,
    Input,
    Select,
} from 'antd';
import {useNavigate} from "react-router-dom";
import React from "react";
import axios from "axios";
import SessionManager from "../auth/SessionManager";

const { Option } = Select;

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

const strongPasswordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

const SignUpForm: React.FC = () => {
    const [form] = Form.useForm();
    const formRef = React.useRef<FormInstance>(null);
    const navigate = useNavigate();

    const onFinish = (values: any) => {

        axios.post('http://localhost:8080/auth/register', values)
            .then(response => {
                if (response.data.status === 200) {
                    return SessionManager(response.data); // Return the promise from SessionManager
                } else {
                    sessionStorage.clear();
                    throw new Error(response.data.message);
                }
            })
            .then(() => {
                navigate('/dashboard/home');
            })
            .catch(error => {
                sessionStorage.clear();
                alert(error);
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

    const onFill = () => {
        navigate('/auth/login');
    };

    const validateStrongPassword = (_: any, value: string) => {
        if (!value || strongPasswordPattern.test(value)) {
            return Promise.resolve();
        }
        return Promise.reject(new Error('Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.'));
    };

    return (
        <Form
            {...formItemLayout}
            form={form}
            ref={formRef}
            name="register"
            onFinish={onFinish}
            initialValues={{ prefix: '94' }}
            style={{ maxWidth: 600 }}
            scrollToFirstError
        >

            <Form.Item
                name="role"
                label="Role"
                rules={[{ required: true, message: 'Please select a role!' }]}
            >
                <Select placeholder="select your gender">
                    <Option value="PASSENGER">Passenger</Option>
                    <Option value="DRIVER">Driver</Option>
                </Select>
            </Form.Item>

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
                name="username"
                label="Username"
                tooltip="What do you want others to call you?"
                rules={[{ required: true, message: 'Please input your username!' }]}
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

            <Form.Item
                name="password"
                label="Password"
                rules={[
                    {
                        required: true,
                        message: 'Please input your password!',
                    },
                    {
                        validator: validateStrongPassword
                    }
                ]}
                hasFeedback
            >
                <Input.Password />
            </Form.Item>

            <Form.Item
                name="passwordConfirm"
                label="Confirm Password"
                dependencies={['password']}
                hasFeedback
                rules={[
                    {
                        required: true,
                        message: 'Please confirm your password!',
                    },
                    ({ getFieldValue }) => ({
                        validator(_, value) {
                            if (!value || getFieldValue('password') === value) {
                                return Promise.resolve();
                            }
                            return Promise.reject(new Error('The two passwords that you entered do not match!'));
                        },
                    }),
                ]}
            >
                <Input.Password />
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit" style={{ marginRight: '8px' }}>
                    Register
                </Button>
                <Button htmlType="button" onClick={onReset}>
                    Reset
                </Button>
                <div style={{marginTop: '8px'}}>
                    <Button type="link" htmlType="button" onClick={onFill} style={{marginLeft: '8px'}}>
                        Go to SignIn
                    </Button>
                </div>
            </Form.Item>

        </Form>
    );
};

export default SignUpForm;