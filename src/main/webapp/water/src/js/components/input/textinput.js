import React from "react";

class TextInput extends React.Component{
    placeholder;
    inputType;
    inputStyle;
    constructor(props) {
        super(null);
        this.placeholder = props.placeholder;
        this.inputType = props.inputType;
        this.inputStyle = props.inputStyle;
    }
    render() {
        return <input type = {this.inputType} className={this.inputStyle} placeholder = {this.placeholder}/>;
    }
}

export default TextInput;