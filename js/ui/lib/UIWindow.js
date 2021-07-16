import React from 'react';
import './UIWindow.css';

function UIWindow({onClosed, onShow = false, className ="", children, title, style}){
    let defStyle = "ui-window-hide";
    let actStyle = "ui-window-show";

    return(
        <div className={`${className} ${onShow? actStyle : defStyle} ui-window`} style={style}>
            <div className="ui-window-close-wrapper">
                <h3>{title}</h3>
                <span onClick={onClosed.bind(null, "x")}>&times;</span>
            </div>
            <div className="ui-window-body">{children}</div>
        </div>
    );
}

export default UIWindow;