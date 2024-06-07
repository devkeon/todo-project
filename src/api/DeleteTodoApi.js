import axios from 'axios';

export const DeleteTodoApi = async (todoId) => {
  const response = await axios.delete(
    `http://localhost:8080/api/todo/trashcan`,
    {
      headers: {
        Authorization: `${window.localStorage.getItem('accessToken')}`,
      },
      params: {
        todoId: todoId,
      },
    }
  );

  if (response.data.code !== 'COM-000') {
    throw new Error();
  }

  return response.data;
};
