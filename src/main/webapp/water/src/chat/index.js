import React from "react";
import * as ReactDOMClient from 'react-dom/client';
import Chat from "../js/chat";

const container = document.getElementById('root');
const root = ReactDOMClient.createRoot(container);


root.render(
    <React.StrictMode>
        <Chat/>
    </React.StrictMode>
);

