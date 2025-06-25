import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Button, ThemeProvider } from '@mui/material';
import Navbar from './customer/components/Navbar/Navbar';
import customTheme from './Theme/customeTheme';
import Home from './customer/pages/Home/home';
 

function App() {
  return (

    <ThemeProvider theme={customTheme}>
      <div>
        <Navbar />
          <Home />
      </div>
    </ThemeProvider>



  );
}

export default App;
