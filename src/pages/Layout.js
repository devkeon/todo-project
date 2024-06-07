import React, { useEffect, useState } from 'react';
import Header from '../components/Header';
import TodoPage from './TodoPage';
import { GetTodoApi } from '../api/GetTodoApi';

function Layout({ children }) {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [todoList, setTodoList] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await GetTodoApi();
        setTodoList([...response.data.todos]);
      } catch (err) {
        console.error(err);
      }
    };
    fetchData();
  }, []);

  return (
    <>
      <Header currentDate={currentDate} setCurrentDate={setCurrentDate} />
      <div>
        {React.Children.map(children, (child) => {
          if (child.type === TodoPage) {
            return React.cloneElement(child, {
              currentDate,
              todoList,
              setTodoList,
            });
          }
          return child;
        })}
      </div>
    </>
  );
}

export default Layout;
