import {
  Box,
  Button,
  Flex,
  Heading,
  Input,
  Text,
  Timeline,
  VStack,
  Breadcrumb,
  HStack,
  Avatar,
} from "@chakra-ui/react";
import Header from "./Header";
import Sidebar from "./components/ui/Sidebar";
import { VscArrowSmallLeft } from "react-icons/vsc";
import { useNavigate, useSearchParams } from "react-router";
import { FetchTaskInfo } from "./fetch/FetchTaskInfo";
import { useState } from "react";
import { AddNewComment } from "./fetch/AddNewComment";

const Task = () => {
  const [searchParams] = useSearchParams();
  const taskid = searchParams.get("id");
  const navigate = useNavigate();
  const { data, isLoading } = FetchTaskInfo(taskid);
  const [comment, setComment] = useState("");
  const mutation = AddNewComment(taskid);

  const onSubmit = (text) => {
    const trimmed = text.trim();
    if (trimmed === "") return;
    mutation.mutate({ text: trimmed, taskId: taskid });
  };

  if (isLoading) return <p>Loading...</p>;

  console.log("task", data);

  return (
    <Box>
      <Header />
      <Flex>
        <Sidebar />
        <VStack align="start" flex="1">
          <HStack paddingTop="2" paddingLeft="2">
            <Button variant="ghost" onClick={() => navigate("/tracker")}>
              <VscArrowSmallLeft /> Back
            </Button>

            <Breadcrumb.Root>
              <Breadcrumb.List>
                <Breadcrumb.Item>
                  <Breadcrumb.Link>Tasks</Breadcrumb.Link>
                </Breadcrumb.Item>
                <Breadcrumb.Separator />
                <Breadcrumb.Item>
                  <Breadcrumb.Link>{data?.title}</Breadcrumb.Link>
                </Breadcrumb.Item>
              </Breadcrumb.List>
            </Breadcrumb.Root>
          </HStack>
          <Box p="5">
            <Heading size="2xl">{data?.title}</Heading>
          </Box>
          <Box p="5">
            <Heading size="2xl" mb={2}>
              Description:
            </Heading>
            <Text w="50%" textAlign="justify">
              {data?.description}
            </Text>
          </Box>
          <Box p="5">
            <Timeline.Root>
              {data?.comments.map((item) => (
                <Timeline.Item>
                  <Timeline.Connector>
                    <Timeline.Separator />
                    <Timeline.Indicator>
                      {item.author[0].toUpperCase()}
                    </Timeline.Indicator>
                  </Timeline.Connector>
                  <Timeline.Content>
                    <Timeline.Title>{item.author} commented</Timeline.Title>
                    <Timeline.Description>
                      {item.createdAt}
                    </Timeline.Description>
                    <Text textStyle="sm">{item.comment}</Text>
                  </Timeline.Content>
                </Timeline.Item>
              ))}

              <Timeline.Item>
                <Timeline.Connector>
                  <Timeline.Separator />
                  <Timeline.Indicator></Timeline.Indicator>
                </Timeline.Connector>
                <Timeline.Content gap="4" mt="-1" w="full">
                  <Input
                    size="sm"
                    placeholder="Add comment..."
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    onKeyDown={(e) => {
                      if (e.key === "Enter") {
                        e.preventDefault();

                        const trimmed = comment.trim();
                        if (!trimmed) return;

                        onSubmit(trimmed);

                        setComment("");
                      }
                    }}
                  />
                </Timeline.Content>
              </Timeline.Item>
            </Timeline.Root>
          </Box>
        </VStack>
        <Box p="10">
          {data?.users.map((user) => (
            <Box key={user} mb={2} display="flex" alignItems="center">
              <Avatar.Root>
                <Avatar.Fallback name={user} />
              </Avatar.Root>
              <Box
                ml={2}
                sx={{
                  overflow: "hidden",
                  textOverflow: "ellipsis",
                  whiteSpace: "nowrap",
                }}
              >
                {user}
              </Box>
            </Box>
          ))}
        </Box>
      </Flex>
    </Box>
  );
};

export default Task;
