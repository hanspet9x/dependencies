import React, { useEffect, useRef } from 'react';
import { Provider } from 'react-redux';
import { connect } from 'react-redux';
import { createStore } from 'redux';

export const TOAST_SHORT = "2000";
export const TOAST_LONG = "4000";
export const ToastPosition = {
    center: "c",
    bottomCenter: "bc",
    rightCenter: "rc",
    leftCenter: "lc",
    left: "l",
    right: "r",

}



const Wrapper = ({info, time, reset}) => {

    let watch = useRef(null);
    let hpRef = useRef();

    const handleMouseEnter = () => {
        window.clearTimeout(watch.current);
    }
    
    const handleMouseOut = () => {
        hide();
    }

    const show = () => {

        hpRef.current.style.transform = "translateY(-20px)";
        hpRef.current.style.opacity = 1;
        hpRef.current.style.pointerEvents = "auto";
        hide(time);

    }



    

    const hide = (time) => {
        watch.current = window.setTimeout(() => {
            hpRef.current.style.transform = "translateY(0px)";
            hpRef.current.style.opacity = "0";
            hpRef.current.style.pointerEvents = "none";
            reset();
        }, time);
        
    }

    useEffect(() => {
        if(typeof info === "string" && info.length > 0){
            show();
        }    
    })

    return (
        <React.Fragment>
            <div id="HPnotify" ref={hpRef} >

                <div id="HPFtext"
                    onMouseEnter={handleMouseEnter}
                    onMouseOut={handleMouseOut}
                >
                    {info}
                </div>

            </div>
        </React.Fragment>
    );

}

const store = createStore((state = {info: "", time: TOAST_SHORT}, action) => {
    return {...state, ...action.data}
});

const mapState = (state) => {
    return {
        info: state.info,
        time: state.time,
    }
}

const resetToast = (dispatch) => {
    return {
        reset: () => dispatch({type: "Close", data: {time: 0}})
    }
}   

const ToastWrapper = connect(mapState, resetToast)(Wrapper);



export const toast = (info = "Toast", time = TOAST_SHORT) => {
    store.dispatch({type: "Alert", data: {info: info, time: time}});
}

const Toast = ({ children }) => {

    return (
        <Provider store={store}>
            {children}
            <ToastWrapper />
        </Provider>

    );
}

export default Toast;
