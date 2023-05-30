import {WithChildrenProps} from "./generalTypes";
import { Navigate } from 'react-router-dom';
import React from "react";
const RequireAuth: React.FC<WithChildrenProps> = ({ children }) => {
    // const access = CheckAuth();
    // console.log("req: "+access);
    const access = sessionStorage.getItem('accessToken');
    return access ? <>{children}</> : <Navigate to="/auth/login" replace />;

};

export default RequireAuth;