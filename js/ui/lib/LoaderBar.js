import React, { useEffect } from 'react';
import { HP } from '../utils/HP';

function LoadingBar({show, style = {}}){

    useEffect(()=>{
        if(show === undefined){
            throw new Error("LoaderError: props.show is undefined.");
        }
    });

    return (
        
        <div className="loading-bar" style={HP.combineStyles({opacity: show===true || show === 1?1:0}, style)}>
            <div></div>
        </div>
    );
}

export default LoadingBar;