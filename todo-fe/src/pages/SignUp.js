import { Button, Container, Grid, TextField, Typography, Paper} from '@mui/material';
import React, { useState } from 'react';
import { SignUpApi } from '../api/SignUpApi';
import { useNavigate } from 'react-router-dom';
import { styled } from '@mui/system';

const StyledContainer = styled(Container)({
  marginTop: '30%',
  backgroundColor: 'white',
});

const StyledPaper = styled(Paper)({
  padding: 20,
  backgroundColor: '#D8EFD3',
});

const StyledTextField = styled(TextField)({
  backgroundColor: '#F1F8E8',
});

const StyledTypography = styled(Typography)({
  fontWeight: 700,
  fontFamily: "'Fantasy', sans-serif",
  color: '#55AD9B',
  textAlign: 'center',
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
    <StyledContainer component="main" maxWidth="xs" style={{ marginTop: '8%' }}>
      <StyledPaper>
      <Grid container justifyContent={'center'} margin={'4px'}>
        <StyledTypography component="h1" variant="h5">
          회원가입
        </StyledTypography>
      </Grid>
      <form onSubmit={signUpSubmitHandler}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <StyledTextField
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
            <StyledTextField
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
            <StyledButton type="submit" fullWidth>
              회원가입
            </StyledButton>
          </Grid>
        </Grid>
      </form>
      </StyledPaper>
    </StyledContainer>
  );
}

export default SignUp;
