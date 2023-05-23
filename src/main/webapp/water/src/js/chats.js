import React from "react";
import searchIcon from "../resources/icons8-find-67.png"
import ChatLink from "./components/chatlink";

class Chats extends React.Component {
    state = {
        userId: "",
        chats: []
    }
    chatList = [];

    getChats = function () {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/getchats");
        xhr.onload = function () {
            let tempChatList = [];
            const data = JSON.parse(xhr.response);
            data.forEach(chat => tempChatList.append(chat));
            this.setState({
                userId: this.state.userId,
                chats: tempChatList
            })
        };
        xhr.send(JSON.stringify({userId: this.state.userId}));

    };

    openChat = (element) => {
        let id = element.getAttribute("chatId");
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/chat");
        xhr.send(JSON.stringify({chatId: id}));

    }

    componentDidMount() {
        this.getChats();
        this.chatList = this.state.chats.map(chat => {
                return <ChatLink chatId={chat.chatId} chatName={chat.chatName} chatLastMessage={chat.lastMessage}
                                 onClick={this.openChat}/>;
            }
        )
    }

    render() {
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
