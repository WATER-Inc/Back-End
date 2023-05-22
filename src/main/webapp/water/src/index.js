import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import * as ReactDOMClient from 'react-dom/client';
import SingUp from "./js/singup";
import SingIn from "./js/singin";
import Chat from "./js/chat";
import Chats from "./js/chats";

const container = document.getElementById('root');
const root = ReactDOMClient.createRoot(container);

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<SingIn/>}/>
                <Route path="/singup" element={<SingUp/>}/>
                <Route path="/chat" element={<Chat/>}/>
                <Route path="/chats" element={<Chats/>}/>
            </Routes>
        </BrowserRouter>
    );
}

root.render(
    <React.Suspense>
        <App/>
    </React.Suspense>
);

