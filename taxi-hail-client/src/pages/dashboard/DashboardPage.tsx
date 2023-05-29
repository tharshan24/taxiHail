import React from "react";
import { Outlet } from "react-router-dom";
import { Layout } from 'antd';
import AppNavbar from "../../components/layouts/AppNavbar";
import AppSidebar from "../../components/layouts/AppSidebar";
import AppFooter from "../../components/layouts/AppFooter";
import {Content, Header} from "antd/lib/layout/layout";

const DashboardPage: React.FC = () => {

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Header>
                <AppNavbar />
            </Header>
            <Layout>
                <AppSidebar />
                <Layout>
                    <Content style={{ margin: '24px 16px' }}>
                        <Outlet />
                    </Content>
                    <AppFooter />
                </Layout>
            </Layout>
        </Layout>
    );
};

export default DashboardPage;