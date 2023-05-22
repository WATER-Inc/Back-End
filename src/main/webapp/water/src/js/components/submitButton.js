import React from "react";
import arrow from "../../resources/arrow.png"

class SubmitButton extends React.Component{
    render(){
        return <button className="submit"><img src={arrow}/></button>;
    }
}

export default SubmitButton;