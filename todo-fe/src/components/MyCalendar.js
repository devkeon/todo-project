import 'react-calendar/dist/Calendar.css';
import React from 'react';
import Calendar from 'react-calendar';

function MyCalendar({ currentDate, setCurrentDate }) {
  return (
    <div>
      <Calendar onChange={setCurrentDate} value={currentDate}></Calendar>
    </div>
  );
}

export default MyCalendar;
