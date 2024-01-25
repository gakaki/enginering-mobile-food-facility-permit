import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

import 'virtual:uno.css'
import "./main.css";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);