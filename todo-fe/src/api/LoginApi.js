import axios from 'axios';

export const LoginApi = async (userName, password) => {
  const response = await axios.post(`http://localhost:8080/api/login`, {
    userName: userName,
    password: password,
  });

  if (response.data.code !== 'COM-000') {
    throw new Error();
  }

  const accessToken = response.headers['authorization'];

  return [accessToken, response.data];
};
