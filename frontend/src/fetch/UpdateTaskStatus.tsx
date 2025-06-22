import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useAuth0 } from "@auth0/auth0-react";
import api from "@/axios/api";

export const UpdateTaskStatus = () => {
  const { getAccessTokenSilently } = useAuth0();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ status, taskId }) => {
      const token = await getAccessTokenSilently();
      const response = await api.put(
        "/task",
        { status, taskId },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["tasksForLoggedUser"],
      });
    },
  });
};
