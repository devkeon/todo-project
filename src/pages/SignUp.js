import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import { SignUpApi } from '../api/SignUpApi';
import { useNavigate } from 'react-router-dom';

function SignUp() {
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const signUpSubmitHandler = async (e) => {
    e.preventDefault();

    try {
      await SignUpApi(userName, password);

      window.alert('회원 가입 완료되었습니다!');

      navigate('/');
    } catch (err) {
      console.log(err);
      window.alert('다시 시도해주세요.');
    }
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: '8%' }}>
      <Grid container justifyContent={'center'} margin={'4px'}>
        <Typography component="h1" variant="h5" style={{ textAlign: 'center' }}>
          회원가입
        </Typography>
      </Grid>
      <form onSubmit={signUpSubmitHandler}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="userName"
              name="userName"
              label="사용하실 아이디(이름)을 입력해주세요."
              onChange={(e) => setUserName(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              fullWidth
              id="password"
              name="password"
              autoComplete="password"
              label="사용하실 비밀번호를 입력해주세요."
              type="password"
              onChange={(e) => setPassword(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" fullWidth>
              회원가입
            </Button>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default SignUp;
