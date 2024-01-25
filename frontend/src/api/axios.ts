import axios from 'axios';

const axiosClient = axios.create({
  baseURL: 'http://127.0.0.1:9888/api/trucks',
});

export default axiosClient;