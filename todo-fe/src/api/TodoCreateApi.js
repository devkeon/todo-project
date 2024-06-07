import axios from 'axios';

export const TodoCreateApi = async (title, date) => {
  const response = await axios.post(
    `http://localhost:8080/api/todo`,
    {
      title: title,
      done: false,
      date: date,
    },
    {
      headers: {
        Authorization: `${localStorage.getItem('accessToken')}`,
      },
    }
  );

  if (response.data.code !== 'COM-000') {
    throw new Error();
  }

  return response.data;
};
