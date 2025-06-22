import api from "@/axios/api";
import { useAuth0 } from "@auth0/auth0-react";
import { useQuery } from "@tanstack/react-query";

const fetchForTask = async (getAccessTokenSilently, taskid) => {
  const token = await getAccessTokenSilently();
  const response = await api.get(`/task?id=${taskid}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

export const FetchTaskInfo = (taskid) => {
  const { getAccessTokenSilently, isAuthenticated } = useAuth0();
  console.log(taskid);
  return useQuery({
    queryKey: ["task", taskid],
    queryFn: () => fetchForTask(getAccessTokenSilently, taskid),
    enabled: isAuthenticated && !!taskid,
  });
};
