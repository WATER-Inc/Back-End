import React from "react";

class TextInput extends React.Component{
    render() {
        return <input id = {this.props.id} type = {this.props.type} className={this.props.class} placeholder = {this.props.placeholder}/>;
    }
}
export default TextInput;