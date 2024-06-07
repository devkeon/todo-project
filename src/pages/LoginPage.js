import React, { useState } from 'react';
import { LoginApi } from '../api/LoginApi';
import { useNavigate } from 'react-router-dom';
import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import kakaoLogin from '../assets/images/kakao_logo.svg';

function LoginPage() {
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const kakaoLoginHandler = () => {
    window.location.replace('http://localhost:8080/oauth2/authorization/kakao');
  };

  const signUpHandler = () => {
    navigate('/signup');
  };

  const loginSubmitHandler = async (e) => {
    e.preventDefault();

    try {
      const [token, response] = await LoginApi(userName, password);

      window.localStorage.setItem('accessToken', token);
      window.alert('로그인 되었습니다.');

      navigate('/todo');
    } catch (err) {
      window.alert('다시 시도해주세요.');
    }
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: '8%' }}>
      <Grid container justifyContent={'center'} margin={'4px'}>
        <Typography component="h1" variant="h5" style={{ textAlign: 'center' }}>
          TODO 로그인
        </Typography>
      </Grid>
      <form onSubmit={loginSubmitHandler}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="userName"
              name="userName"
              label="유저 이름"
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
              label="비밀번호"
              type="password"
              onChange={(e) => setPassword(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" fullWidth>
              로그인
            </Button>
          </Grid>
        </Grid>
      </form>
      <Grid container>
        <Grid item xs={12} justifyContent="center">
          <Typography
            style={{ textAlign: 'center' }}
            margin={'2px'}
            padding={'2px'}
          >
            or
          </Typography>
        </Grid>
        <Grid onClick={kakaoLoginHandler}>
          <img src={kakaoLogin} alt="kakao_login" width={'100%'} />
        </Grid>
      </Grid>
      <Grid>
        <Button type="button" onClick={signUpHandler} fullWidth>
          회원 가입
        </Button>
      </Grid>
    </Container>
  );
}

export default LoginPage;
