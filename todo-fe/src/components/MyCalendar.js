import 'react-calendar/dist/Calendar.css';
import React from 'react';
import Calendar from 'react-calendar';
import { styled } from '@mui/system';

const CalendarContainer = styled('div')({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  flexDirection: 'column',
  padding: '0px',
  backgroundColor: '#E0F7FA',
  borderRadius: 40,
  boxShadow: '0 4px 8px #0000A',
  '& .react-calendar': {
    border: 'none',
  },
  '& .react-calendar__month-view__days__day--neighboringMonth': {
    visibility: 'hidden',
  },
  '& .react-calendar__tile': {
    margin: '0', 
  },
});



function MyCalendar({ currentDate, setCurrentDate }) {
  return (
    <CalendarContainer>
      <Calendar onChange={setCurrentDate} value={currentDate}/>
    </CalendarContainer>
  );
}

export default MyCalendar;
