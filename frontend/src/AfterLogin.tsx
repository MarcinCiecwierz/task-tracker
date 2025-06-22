import { Box, Flex, Grid } from "@chakra-ui/react";
import Header from "./Header";
import Sidebar from "./components/ui/Sidebar";
import { useEffect, useState } from "react";
import DraggableTask from "./components/ui/dnd/DraggableTask";
import DroppableColumn from "./components/ui/dnd/DroppableColumn";

import { DndContext, closestCenter } from "@dnd-kit/core";
import { FetchAllTasksForLogged } from "./fetch/FetchAllTasksForLogged";
import { UpdateTaskStatus } from "./fetch/UpdateTaskStatus";

const statuses = ["BackLog", "To-do", "Doing", "Done", "Tested"];

const afterLogin = () => {
  const { data, isLoading } = FetchAllTasksForLogged();

  const [tasks, setTasks] = useState([]);
  const mutation = UpdateTaskStatus();

  useEffect(() => {
    if (data) {
      const formatted = data.map((task) => ({
        id: task.id,
        title: task.title,
        status: task.status,
      }));
      setTasks(formatted);
    }
  }, [data]);

  const handleDragEnd = (event) => {
    const { active, over } = event;

    if (over && active.id !== over.id) {
      const taskIndex = tasks.findIndex((t) => t.id === active.id);
      if (taskIndex > -1) {
        const updated = [...tasks];
        const updatedTask = {
          ...updated[taskIndex],
          status: over.id,
        };

        updated[taskIndex] = updatedTask;
        setTasks(updated);

        mutation.mutate({ taskId: updatedTask.id, status: updatedTask.status });
      }
    }
  };

  return (
    <Box>
      <Header />
      <Flex>
        <Sidebar />
        <Box flex="1" p="10">
          <DndContext
            collisionDetection={closestCenter}
            onDragEnd={handleDragEnd}
          >
            <Grid templateColumns="repeat(5, 1fr)" gap={4}>
              {statuses.map((status) => (
                <DroppableColumn key={status} status={status}>
                  {tasks
                    .filter((task) => task.status === status)
                    .map((task) => (
                      <DraggableTask key={task.id} task={task} />
                    ))}
                </DroppableColumn>
              ))}
            </Grid>
          </DndContext>
        </Box>
      </Flex>
    </Box>
  );
};

export default afterLogin;
