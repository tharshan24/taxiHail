import React from 'react';
import { Layout, Menu } from 'antd';
import { HomeOutlined, UserOutlined, LockOutlined, CarOutlined } from '@ant-design/icons';
import {Link} from "react-router-dom";

const { Sider } = Layout;

const AppSidebar: React.FC = () => {
    return (
        <Sider width={200} style={{ background: '#fff' }}>
            <Menu
                mode="vertical"
                defaultSelectedKeys={['1']}
                style={{ height: '100%', borderRight: 0 }}
            >
                <Menu.Item key="home" icon={<HomeOutlined />}>
                    <Link to="/dashboard/home">Home</Link>
                </Menu.Item>
                <Menu.Item key="profile" icon={<UserOutlined />}>
                    <Link to="/dashboard/profile">Profile</Link>
                </Menu.Item>
                <Menu.Item key="change-password" icon={<LockOutlined />}>
                    <Link to="/dashboard/update-password">Change Password</Link>
                </Menu.Item>
                { sessionStorage.getItem('role') === "DRIVER" ?
                    <Menu.Item key="manage-vehicle" icon={<CarOutlined />}>
                        <Link to="/dashboard/manage-vehicle">Manage Vehicle</Link>
                    </Menu.Item>
                    :
                    <Menu.Item key="Current Rides" icon={<CarOutlined />}>
                        <Link to="/dashboard/current-rides">Current Rides</Link>
                    </Menu.Item>
                }
            </Menu>
        </Sider>
    );
};

export default AppSidebar;
