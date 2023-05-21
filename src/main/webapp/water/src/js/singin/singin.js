import React from "react";
import TextInput from "./components/textinput";
import SubmitButton from "./components/submitButton";
import styles from "../../css/signin.css";
import wavesDesktop from "../../resources/desktopSIngIn.svg";

class SingIn extends React.Component {
    componentDidMount() {
        const ImageScript = document.createElement("script");
        ImageScript.type = "text/babel"
        ImageScript.src = "./imageLogic.js";
        document.body.appendChild(ImageScript);
    }

    render() {
        return <div className="wrapper main-wrapper">
            <img id="waves" src={wavesDesktop}/>
            <div className="wrapper column-wrapper main-section">
                <h3 className="water">WATER</h3>
                <div className="wrapper column-wrapper input-section">
                    <div className="wrapper column-wrapper input-wrapper">
                        <TextInput id="userName" type="text" placeholder="Name"/>
                        <TextInput id="password" type="password" placeholder="Password"/>
                        <div className="wrapper link-wrapper">
                            <a className="forgot" href="/forgot-password">Forgot password?</a>
                        </div>
                    </div>
                </div>
                <div className="wrapper row-wrapper submit-section">
                    <div className="wrapper column-wrapper sign-button-block">
                        <p className="link big-link">Sing In</p>
                        <a className="link small-link" href="./singUp.html">Sing Up</a>
                    </div>
                    <div className="submit-wrapper">
                        <SubmitButton/>
                    </div>
                </div>
            </div>
        </div>
    }
}

export default SingIn;
