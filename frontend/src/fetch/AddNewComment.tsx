import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useAuth0 } from "@auth0/auth0-react";
import api from "@/axios/api";

export const AddNewComment = (taskIdFromProps) => {
  const { getAccessTokenSilently } = useAuth0();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ text, taskId }) => {
      const token = await getAccessTokenSilently();
      const response = await api.post(
        "/comment",
        { text, taskId },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["task", taskIdFromProps] });
    },
  });
};
