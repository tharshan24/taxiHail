import {BrowserRouter, Route, Routes} from 'react-router-dom';

// Import your components for signup, sign-in, and dashboard
import SignUpPage from "../../pages/auth/SignUpPage";
import LoginPage from "../../pages/auth/LoginPage";
import DashboardPage from "../../pages/dashboard/DashboardPage";
import React, {useEffect, useState} from "react";
import PassengerHomePage from "../../pages/uiComponents/PassengerHomePage";
import ProfilePage from "../../pages/uiComponents/ProfilePage";
import DriverHomePage from "../../pages/uiComponents/DriverHomePage";
import UpdatePasswordPage from "../../pages/uiComponents/UpdatePasswordPage";
import RequireAuth from "./RequireAuth";
import Middle from "./Middle"
import RootRedirect from "./RootRedirect";
import LazyLogin from "./LazyLogin";
import ManageVehiclePage from "../../pages/uiComponents/ManageVehiclePage";
import CurrentRides from "../../pages/uiComponents/CurrentRidesPage";
import CurrentRidesPage from "../../pages/uiComponents/CurrentRidesPage";

export const AppRouter: React.FC = () => {

    const [role, setRole] = useState<string | null>(null);

    useEffect(() => {
        let storedRole = sessionStorage.getItem("role");
        setRole(storedRole);
    }, []);

    const protectedLayout = (
        <RequireAuth>
            <DashboardPage/>
        </RequireAuth>
    );

    const lazyLogin = (
        <LazyLogin>
            <Middle/>
        </LazyLogin>
    )

    return (
        <BrowserRouter>
            <Routes>
                <Route path="auth" element={lazyLogin}>
                    <Route path="login" element={<LoginPage/>}/>
                    <Route path="signup" element={<SignUpPage/>}/>
                </Route>
                <Route path="dashboard" element={protectedLayout}>
                    <Route
                        path="home"
                        element={role === "PASSENGER" ? <PassengerHomePage/> : <DriverHomePage/>}
                    />
                    <Route path="profile" element={<ProfilePage/>}/>
                    <Route path="update-password" element={<UpdatePasswordPage/>}/>
                    <Route
                        path="manage-vehicle"
                        element={role === "DRIVER" ? <ManageVehiclePage/> : <RootRedirect/>}
                    />
                    <Route
                        path="current-rides"
                        element={<CurrentRidesPage/>}
                    />
                </Route>
                <Route path="/*" element={<RootRedirect/>}/>
            </Routes>
        </BrowserRouter>
    );
};