import {Navigate} from 'react-router-dom';
import React from "react";

const RootRedirect: React.FC = () => {

    // const access = CheckAuth();
    const access = sessionStorage.getItem('accessToken');
    return access ? <Navigate to="/dashboard/home" replace/> : <Navigate to="/auth/login" replace/>;
};

export default RootRedirect;