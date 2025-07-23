import axios from "axios";
import { useAuth0 } from "@auth0/auth0-react";

// const { getAccessTokenSilently } = useAuth0();
const apiUrl = import.meta.env.VITE_API_URL;

const api = axios.create({
  baseURL: apiUrl,
});

// api.interceptors.request.use(async (config) => {
//   const token = await getAccessTokenSilently();
//   config.headers.Authorization = `Bearer ${token}`;
//   return config;
// });

export default api;
