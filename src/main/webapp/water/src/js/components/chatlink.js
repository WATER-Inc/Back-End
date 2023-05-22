import React from "react";

class ChatLink extends React.Component{
    render(){
        return <a className="chat-link" id={this.props.chatId}>
            <div className="chat wrapper row-wrapper">
                <div className="chat-info wrapper column-wrapper">
                    <p className="chat-name">{this.props.chatName}</p>
                    <p className="lastMessage">{this.props.chatLastMessage}</p>
                </div>
            </div>
        </a>
    }
}
export default ChatLink;