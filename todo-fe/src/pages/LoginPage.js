import React, { useState } from 'react';
import { LoginApi } from '../api/LoginApi';
import { useNavigate } from 'react-router-dom';
import { Button, Container, Grid, TextField, Typography,Paper } from '@mui/material';
import kakaoLogin from '../assets/images/kakao_logo.svg';
import { styled } from '@mui/system';


const StyledContainer = styled(Container)({
  marginTop: '16%',
  backgroundColor: 'white',
});

const StyledPaper = styled(Paper)({
  padding: 20,
  backgroundColor: '#D8EFD3',
});

const StyledTextField = styled(TextField)({
  backgroundColor: '#F1F8E8',
});

const StyledButton = styled(Button)({
  backgroundColor: '#55AD9B',
  marginLeft:120,
  width: '120px',
  color: 'white',
  fontWeight: 500,
  '&:hover':{
    backgroundColor: '#4d9989',
    color: 'white',
  },
});

const StyledTodoLoginTypography = styled(Typography)({
  fontWeight: 700,
  fontSize: 50,
  fontFamily: "'Fantasy', sans-serif",
  color: '#3c786b',
  textAlign: 'center',
});

const StyledOrTypography = styled(Typography)({
  color: '#3c786b',
  fontWeight: 300,
});

const StyledGrid = styled(Grid)({
});

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
    <StyledContainer component="main" maxWidth="xs">
      <StyledPaper>
        <StyledGrid container justifyContent={'center'}>
          <StyledTodoLoginTypography component="h1" variant="h5">
            TODO-LIST
          </StyledTodoLoginTypography>
        </StyledGrid>
        <form onSubmit={loginSubmitHandler}>
          <StyledGrid container spacing={2}>
            <StyledGrid item xs={12}>
              <StyledTextField
                variant="outlined"
                required
                fullWidth
                id="userName"
                name="userName"
                label="아이디"
                onChange={(e) => setUserName(e.target.value)}
              />
            </StyledGrid>
            <StyledGrid item xs={12}>
              <StyledTextField
                required
                fullWidth
                id="password"
                name="password"
                autoComplete="password"
                label="비밀번호"
                type="password"
                onChange={(e) => setPassword(e.target.value)}
              />
            </StyledGrid>
            <StyledGrid item xs={12}>
              <StyledButton type="submit">
                로그인
              </StyledButton>
            </StyledGrid>
          </StyledGrid>
        </form>
        <StyledGrid container>
          <StyledGrid item xs={12} justifyContent="center">
            <StyledOrTypography sx={{ textAlign: 'center' }}>
              or
            </StyledOrTypography>
          </StyledGrid>
          <StyledGrid onClick={kakaoLoginHandler}>
            <img src={kakaoLogin} alt="kakao_login" width="100%" />
          </StyledGrid>
        </StyledGrid>
        <StyledGrid>
          <StyledButton type="button" onClick={signUpHandler}>
            회원 가입
          </StyledButton>
        </StyledGrid>
      </StyledPaper>
    </StyledContainer>
  );
}

export default LoginPage;
