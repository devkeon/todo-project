import {
  AppBar,
  Button,
  Menu,
  MenuItem,
  Toolbar,
  Typography,
} from '@mui/material';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import MyCalendar from './MyCalendar';
import { WeatherApi } from '../api/WeatherApi';

function Header({ currentDate, setCurrentDate }) {
  const [loggedIn, setLoggedIn] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const [temperature, setTemperature] = useState(0.0);
  const [rainAmount, setRainAmount] = useState(0.0);

  const open = Boolean(anchorEl);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    setLoggedIn(!!token);

    const weatherApiSend = async () => {
      const resp = await WeatherApi();

      setTemperature(resp.data.temperature);
      setRainAmount(resp.data.rainAmount);
    };
    weatherApiSend();
  }, []);

  const navigate = useNavigate();

  const handleClickTitle = (event) => {
    event.preventDefault();

    if (anchorEl === null) {
      setAnchorEl(event.currentTarget);
    } else {
      setAnchorEl(null);
    }
  };

  const logoClickHandler = () => {
    navigate('/todo');
  };

  const logoutHandler = () => {
    const confirm = window.confirm('로그아웃 하시겠습니까?');

    if (confirm) {
      window.alert('로그아웃 되었습니다.');
      window.localStorage.removeItem('accessToken');
      window.localStorage.removeItem('userName');
      setLoggedIn(false);
    }
  };

  const loginRedirectHandler = () => {
    navigate('/');
  };

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography
          onClick={logoClickHandler}
          variant="h6"
          component="div"
          sx={{ flexGrow: 1 }}
        >
          TODO
        </Typography>
        <Typography sx={{ mx: 2 }}>현재 기온: {temperature}</Typography>
        {rainAmount ? (
          <Typography sx={{ mx: 2 }}>현재 강수량: {rainAmount}</Typography>
        ) : (
          <></>
        )}
        {loggedIn ? (
          <>
            <Button color="inherit" onClick={handleClickTitle}>
              날짜 선택
            </Button>
            <Menu
              id="simple-menu"
              anchorEl={anchorEl}
              open={open}
              onClose={handleClickTitle}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
            >
              <MenuItem>
                <MyCalendar
                  currentDate={currentDate}
                  setCurrentDate={setCurrentDate}
                />
              </MenuItem>
            </Menu>
            <Button color="inherit" onClick={logoutHandler}>
              로그아웃
            </Button>
          </>
        ) : (
          <Button color="inherit" onClick={loginRedirectHandler}>
            로그인
          </Button>
        )}
      </Toolbar>
    </AppBar>
  );
}

export default Header;
