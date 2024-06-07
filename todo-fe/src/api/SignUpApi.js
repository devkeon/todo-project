import axios from 'axios';

export const SignUpApi = async (userName, password) => {
  const response = await axios.post(`http://localhost:8080/api/sign-up`, {
    userName: userName,
    password: password,
  });

  if (response.data.code !== 'COM-000') {
    const code = response.data.code;
    console.log(code);
    throw new Error();
  }

  return response.data;
};
