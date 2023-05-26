import React, { useEffect, useState } from 'react';
import axios from 'axios';

const CheckAuth = ( ) => {
    const [access, setAccess] = useState(false);

    useEffect(() => {
        const checkAccess = async () => {
            const token = sessionStorage.getItem('accessToken');
            if (token) {
                try {
                    const response = await axios.get('http://localhost:8080/user/test', {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });
                    if (response.status === 200) {
                        setAccess(true);
                    }
                } catch (error) {
                    // Request failed or unauthorized
                    sessionStorage.clear();
                    console.error(error);
                }
            } else {
                // Token not present
                sessionStorage.clear();
                console.log('Token not found');
            }
        };

        checkAccess();
    }, []);

    return access? true : false;
};

export default CheckAuth;