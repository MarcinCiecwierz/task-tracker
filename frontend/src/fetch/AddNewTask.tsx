import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useAuth0 } from "@auth0/auth0-react";
import api from "@/axios/api";

export const AddNewTask = () => {
  const { getAccessTokenSilently } = useAuth0();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ title, description, usersIds }) => {
      console.log(title, description, usersIds);
      const token = await getAccessTokenSilently();
      const response = await api.post(
        "/task",
        { title, description, usersIds },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["tasksForLoggedUser"] });
    },
  });
};
