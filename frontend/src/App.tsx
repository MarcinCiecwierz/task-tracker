import { Box, Center, Flex } from "@chakra-ui/react";
import Header from "./Header";
import Sidebar from "./components/ui/Sidebar";

const App = () => {
  return (
    <Box>
      <Header />
      <Flex>
        <Sidebar />
        <Box flex={"1"} p="4">
          <Center>
            Welcome to the task Tracker app. Here you can track team's tasks.
          </Center>
        </Box>
      </Flex>
    </Box>
  );
};

export default App;
