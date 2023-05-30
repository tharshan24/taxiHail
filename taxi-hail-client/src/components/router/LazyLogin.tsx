import {WithChildrenProps} from "./generalTypes";
import { Navigate } from 'react-router-dom';
import React from "react";
const LazyLogin: React.FC<WithChildrenProps> = ({ children }) => {
    // const access = CheckAuth();
    // console.log("lazy: "+access);

    const access = sessionStorage.getItem('accessToken');
    return access ? <Navigate to="/dashboard/home" replace /> : <>{children}</>;
};

export default LazyLogin;