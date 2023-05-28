import React from "react";
import wavesDesktop from "../resources/desktopSIngIn.svg";
import wavesPhone from "../resources/phoneSingIn.svg";
import arrow from "../resources/arrow.png";


class SingUp extends React.Component {
    state = {
        userName: "",
        userPassword: "",
        userPasswordDup: ""
    };
    handleUserNameInput = (event) => {
        this.setState({
            userName: event.target.value,
            userPassword: this.state.userPassword,
            userPasswordDup: this.state.userPasswordDup
        })
    }

    handleUserPasswordInput = (event) => {
        this.setState({
            userName: this.state.userName,
            userPassword: event.target.value,
            userPasswordDup: this.state.userPasswordDup
        })
    }
    handleUserPasswordDupInput = (event) => {
        this.setState({
            userName: this.state.userName,
            userPassword: this.state.userPassword,
            userPasswordDup: event.target.value
        })
    }

    formReset = () => {
        document.getElementById("userName").value = "";
        document.getElementById("password").value = "";
        document.getElementById("password-dup").value = "";
    }
    sendData = () => {
        if(this.state.userPassword.localeCompare(this.state.userPasswordDup) === 0) {
            let xhr = new XMLHttpRequest();
            xhr.open('POST', `https://localhost:8080/water_war/register?username=${this.state.userName}&userpassword=${this.state.userPassword}`);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = () => {
                console.log(this);
            }
            xhr.send();
        }else alert("passwords don't match");
        this.formReset();
    }

    componentDidMount() {
        const waves = document.getElementById("waves");
        let prevWidth = document.body.offsetWidth;
        if (prevWidth > 801) {
            waves.src = wavesDesktop;
        } else waves.src = wavesPhone;
        window.addEventListener("resize", () => {
            let width = document.body.offsetWidth;
            if (width != prevWidth) {
                prevWidth = width;
                if (width > 801) {
                    waves.src = wavesDesktop;
                } else waves.src = wavesPhone;
            }
        })
    }

    render() {
        return <div className="wrapper main-wrapper">
            <img id="waves" src={wavesDesktop} alt="waves image"/>
            <div className="wrapper column-wrapper main-section">
                <h3 className="water">WATER</h3>
                <div className="wrapper column-wrapper input-section">
                    <div className="wrapper column-wrapper input-wrapper">
                        <input id="userName" type="text" placeholder="Name" onChange={this.handleUserNameInput}/>
                        <input id="password" type="password" placeholder="Password"
                               onChange={this.handleUserPasswordInput}/>
                        <input id="password-dup" type="password" placeholder="Password"
                               onChange={this.handleUserPasswordDupInput}/>
                    </div>
                </div>
                <div className="wrapper row-wrapper submit-section">
                    <div className="wrapper column-wrapper sign-button-block">
                        <p className="link big-link">Sing Up</p>
                        <a className="link small-link" href="./">Sing In</a>
                    </div>
                    <div className="submit-wrapper">
                        <button className="submit" onClick={this.sendData}><img src={arrow}/></button>
                    </div>
                </div>
            </div>
        </div>
    }
}

export default SingUp;
