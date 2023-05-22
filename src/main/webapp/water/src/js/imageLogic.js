import wavesDesktop from "../resources/desktopSIngIn.svg";
import wavesPhone from "../resources/phoneSingIn.svg";

(function(){
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
})();