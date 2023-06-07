import React, {useEffect, useState} from "react";
import axios from "axios";
import moment from "moment";

// Generate random latitude within the specified range
const generateRandomLatitude = () => {
    const minLatitude = 6.8962791;
    const maxLatitude = 6.9018405;
    return Math.random() * (maxLatitude - minLatitude) + minLatitude;
};

// Generate random longitude within the specified range
const generateRandomLongitude = () => {
    const minLongitude = 79.8527541;
    const maxLongitude = 79.8561938;
    return Math.random() * (maxLongitude - minLongitude) + minLongitude;
};

export const LocationUpdate = () => {
    const [vehicleId, setVehicleId] = useState(sessionStorage.getItem('vehicleId'))
    const [vehicleTypeId, setVehicleTypeId] = useState(sessionStorage.getItem('vehicleTypeId'))

    useEffect(() => {


        if ((!vehicleId || vehicleId === "undefined") && sessionStorage.getItem('role') === "DRIVER") {
            try {
                const token = sessionStorage.getItem('accessToken');
                axios.get('http://localhost:8080/vehicle/view_vehicle_by_user', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                    .then(response => {
                        sessionStorage.setItem("vehicleId", response.data.vehicleId)
                        sessionStorage.setItem("vehicleTypeId", response.data.vehicleType.vehicleTypeId)
                        setVehicleId(response.data.vehicleId);
                        setVehicleId(response.data.vehicleTypeId);
                        console.log('Request sent successfully');
                    })
                    .catch(error => {
                        // Handle error if needed
                        console.error('Error sending request:', error);
                    });
            } catch (error) {
                // Request failed or unauthorized
                sessionStorage.clear();
                console.error(error);
                alert(error);
            }
        }

        if (vehicleId && sessionStorage.getItem("vehicleId")) {
            const interval = setInterval(() => {
                const data = {
                    driver: sessionStorage.getItem("userId"),
                    vehicle: sessionStorage.getItem("vehicleId"),
                    vehicleType: sessionStorage.getItem("vehicleTypeId"),
                    location: `${generateRandomLatitude()},${generateRandomLongitude()}`,
                    inRide: sessionStorage.getItem("inRide"),
                    ride: sessionStorage.getItem("ride"),
                    status: 1,
                    createdAt: moment(Date.now()).format('YYYY-MM-DDTHH:mm:ss[Z]')
                };

                const token = sessionStorage.getItem('accessToken');
                axios.post('http://localhost:4000/location/updateLocation', data, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                    .then(response => {
                        // Handle response if needed
                        console.log('Request sent successfully');
                    })
                    .catch(error => {
                        // Handle error if needed
                        console.error('Error sending request:', error);
                    });
            }, 5000); // Set the interval duration (in milliseconds) as per your requirement

            return () => {
                clearInterval(interval); // Cleanup interval on component unmount
            };
        }
    }, []);

    return null;
}