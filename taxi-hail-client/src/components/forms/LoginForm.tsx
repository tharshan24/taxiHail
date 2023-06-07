import {Button, Form, FormInstance, Input, message} from 'antd';
import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {useNavigate} from 'react-router-dom';
import axios from "axios";
import React from "react";
import SessionManager from "../auth/SessionManager";

export const LoginForm: React.FC = () => {

    const formRef = React.useRef<FormInstance>(null);
    const navigate = useNavigate();

    const tailLayout = {
        wrapperCol: {offset: 8, span: 16},
    };

    const onFinish = (values: any) => {
        axios
            .post('http://localhost:8080/auth/authenticate', values)
            .then(response => {
                if (response.data.status === 200) {
                    message.success(response.data.message);
                    return SessionManager(response.data); // Return the promise from SessionManager
                } else {
                    sessionStorage.clear();
                    message.error(response.data.message);
                }
            })
            .then(() => {
                console.log(sessionStorage.getItem("role"));
                navigate('/dashboard/home');
            })
            .catch(error => {
                sessionStorage.clear();
                alert(error);
            });
    };


    const onReset = () => {
        formRef.current?.resetFields();
    };

    const onFill = () => {
        navigate('/auth/signup');
    };

    return (
        <>
            <Form
                name="normal_login"
                ref={formRef}
                className="login-form"
                initialValues={{remember: true}}
                onFinish={onFinish}
            >
                <Form.Item
                    name="username"
                    rules={[{required: true, message: 'Please input your Username!'}]}
                >
                    <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Username"/>
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[{required: true, message: 'Please input your Password!'}]}
                >
                    <Input
                        prefix={<LockOutlined className="site-form-item-icon"/>}
                        type="password"
                        placeholder="Password"
                    />
                </Form.Item>

                <Form.Item {...tailLayout}>
                    <Button type="primary" htmlType="submit" className="login-form-button" style={{marginRight: '8px'}}>
                        Log in
                    </Button>
                    <Button htmlType="button" onClick={onReset}>
                        Reset
                    </Button>
                    <div style={{marginTop: '8px'}}>
                        <Button type="link" htmlType="button" onClick={onFill} style={{marginLeft: '8px'}}>
                            Go to SignUp
                        </Button>
                    </div>
                </Form.Item>
            </Form>
        </>
    );
}