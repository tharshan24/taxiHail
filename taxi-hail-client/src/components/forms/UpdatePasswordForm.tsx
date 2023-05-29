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

const UpdatePasswordForm: React.FC = () => {
    const [form] = Form.useForm();
    const formRef = React.useRef<FormInstance>(null);
    const navigate = useNavigate();

    const onFinish = (values: any) => {
        const token = sessionStorage.getItem('accessToken');
        axios.post('http://localhost:8080/user/reset_password', values, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => {
                // Handle the response from the server
                if(response.data.statusCode === 200) {
                    alert(response.data.body);
                    navigate('/dashboard/update-password');
                }
                else {
                    // sessionStorage.clear();
                    alert(response.data)
                    console.log(response)
                }
            })
            .catch(error => {
                // sessionStorage.clear();
                alert(error.response);
                console.log(error.response)
            });
    };

    const onReset = () => {
        formRef.current?.resetFields();
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
                name="oldPassword"
                label="Current Password"
                rules={[
                    {
                        required: true,
                        message: 'Please input your password!',
                    }
                ]}
            >
                <Input.Password />
            </Form.Item>

            <Form.Item
                name="newPassword"
                label="New Password"
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
                name="newPasswordConfirm"
                label="Confirm New Password"
                dependencies={['newPassword']}
                hasFeedback
                rules={[
                    {
                        required: true,
                        message: 'Please confirm your password!',
                    },
                    ({ getFieldValue }) => ({
                        validator(_, value) {
                            if (!value || getFieldValue('newPassword') === value) {
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
                    Update
                </Button>
                <Button htmlType="button" onClick={onReset}>
                    Reset
                </Button>
            </Form.Item>

        </Form>
    );
};

export default UpdatePasswordForm;