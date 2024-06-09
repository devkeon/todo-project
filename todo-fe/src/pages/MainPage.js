import { Container, List, Paper } from '@mui/material';
import React, { useState } from 'react';
import AddTodo from '../components/AddTodo';
import Todo from '../components/Todo';

function MainPage() {
  const [items, setItem] = useState([]);

  const add = (item) => {
    const newItem = {
      ...item,
      id: 'ID-' + items.length,
      done: false,
    };

    setItem([...items, newItem]);
    console.log('items:', items);
  };

  // 예약어로 인해 사용 불가로 del 할당
  const del = (item) => {
    const newItem = items.filter((e) => e.id !== item.id);
    setItem(newItem);
    console.log('Update Items : ', items);
  };

  return (
    <div className="App">
      <Paper style={{ margin: 16, padding: 16,backgroundColor}}>
        <Container maxWidth="md">
          <AddTodo add={add} />
          <div className="TodoList">
            <Paper style>
              <List>
                {items.map((item) => (
                  <Todo item={item} key={item.id} del={del} />
                ))}
              </List>
            </Paper>
          </div>
        </Container>
      </Paper>
    </div>
  );
}

export default MainPage;
