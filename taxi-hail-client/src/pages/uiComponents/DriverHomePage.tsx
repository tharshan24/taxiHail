import React from "react";
import {useNavigate} from "react-router-dom";

const DriverHomePage: React.FC = () => {

    const navigate = useNavigate();

    console.log(sessionStorage.getItem("role"))
    if (sessionStorage.getItem("role") !== "DRIVER") {
        navigate("/")
    }

    return (
        <>
            Driver
        </>
    );
};

export default DriverHomePage;