import { useAuth0 } from "@auth0/auth0-react";
import {
  Avatar,
  Box,
  Button,
  Flex,
  Heading,
  Separator,
  Menu,
  Portal,
} from "@chakra-ui/react";
import { useEffect } from "react";
import { CiLogout } from "react-icons/ci";

const Header = () => {
  const {
    loginWithRedirect,
    logout,
    getAccessTokenSilently,
    isAuthenticated,
    user,
  } = useAuth0();

  useEffect(() => {
    const fetchToken = async () => {
      try {
        const token = await getAccessTokenSilently({
          detailedResponse: true,
        });
        console.log("JWT Token:", token);
      } catch (e) {
        console.error("❌ Błąd pobierania tokena:", e);
      }
    };

    if (isAuthenticated) {
      fetchToken();
    }
  }, [isAuthenticated, getAccessTokenSilently]);

  return (
    <Box>
      <Flex gap="4" justify="space-between">
        <Heading size="2xl" mt={3} ml={4}>
          Task Tracker
        </Heading>
        <Box my={3} mr={2}>
          {isAuthenticated ? (
            <Box>
              <Menu.Root>
                <Menu.Trigger>
                  <Avatar.Root>
                    <Avatar.Fallback name={user?.name} />
                  </Avatar.Root>
                </Menu.Trigger>
                <Portal>
                  <Menu.Positioner>
                    <Menu.Content>
                      <Menu.Item
                        value="logout"
                        onClick={() => logout()}
                        color="gray.400"
                      >
                        <CiLogout />
                        Logout
                      </Menu.Item>
                    </Menu.Content>
                  </Menu.Positioner>
                </Portal>
              </Menu.Root>
            </Box>
          ) : (
            <Box>
              <Button onClick={() => loginWithRedirect()} mr="2" rounded="full">
                Log in
              </Button>
              <Avatar.Root>
                <Avatar.Fallback />
              </Avatar.Root>
            </Box>
          )}
        </Box>
      </Flex>
      <Separator size="sm" />
    </Box>
  );
};

export default Header;
