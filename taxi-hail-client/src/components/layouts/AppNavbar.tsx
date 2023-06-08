import React, {useEffect, useState} from 'react';
import {Avatar, Dropdown, Menu, message} from 'antd';
import {UserOutlined, LogoutOutlined} from '@ant-design/icons';
import axios from "axios";
import {useNavigate} from "react-router-dom";

const AppNavbar: React.FC = () => {

    const navigate = useNavigate();
    const handleLogout = () => {
        const token = sessionStorage.getItem('accessToken');
        axios.get('http://localhost:8080/auth/logout', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => {
                // Handle the response from the server
                if (response.data.status === 200) {
                    sessionStorage.clear();
                    message.success(response.data.message);
                    navigate('/auth/login');
                } else {
                    sessionStorage.clear();
                    message.error(response.data.message)
                    setTimeout(() => {
                        navigate('/auth/login');
                    }, 2000)
                }
            })
            .catch(error => {
                sessionStorage.clear();
                message.error(error);
                setTimeout(() => {
                    navigate('/auth/login');
                }, 2000)
            });
    };

    const menu = (
        <Menu onClick={handleLogout}>
            <Menu.Item key="logout" style={{}}>
                <LogoutOutlined style={{color: '#000'}}/>
                Logout
            </Menu.Item>
        </Menu>
    );

    return (
        <div className="profile-avatar-container">
            <Dropdown overlay={menu} placement="bottomRight">
                <Avatar
                    size="large"
                    icon={<UserOutlined/>}
                    alt="Profile Image"
                />
            </Dropdown>
        </div>
    );
};

export default AppNavbar;
