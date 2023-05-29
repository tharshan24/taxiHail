import React from 'react';
import { Avatar, Dropdown, Menu } from 'antd';
import { UserOutlined, LogoutOutlined } from '@ant-design/icons';
import axios from "axios";
import SessionManager from "../auth/SessionManager";
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
                if(response.data.status === 200) {
                    sessionStorage.clear();
                    alert(response.data.message);
                    navigate('/auth/login');
                }
                else {
                    sessionStorage.clear();
                    alert(response.data.message)
                    setTimeout(() => {
                        navigate('/auth/login');
                    },2000)
                }
            })
            .catch(error => {
                sessionStorage.clear();
                alert(error);
                setTimeout(() => {
                    navigate('/auth/login');
                },2000)
            });
    };

    const menu = (
        <Menu onClick={handleLogout}>
            <Menu.Item key="logout" style={{}}>
                <LogoutOutlined style={{ color: '#000' }}/>
                Logout
            </Menu.Item>
        </Menu>
    );

    return (
        <div className="profile-avatar-container">
            <Dropdown overlay={menu} placement="bottomRight" >
                <Avatar
                    size="large"
                    icon={<UserOutlined />}
                    alt="Profile Image"
                />
            </Dropdown>
        </div>
    );
};

export default AppNavbar;
