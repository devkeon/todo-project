import { DeleteOutline, EditOutlined } from '@mui/icons-material';
import {
  Checkbox,
  IconButton,
  InputBase,
  ListItem,
  ListItemSecondaryAction,
  ListItemText,
} from '@mui/material';
import React, { useState } from 'react';
import { DeleteTodoApi } from '../api/DeleteTodoApi';
import { EditTodoApi } from '../api/EditTodoApi';

function Todo({ todo, id, todoList, setTodoList }) {
  const [item, setItem] = useState(todo);
  const [readOnly, setReadOnly] = useState(true);

  const deleteEventHandler = async () => {
    const confirm = window.confirm(`${item.title}을 삭제하시겠습니까?`);

    if (confirm) {
      await DeleteTodoApi(id);
      setTodoList(todoList.filter((beforeTodo) => beforeTodo.todoId !== id));
    }
  };

  const editToggleHandler = async () => {
    if (readOnly === false) {
      const response = await EditTodoApi(item);

      setItem(response.data);

      const newTodoList = todoList.map((target) => {
        if (target.todoId === id) {
          return response.data;
        }
        return target;
      });

      setTodoList(newTodoList);
      setReadOnly(true);
    } else {
      setReadOnly(false);
    }
  };

  const keyDownEventHandler = (e) => {
    if (e.key === 'Enter' && !readOnly) {
      editTodoHandler();
    }
  };

  const editTodoHandler = (e) => {
    e.preventDefault();

    const thisItem = {
      ...item,
      title: e.target.value,
    };

    setItem(thisItem);
  };

  const checkboxEventHandler = async (e) => {
    const thisItem = {
      ...item,
      done: item.done ? false : true,
    };

    const response = await EditTodoApi(thisItem);

    const newTodoList = todoList.map((target) => {
      if (target.todoId === id) {
        return response.data;
      }
      return target;
    });

    setTodoList(newTodoList);

    setItem(response.data);
  };

  return (
    <ListItem>
      <Checkbox checked={item.done} onChange={checkboxEventHandler} />
      <ListItemText>
        <InputBase
          inputProps={{ 'aria-label': 'naked', readOnly: readOnly }}
          type="text"
          id={item.id}
          name={item.id}
          value={item.title}
          multiline={true}
          fullWidth={true}
          onChange={editTodoHandler}
          onKeyDown={keyDownEventHandler}
        />
      </ListItemText>
      <ListItemSecondaryAction>
        <IconButton aria-label="Edit" onClick={editToggleHandler}>
          <EditOutlined />
        </IconButton>
        <IconButton aria-label="Delete" onClick={deleteEventHandler}>
          <DeleteOutline />
        </IconButton>
      </ListItemSecondaryAction>
    </ListItem>
  );
}

export default Todo;
