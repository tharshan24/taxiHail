import React, {useEffect, useState} from "react";
import {Spin} from "antd";
import axios from "axios/index";
import moment from "moment/moment";
import {useNavigate} from "react-router-dom";

const ManageVehiclePage: React.FC = () => {

    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [vehicleData, setVehicleData] = useState<any>(null);

    useEffect(() => {

        const checkVehicle = async () => {
            try {
                const token = sessionStorage.getItem('accessToken');
                const response = await axios.get('http://localhost:8080/vehicle/view_vehicle_by_user', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                if (response.status === 200) {
                    setTimeout(() => {
                        const fetchedData = {

                        };
                        setVehicleData(fetchedData);
                        setLoading(false);
                    }, 500);
                } else {
                    sessionStorage.clear();
                    alert(response.data)
                    console.log(response)
                    navigate('/auth/profile');
                }
            } catch (error) {
                // Request failed or unauthorized
                sessionStorage.clear();
                console.error(error);
                alert(error);
                navigate('/auth/profile');
            }
        };

        checkVehicle();

    }, []);

    if (loading) {
        return <Spin size="large" />;
    }

    return (
        <></>
    );

};

export default ManageVehiclePage;