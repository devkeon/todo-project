import axios from 'axios';

export const EditTodoApi = async (editTodo) => {
  const response = await axios.patch(
    `http://localhost:8080/api/todo`,
    editTodo,
    {
      headers: {
        Authorization: `${window.localStorage.getItem('accessToken')}`,
      },
    }
  );

  if (response.data.code !== 'COM-000') {
    throw new Error();
  }

  return response.data;
};
