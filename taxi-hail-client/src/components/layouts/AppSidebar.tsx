import React from 'react';
import {Layout, Menu} from 'antd';
import {HomeTwoTone, EditTwoTone, LockTwoTone, CarTwoTone, EyeTwoTone} from '@ant-design/icons';
import {Link} from "react-router-dom";

const {Sider} = Layout;

const AppSidebar: React.FC = () => {
    return (
        <Sider width={200} style={{background: '#fff'}}>
            <Menu
                mode="vertical"
                defaultSelectedKeys={['1']}
                style={{height: '100%', borderRight: 0}}
            >
                <Menu.Item key="home" icon={<HomeTwoTone/>}>
                    <Link to="/dashboard/home">Home</Link>
                </Menu.Item>
                <Menu.Item key="profile" icon={<EditTwoTone/>}>
                    <Link to="/dashboard/profile">Profile</Link>
                </Menu.Item>
                <Menu.Item key="change-password" icon={<LockTwoTone/>}>
                    <Link to="/dashboard/update-password">Change Password</Link>
                </Menu.Item>
                <Menu.Item key="Current Rides" icon={<EyeTwoTone/>}>
                    <Link to="/dashboard/current-rides">Current Rides</Link>
                </Menu.Item>
                {sessionStorage.getItem('role') === "DRIVER" ?
                    <Menu.Item key="manage-vehicle" icon={<CarTwoTone />}>
                        <Link to="/dashboard/manage-vehicle">Manage Vehicle</Link>
                    </Menu.Item>
                    :
                    null
                }
            </Menu>
        </Sider>
    );
};

export default AppSidebar;
