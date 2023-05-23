import React, {useState} from "react";
import SubmitButton from "./components/submitButton";
import wavesDesktop from "../resources/desktopSIngIn.svg";
import wavesPhone from "../resources/phoneSingIn.svg";

class SingIn extends React.Component{
    state = {
      userName: "",
      userPassword: ""
    };
    handleUserNameInput = (event) => {
        this.setState({
            userName: event.target.value,
            userPassword: this.state.userPassword
        })
    }

    handleUserPasswordInput = (event) => {
        this.setState({
            userName: this.state.userName,
            userPassword: event.target.value
        })
    }

    sendData = () => {
        let xhr = new XMLHttpRequest();
        xhr.open('POST','https://localhost:8080/singup');
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = () => {
            console.log(this);
        }
        xhr.send(JSON.stringify(this.state));
    }
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
                        <input id="userName" type="text" placeholder="Name" onChange={this.handleUserNameInput}/>
                        <input id="password" type="password" placeholder="Password" onChange={this.handleUserPasswordInput}/>
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
                        <SubmitButton onClick={this.sendData}/>
                    </div>
                </div>
            </div>
        </div>
    }
}
export default SingIn;
