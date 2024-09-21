// src/services/api.js
import { API_BASE_URL } from '@/constants';
import axios from 'axios';

const api = axios.create({
    baseURL: API_BASE_URL,
    timeout: 10000,
    headers: { 'Content-Type': 'application/json' },
});

export default api;