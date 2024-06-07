import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import SignUp from './pages/SignUp';
import TodoPage from './pages/TodoPage';
import Layout from './pages/Layout';
import OauthLoginPage from './pages/OauthLoginPage';
import TrashPage from './pages/TrashPage';

function Router() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />}></Route>
        <Route path="/signup" element={<SignUp />}></Route>
        <Route
          path="/todo"
          element={<Layout children={<TodoPage />} />}
        ></Route>
        <Route path="/oauth/callback" element={<OauthLoginPage />}></Route>
        <Route
          path="/trashcan"
          element={<Layout children={<TrashPage />} />}
        ></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default Router;
