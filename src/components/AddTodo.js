import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import { TodoCreateApi } from '../api/TodoCreateApi';
import { TrashTodoApi } from '../api/DeleteTodoApi';
import { useNavigate } from 'react-router-dom';

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

      needToDelete.map(async (target) => await TrashTodoApi(target.todoId));

      const newTodoList = todoList.filter((target) => target.date !== date);

      setTodoList(newTodoList);
    }
  };

  return (
    <Container style={{ margin: 10, padding: 10 }}>
      <Grid container style={{ margin: 6, padding: 6 }}>
        <Grid xs={10} md={11} item style={{ paddingRight: 16 }}>
          <TextField
            placeholder="추가할 TODO를 입력해주세요."
            fullWidth
            onChange={onInputChange}
            value={item.title}
            onKeyDown={enterKeyEventHandler}
          />
        </Grid>
        <Grid xs={1} md={1} item>
          <Button fullWidth variant="outlined" onClick={onButtonClick}>
            +
          </Button>
        </Grid>
      </Grid>
      <Grid
        container
        alignItems="center"
        justifyContent={'space-between'}
        style={{ margin: 6, padding: 6 }}
      >
        <Grid>
          <Typography style={{ paddingRight: 16 }}>{date}</Typography>
        </Grid>
        <Grid>
          <Button fullWidth onClick={deleteAllHandler}>
            일괄 삭제
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
}

export default AddTodo;
