import React from 'react';
import { Layout } from 'antd';

const { Footer } = Layout;

const AppFooter = () => {

    const footerStyle : React.CSSProperties = {
        backgroundColor: '#c6c6c6',
        padding: '20px',
        textAlign: 'center',
        fontSize: '14px',
        color: '#777',
    };

    return (
        <Footer style={footerStyle}>
            <div>
                ------------- | ------ TaxiHail ------ | -------------
            </div>
            <div>
                &copy; 2023 TaxiHail. All rights reserved.
            </div>
        </Footer>
    );
};

export default AppFooter;