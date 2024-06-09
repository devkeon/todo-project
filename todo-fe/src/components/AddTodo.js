import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import { DeleteTodoApi } from '../api/DeleteTodoApi';
import { useNavigate } from 'react-router-dom';
import { TodoCreateApi } from '../api/TodoCreateApi';

import { styled } from '@mui/system';

const StyledContainer = styled(Container)({
  borderRadius:40,
  backgroundColor:'#E6FFE6',
  color:'green',
  margin: 10,
  padding: 1,
});

const StyledPlusGrid = styled(Grid)({
  borderRadius:70,
  backgroundColor:'#FFFFE0',
  margin: 6,
  padding: 20,
});

const StyledDeleteGrid = styled(Grid)({
  margin: 6,
  padding: 20,
});

const StyledTextField = styled(TextField)({
  width: '100%',
  backgroundColor:'white',
});

const StyledPlusButton = styled(Button)({
  width: '100%',
  border: 'none',
  top: '15%',
  backgroundColor: '#66BB6A',
  borderRadius: 20,
  color: 'white',
  fontWeight: 500,
  boxShadow: '0px 4px 8px #0000001A', 
  transition: 'all 0.3s ease',
  '&:hover': {
    backgroundColor: '#558B2F',
    color: 'black',
    boxShadow: '0px 8px 16px #0000001A',
  },
  '&:active': {
    boxShadow: '0px 4px 8px #0000001A',
    transform: 'translateY(2px)',
  },
});

const StyledDeleteButton = styled(Button)({
  marginRight:10,
  border: 'none',
  backgroundColor: '#66BB6A',
  borderRadius: 20,
  color: 'white',
  fontWeight: 500,
  boxShadow: '0px 4px 8px #0000001A', 
  transition: 'all 0.3s ease',
  '&:hover': {
    backgroundColor: '#558B2F',
    color: 'black',
    boxShadow: '0px 8px 16px #0000001A',
  },
  '&:active': {
    boxShadow: '0px 4px 8px #0000001A',
    transform: 'translateY(2px)',
  },
});

function AddTodo({ date, todoList, setTodoList }) {
  const [item, setItem] = useState({ title: '' });
  const navigate = useNavigate();

  const onInputChange = (e) => {
    const thisItem = {
      ...item,
      title: e.target.value,
    };
    setItem(thisItem);
    console.log(thisItem);
  };

  const onButtonClick = async () => {
    if (window.localStorage.getItem('accessToken') === null) {
      window.alert('서비스를 이용하려면 로그인 해주세요.');
      navigate('/');
      return;
    }

    const resopnse = await TodoCreateApi(item.title, date);
    setTodoList([...todoList, resopnse.data]);
    setItem({ title: '' });
  };

  const enterKeyEventHandler = (e) => {
    if (e.key === 'Enter') {
      onButtonClick();
    }
  };

  const deleteAllHandler = async () => {
    if (window.localStorage.getItem('accessToken') === null) {
      window.alert('서비스를 이용하려면 로그인 해주세요.');
      navigate('/');
      return;
    }
    const confirm = window.confirm(
      `정말로 ${date}의 Todo를 전부 삭제하시겠습니까?`
    );
    if (confirm) {
      const needToDelete = todoList.filter((target) => target.date === date);

      needToDelete.map(async (target) => await DeleteTodoApi(target.todoId));

      const newTodoList = todoList.filter((target) => target.date !== date);

      setTodoList(newTodoList);
    }
  };

  return (
    <StyledContainer>
      <StyledPlusGrid container>
        <Grid xs={10} md={11} item style={{ paddingRight: 16 }}>
          <StyledTextField
            placeholder="추가할 TODO를 입력해주세요."
            fullWidth
            onChange={onInputChange}
            value={item.title}
            onKeyDown={enterKeyEventHandler}
          />
        </Grid>
        <Grid xs={1} md={1} item>
          <StyledPlusButton fullWidth variant="outlined" onClick={onButtonClick}>
            +
          </StyledPlusButton>
        </Grid>
      </StyledPlusGrid>
      <StyledDeleteGrid
        container
        alignItems="center"
        justifyContent='space-between'>
        <Grid>
          <Typography style={{ paddingRight: 16, fontSize:30, fontStyle:'bold',}}>{date} 일정</Typography>
        </Grid>
        <Grid>
          <StyledDeleteButton fullWidth onClick={deleteAllHandler}>
            일괄 삭제
          </StyledDeleteButton>
        </Grid>
      </StyledDeleteGrid>
    </StyledContainer>
  );
}

export default AddTodo;
