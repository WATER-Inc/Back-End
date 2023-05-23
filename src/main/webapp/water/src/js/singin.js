import React from "react";
import TextInput from "./components/textinput";
import SubmitButton from "./components/submitButton";
import wavesDesktop from "../resources/desktopSIngIn.svg";
import wavesPhone from "../resources/phoneSingIn.svg";

class SingUp extends React.Component{
    componentDidMount() {
        const waves = document.getElementById("waves");
        let prevWidth = document.body.offsetWidth;
        if(prevWidth > 801){
            waves.src=wavesDesktop;
        }else  waves.src=wavesPhone;
        window.addEventListener("resize", () => {
            let width = document.body.offsetWidth;
            if(width != prevWidth){
                prevWidth = width;
                if(width > 801){
                    waves.src=wavesDesktop;
                }else  waves.src=wavesPhone;
            }
        })
    }
    render(){
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
                        <a className="link small-link" href="/singup">Sing Up</a>
                    </div>
                    <div className="submit-wrapper">
                        <SubmitButton />
                    </div>
                </div>
            </div>
        </div>
    }
}
export default SingUp;
