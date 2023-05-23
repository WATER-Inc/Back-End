import React from "react";
import * as ReactDOMClient from 'react-dom/client';
import SingUp from "../js/singup";

const container = document.getElementById('root');
const root = ReactDOMClient.createRoot(container);


root.render(
    <React.StrictMode>
        <SingUp/>
    </React.StrictMode>
);

