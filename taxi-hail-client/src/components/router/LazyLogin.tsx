import {WithChildrenProps} from "./generalTypes";
import { Navigate } from 'react-router-dom';
import CheckAuth from "./CheckAuth";
import React from "react";
const LazyLogin: React.FC<WithChildrenProps> = ({ children }) => {
    const access = CheckAuth();
    return !access ? <>{children}</> : <Navigate to="/dashboard/home" replace />;
};

export default LazyLogin;