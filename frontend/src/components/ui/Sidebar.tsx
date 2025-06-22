import {
  Box,
  Button,
  Flex,
  Icon,
  Separator,
  Stack,
  Drawer,
  Portal,
  CloseButton,
  Field,
  Input,
  Select,
  createListCollection,
} from "@chakra-ui/react";
import { MdPerson } from "react-icons/md";
import { IoMdAdd } from "react-icons/io";
import { GoTasklist } from "react-icons/go";
import { useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { FetchAllMembers } from "@/fetch/FetchAllMembers";
import { useNavigate } from "react-router";
import { AddNewTask } from "@/fetch/AddNewTask";

interface FormValues {
  title: string;
  description: string;
  person: string[];
}

const Sidebar = () => {
  const mutation = AddNewTask();
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm<FormValues>({
    defaultValues: {
      person: [],
    },
  });

  const onSubmit = handleSubmit((data) => {
    // console.log(data);
    mutation.mutate({
      title: data.title,
      description: data.description,
      usersIds: data.person,
    });
  });

  const { data, isLoading } = FetchAllMembers();

  const members = createListCollection({
    items:
      data?.map((member) => ({
        label: member.name,
        value: member.id,
        role: member.role,
      })) || [],
  });
  return (
    <Flex width="146px" pos="sticky" height="100vh">
      <Box ml={2}>
        <Stack spaceY={1}>
          <Drawer.Root open={open} onOpenChange={(e) => setOpen(e.open)}>
            <Drawer.Trigger asChild>
              <Button variant="surface" mt={4} size="sm">
                <Icon>
                  <IoMdAdd />
                </Icon>
              </Button>
            </Drawer.Trigger>
            <Portal>
              <Drawer.Backdrop />
              <form onSubmit={onSubmit}>
                <Drawer.Positioner>
                  <Drawer.Content>
                    <Drawer.Header>
                      <Drawer.Title>Create a new task</Drawer.Title>
                    </Drawer.Header>
                    <Drawer.Body>
                      <Stack gap="4" align="flex-start" maxW="sw">
                        <Field.Root invalid={!!errors.title}>
                          <Field.Label>Title</Field.Label>
                          <Input {...register("title")} />
                          <Field.ErrorText>
                            {errors.title?.message}
                          </Field.ErrorText>
                        </Field.Root>

                        <Field.Root invalid={!!errors.description}>
                          <Field.Label>Description</Field.Label>
                          <Input {...register("description")} />
                          <Field.ErrorText>
                            {errors.description?.message}
                          </Field.ErrorText>
                        </Field.Root>

                        <Controller
                          name="person"
                          control={control}
                          render={({ field }) => (
                            <Select.Root
                              multiple
                              collection={members}
                              size="sm"
                              maxW="sw"
                              onValueChange={({ value }) => {
                                field.onChange(Array.from(value)); // Convert Set to string[]
                              }}
                            >
                              <Select.HiddenSelect />
                              <Select.Label>Select users</Select.Label>
                              <Select.Control>
                                <Select.Trigger>
                                  <Select.ValueText placeholder="Select users" />
                                </Select.Trigger>
                                <Select.IndicatorGroup>
                                  <Select.Indicator />
                                </Select.IndicatorGroup>
                              </Select.Control>
                              <Select.Positioner>
                                <Select.Content>
                                  {members?.items.map((item) => (
                                    <Select.Item item={item} key={item.value}>
                                      {item.label} {item.role}
                                      <Select.ItemIndicator />
                                    </Select.Item>
                                  ))}
                                </Select.Content>
                              </Select.Positioner>
                            </Select.Root>
                          )}
                        />
                      </Stack>
                    </Drawer.Body>
                    <Drawer.Footer>
                      <Button variant="outline" onClick={() => setOpen(false)}>
                        Cancel
                      </Button>
                      <Button type="submit">Save</Button>
                    </Drawer.Footer>

                    <Drawer.CloseTrigger asChild>
                      <CloseButton size="sm" />
                    </Drawer.CloseTrigger>
                  </Drawer.Content>
                </Drawer.Positioner>
              </form>
            </Portal>
          </Drawer.Root>

          <Button
            variant="ghost"
            rounded="full"
            onClick={() => navigate("/tracker")}
          >
            <GoTasklist />
            Tasks
          </Button>

          <Button variant="ghost" rounded="full">
            <MdPerson /> Members
          </Button>
        </Stack>
      </Box>
      <Separator orientation="vertical" size="sm" ml={4} />
    </Flex>
  );
};

export default Sidebar;
