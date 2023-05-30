const SessionManager = (data: { access_token: string; refresh_token: string; user_id: string; username: string; role: string; }) => {

    sessionStorage.setItem('accessToken', data.access_token);
    sessionStorage.setItem('refreshToken', data.refresh_token);
    sessionStorage.setItem('userId', data.user_id);
    sessionStorage.setItem('username', data.username);
    sessionStorage.setItem('role', data.role);
    return;
};

export default SessionManager;