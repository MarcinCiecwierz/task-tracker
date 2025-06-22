import { Box, Button, Flex, VStack, Text } from "@chakra-ui/react";
import { useDraggable } from "@dnd-kit/core";
import { IoPeopleSharp } from "react-icons/io5";
import { VscArrowRight } from "react-icons/vsc";
import { useNavigate } from "react-router";

const DraggableTask = ({ task }) => {
  const { attributes, listeners, setNodeRef, transform } = useDraggable({
    id: task.id,
  });

  const style = {
    transform: transform
      ? `translate(${transform.x}px, ${transform.y}px)`
      : undefined,
  };

  const navigate = useNavigate();

  return (
    <Box
      ref={setNodeRef}
      style={style}
      {...listeners}
      {...attributes}
      bg="gray.900"
      px={4}
      py={2}
      borderRadius="md"
      boxShadow="md"
      w="full"
    >
      <VStack align="start">
        <Text>Title: {task.title}</Text>
        <Flex justify="space-between" w="full" align="center">
          <Flex gap={2} align="center">
            <IoPeopleSharp />
            <Text fontSize="sm">10</Text>
          </Flex>
          <Button
            size="sm"
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => navigate(`/task?id=${task.id}`)}
          >
            <VscArrowRight />
          </Button>
        </Flex>
      </VStack>
    </Box>
  );
};

export default DraggableTask;
