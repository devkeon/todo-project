import axios from 'axios';

export const GetTodoApi = async () => {
  const response = await axios.get(`http://localhost:8080/api/todo`, {
    headers: {
      Authorization: `${window.localStorage.getItem('accessToken')}`,
    },
  });

  if (response.data.code !== 'COM-000') {
    throw new Error();
  }

  return response.data;
};
