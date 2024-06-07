import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

function OauthLoginPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const accessToken = queryParams.get('token');

  useEffect(() => {
    window.localStorage.setItem('accessToken', accessToken);
    navigate('/todo');
  });

  return <div>로딩중..</div>;
}

export default OauthLoginPage;
