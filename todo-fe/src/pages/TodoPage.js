import React from 'react';
import { Container, List } from '@mui/material';
import Todo from '../components/Todo';
import AddTodo from '../components/AddTodo';

function TodoPage({ currentDate, todoList, setTodoList }) {
  const convertToDateString = () => {
    const date = new Date(currentDate);
    const offset = date.getTimezoneOffset() * 60000;
    const localDate = new Date(date.getTime() - offset);

    return localDate.toISOString().slice(0, 10);
  };

  return (
    <Container>
      <AddTodo
        date={convertToDateString()}
        todoList={todoList}
        setTodoList={setTodoList}
      />
      <List>
        {todoList
          .filter((todo) => todo.date === convertToDateString())
          .map((todo) => (
            <Todo
              todo={todo}
              id={todo.todoId}
              key={todo.todoId}
              todoList={todoList}
              setTodoList={setTodoList}
            />
          ))}
      </List>
    </Container>
  );
}

export default TodoPage;
