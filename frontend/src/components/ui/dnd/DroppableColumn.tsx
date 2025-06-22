import { GridItem, VStack, Text, Separator } from "@chakra-ui/react";
import { useDroppable } from "@dnd-kit/core";

const DroppableColumn = ({ status, children, onDrop }) => {
  const { isOver, setNodeRef } = useDroppable({ id: status });

  return (
    <GridItem
      ref={setNodeRef}
      bg={isOver ? "gray.800" : "gray.contrast"}
      borderRadius="xl"
      p={4}
      minH="80vH"
    >
      <Text fontWeight="bold" fontSize="lg" mb={4}>
        {status}
      </Text>
      <Separator mb={2} />
      <VStack spacing={4}>{children}</VStack>
    </GridItem>
  );
};

export default DroppableColumn;
