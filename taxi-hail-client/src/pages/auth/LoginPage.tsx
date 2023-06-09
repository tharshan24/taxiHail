import React from 'react';
import {Col, Row} from 'antd';
import {LoginForm} from "../../components/forms/LoginForm";

const LoginPage: React.FC = () => {


    return (
        <Row
            justify="center"
            align="middle"
            style={{
                minHeight: '100vh',
                backgroundColor: '#f5f5f5', // Set a light background color
            }}
        >

            <Col xs={20} sm={16} md={12} lg={8} xl={6}>
                <div
                    style={{
                        background: '#fff',
                        padding: '24px',
                        borderRadius: '4px',
                        boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)', // Add a subtle shadow
                    }}
                >
                    <h1 style={{textAlign: 'center', marginBottom: '24px', color: '#1890ff'}}>Login</h1>
                    <LoginForm/>
                </div>
            </Col>
        </Row>
    );
};

export default LoginPage;