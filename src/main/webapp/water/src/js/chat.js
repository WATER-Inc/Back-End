import React from "react";
import styles from "../chat/chat.css";
import sendIcon from "../resources/icons8-email-send-60.png"
import returnIcon from "../resources/icons8-double-left-48.png"
import Message from "./components/message";

class Chat extends React.Component {
    state = {
        userId: "",
        chatId: "",
        currentMessage: "",
        messageList: []
    }

    getMessages = () => {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/getmessages");
        xhr.onload = function () {
            let tempMessageList = [];
            const data = JSON.parse(xhr.response);
            data.forEach(message => tempMessageList.append(message));
            this.setState({
                userId: this.state.userId,
                chatId: this.state.chatId,
                currentMessage: this.state.currentMessage,
                messageList: tempMessageList
            })
        };
        xhr.send(JSON.stringify({userId: this.state.userId, chatId: this.state.chatId}));
    }

    handleMessage = (event) => {
        this.setState({
            userId: this.state.userId,
            chatId: this.state.chatId,
            currentMessage: event.target.value,
            messageList: this.state.messageList
        })
    }

    sendMessage = () => {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/sendmessage");
        xhr.send(JSON.stringify({
            userId: this.state.userId,
            chatId: this.state.chatId,
            message: this.state.currentMessage
        }));
        this.messageList.append(<Message from="my" name={this.userName} messageText={this.state.currentMessage}/>);
        this.setState({
            userId: this.state.userId,
            chatId: this.state.chatId,
            currentMessage: "",
            messageList: this.state.messageList
        })
        this.forceUpdate();
    }

    componentDidMount() {
        this.getMessages();
        this.messageList = this.state.messageList.map(message => {
                return <Message from={message.from == this.userId ? "my" : "from"} name={message.username}
                                messageText={message.text}/>;
            }
        )
    }

    render() {
        return <div className="wrapper main-wrapper">
            <div className="wrapper row-wrapper header">
                <a className="arrow"><img src={returnIcon}/></a>
                <p className="chat-name">Name</p>
            </div>
            <div className="wrapper column-wrapper message-list">
                {this.messageList}
            </div>
            <div className="message-input">
                <form className="wrapper row-wrapper">
                    <input type="text" placeholder="Text.." name="text" onChange={this.handleMessage}/>
                    <button type="submit" onSubmit={this.sendMessage}><img src={sendIcon}/></button>
                </form>
            </div>
        </div>
    }
}

export default Chat;
