# Task Tracking appliaction

Application helps its users to track their tasks. Tasks can be moved throgh different stages, starting from 'To-Do' to 'Done'. It is created in kanban style so users can drag and drop tasks between columns to change status of task.

Users also can add other users to tasks and it will appear in logged user tracker.

For each tasks users can add comments and discuss.

Users can register and log using social accounts. It is possible due to Auth0 framework that handles authorization.

## Technologies

To create this appliaction I have used:

* Java with Spring Boot to handle backend requests
* Postresql as db
* React, vite and typescript
* Chakra ui components
* Dnd framework to move tasks
* Auth0 to authorizate users
* Junit to test use cases on backend






## Deployment

This project is divided into 2 sections backend and frontend. Both folders have a dockerfile. To combine them and run both files in the same time use docker compose file.

To build use:
```
  docker compose up --build
```

After that appliaction should be running on localhost:5173

If for some reason it builds and do not start use:

```
  docker compose up
```
## API Reference

### Task

#### Post a task

```
  POST /api/task
```

| Parameter | Type     | Description                | Required |
| :-------- | :------- | :------------------------- | :------- |
| `title`   | `string` |Title of a task| `✅`
| `description`   | `string` |Description of a task| `✅`
| `usersIds`   | `UUID[]` |UUID of users| `✅`

Return:
```
  201 Created
```

#### Get tasks for logged user

```
  GET /api/task/list
```
Return:
```
  200 List<Task>
```

#### Get task

```
  GET /api/task?id={$}
```

| Parameter | Type     | Description                | Required |
| :-------- | :------- | :------------------------- | :------- |
| `id`   | `UUID` |UUID of a task| `✅`

Return:
```
  200 Task
```

#### Change status of task

```
  POST /api/task
```

| Parameter | Type     | Description                | Required |
| :-------- | :------- | :------------------------- | :------- |
| `id`   | `UUID` |UUID of a task| `✅`
| `status`   | `string` |New status of a task| `✅`

Return:
```
  201 Ok
```

### Comment

#### Post a task

```
  POST /api/comment
```

| Parameter | Type     | Description                | Required |
| :-------- | :------- | :------------------------- | :------- |
| `id`   | `UUID` |UUID of a task| `✅`
| `text`   | `string` |Text of a comment| `✅`

Return:
```
  201 Created
```


## License

This project is for educational purposes only. All rights reserved. Do not use or distribute without permission.

