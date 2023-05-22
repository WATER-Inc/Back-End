import React from "react";
import styles from "../css/chats.css";
import searchIcon from "../resources/icons8-find-67.png"
import ChatLink from "./components/chatlink";

class Chats extends React.Component{
    chatList =  [];
    componentDidMount() {
        const chats = [];
        this.chatList = chats.map( chat => {
                return <ChatLink chatId={chat.chatId} chatName={chat.chatName} chatLastMessage={chat.lastMessage} />;
            }
        )
    }

    render(){
        return <div>
            <div className="search-bar">
                <form className="wrapper row-wrapper">
                    <input type="text" placeholder="Search.." name="search"/>
                        <button type="submit"><img src={searchIcon}/></button>
                </form>
            </div>
            <div className="wrapper column-wrapper chat-list">
                {this.chatList}
            </div>
        </div>
    }
}
export default Chats;
