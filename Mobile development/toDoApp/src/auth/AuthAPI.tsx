import axios from 'axios';
import { baseURL, config, withLogs } from '../core';

const authURL = `http://${baseURL}/api/auth/login`;

export interface AuthProps {
    token: string;
}

export const login: (username?: string, password?: string) => Promise<AuthProps> = (username, password) => {
    return withLogs(axios.post(authURL, { username, password }, config), 'login');
}
