import React from "react";
import TextInput from "./textinput";
import styles from '../../../css/signin.css';
class NameInput extends TextInput{
    constructor() {
        let props = {
            placeholder : "Name",
            inputType: "text",
            inputStyle: styles.minput
        }
        super(props);
    }
}
export default NameInput;
