import api from "@/axios/api";
import { useAuth0 } from "@auth0/auth0-react";
import { useQuery } from "@tanstack/react-query";

const fetchMembers = async (getAccessTokenSilently) => {
  const token = await getAccessTokenSilently();
  const response = await api.get("/user/list", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

export const FetchAllMembers = () => {
  const { getAccessTokenSilently, isAuthenticated } = useAuth0();
  return useQuery({
    queryKey: ["allMembers"],
    queryFn: () => fetchMembers(getAccessTokenSilently),
    enabled: isAuthenticated,
  });
};
