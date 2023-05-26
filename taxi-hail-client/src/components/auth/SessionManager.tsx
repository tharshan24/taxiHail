import React from "react";

const SessionManager = async (data: { access_token: string; refresh_token: string; user_id: string; username: string; role: string; }) => {

    await sessionStorage.setItem('accessToken', data.access_token);
    await sessionStorage.setItem('refreshToken', data.refresh_token);
    await sessionStorage.setItem('userId', data.user_id);
    await sessionStorage.setItem('username', data.username);
    await sessionStorage.setItem('role', data.role);
    return;
};

export default SessionManager;