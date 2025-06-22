import api from "@/axios/api";
import { useAuth0 } from "@auth0/auth0-react";
import { useQuery } from "@tanstack/react-query";

const fetchForAllTasks = async (getAccessTokenSilently) => {
  const token = await getAccessTokenSilently();
  const response = await api.get("/task/list", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

export const FetchAllTasksForLogged = () => {
  const { getAccessTokenSilently, isAuthenticated } = useAuth0();
  return useQuery({
    queryKey: ["tasksForLoggedUser"],
    queryFn: () => fetchForAllTasks(getAccessTokenSilently),
    enabled: isAuthenticated,
  });
};
