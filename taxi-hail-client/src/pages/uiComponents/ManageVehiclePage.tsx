import React, {useEffect, useState} from "react";
import {Spin} from "antd";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {AddVehicleForm} from "../../components/forms/AddVehicleForm";
import {UpdateVehicleForm} from "../../components/forms/UpdateVehicleForm";

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
                            vehicleId: response.data.vehicleId,
                            vehicleType: response.data.vehicleType,
                            vehicleNo: response.data.vehicleNo,
                            createdAt: response.data.createdAt
                        };
                        setVehicleData(fetchedData);
                        setLoading(false);
                    }, 500);
                } else if (response.status == 204) {
                    // alert(response)
                    console.log(response)
                    setLoading(false);
                }
            } catch (error) {
                // Request failed or unauthorized
                sessionStorage.clear();
                console.error(error);
                alert(error);
                navigate('/dashboard/home');
            }
        };

        checkVehicle();

    }, []);

    if (loading) {
        return <Spin size="large" />;
    }

    if (vehicleData !== null) {
        return (
            <UpdateVehicleForm vehicleData={vehicleData}/>
        );
    } else {
        return (
            <AddVehicleForm vehicleData={vehicleData} />
        );
    }

};

export default ManageVehiclePage;