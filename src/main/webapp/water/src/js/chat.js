import React from "react";
import styles from "../chat/chat.css";
import sendIcon from "../resources/icons8-email-send-60.png"
import returnIcon from "../resources/icons8-double-left-48.png"
import Message from "./components/message";

class Chat extends React.Component{
    messageList =  [];
    userId = 1;
    componentDidMount() {
        const messages = [
            {
                from: 2,
                name: "Nick",
                text: "Hello, Mike!"
            },
            {
                from: 1,
                name: "Mike",
                text: "Hello, Nick!"
            },
            {
                from: 2,
                name: "Nick",
                text: "..."
            },
        ];
        this.messageList = messages.map( message => {
                return <Message from={message.from == this.userId ? "my" : "from"} name={message.username} messageText={message.text} />;
            }
        )
    }

    render(){
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
                    <input type="text" placeholder="Text.." name="text"/>
                    <button type="submit"><img src={sendIcon}/></button>
                </form>
            </div>
        </div>
    }
}
export default Chat;
