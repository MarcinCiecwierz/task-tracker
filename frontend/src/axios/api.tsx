import axios from "axios";
import { useAuth0 } from "@auth0/auth0-react";

// const { getAccessTokenSilently } = useAuth0();

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

// api.interceptors.request.use(async (config) => {
//   const token = await getAccessTokenSilently();
//   config.headers.Authorization = `Bearer ${token}`;
//   return config;
// });

export default api;
