import {BrowserRouter, Outlet, Route, Routes} from 'react-router-dom';

// Import your components for signup, sign-in, and dashboard
import SignUpPage from "../../pages/auth/SignUpPage";
import LoginPage from "../../pages/auth/LoginPage";
import DashboardPage from "../../pages/dashboard/DashboardPage";
import React from "react";
import PassengerHomePage from "../../pages/uiComponents/PassengerHomePage";
import ProfilePage from "../../pages/uiComponents/ProfilePage";
import DriverHomePage from "../../pages/uiComponents/DriverHomePage";
import UpdatePasswordPage from "../../pages/uiComponents/UpdatePasswordPage";
import RequireAuth from "./RequireAuth";
import Middle from "./Middle"
import RootRedirect from "./RootRedirect";
import LazyLogin from "./LazyLogin";

export const AppRouter: React.FC = () => {

    const getRole = () => {
        return sessionStorage.getItem("role");
    }

    const protectedLayout = (
        <RequireAuth>
            <DashboardPage />
        </RequireAuth>
    );

    const lazyLogin = (
        <LazyLogin>
            <Middle />
        </LazyLogin>
    )

    return (
         <BrowserRouter>
            <Routes>
                <Route path="auth" element={ lazyLogin } >
                    <Route path="login" element={<LoginPage />} />
                    <Route path="signup" element={<SignUpPage />} />
                </Route>
                <Route path="dashboard" element={ protectedLayout }>
                    <Route
                        path="home"
                        element={getRole() === "PASSENGER" ? <PassengerHomePage /> : <DriverHomePage />}
                    />
                    <Route path="profile" element={<ProfilePage />} />
                    <Route path="update-password" element={<UpdatePasswordPage />} />
                </Route>
                <Route path="/*" element={ <RootRedirect /> }/>
            </Routes>
         </BrowserRouter>
    );
};