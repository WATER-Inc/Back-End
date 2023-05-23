import React from "react";
import * as ReactDOMClient from 'react-dom/client';
import Chats from "../js/chats";

const container = document.getElementById('root');
const root = ReactDOMClient.createRoot(container);



root.render(
    <React.StrictMode>
        <Chats/>
    </React.StrictMode>
);

