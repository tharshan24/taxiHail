import { Navigate } from 'react-router-dom';
import React from "react";
import CheckAuth from "../auth/CheckAuth";

const RootRedirect : React.FC = () => {

    const access = CheckAuth();

    return access ? <Navigate to="/dashboard/home" replace /> : <Navigate to="/auth/login" replace />;
};

export default RootRedirect;