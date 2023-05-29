import React from 'react';
import { Layout } from 'antd';

const { Footer: Footer } = Layout;

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
                Random Detail 1 | Random Detail 2 | Random Detail 3
            </div>
            <div>
                &copy; 2023 Your Company Name. All rights reserved.
            </div>
        </Footer>
    );
};

export default AppFooter;
