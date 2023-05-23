import React from "react";
import * as ReactDOMClient from 'react-dom/client';
import SingIn from "../js/singin";

const container = document.getElementById('root');
const root = ReactDOMClient.createRoot(container);



root.render(
    <React.StrictMode>
        <SingIn/>
    </React.StrictMode>
);

