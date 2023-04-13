import React from "react";
import NameInput from "../js/components/input/nameinput";
import PasswordInput from "../js/components/input/passwordinput";
class SingIn extends React.Component{
        constructor(props) {
                super(props);
        }
        render(){
                return <div>
                <NameInput></NameInput>
                {/*<PasswordInput></PasswordInput>*/}
                </div>;
        }
}
export default SingIn;
