import React from 'react';
import { useEffect } from 'react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

const initalState = {
    wX: 0, wY: 0, wW: 0, wH: 0
}
const store = createStore((state = initalState, action)=>{
    return {...state, ...action.data};
});

function EventBoxContainer({children}) {

    
    
    useEffect(() => {
        
        window.onscroll = () => {
            store.dispatch({
                 data: {
                    wX: window.pageXOffset,
                    wY: window.pageYOffset, 
                    wW: window.innerWidth, 
                    wH: window.innerHeight,
                 },
                 type: "win"
                });
        }
    })
    return (
        <Provider store={store}>
            {children};
        </Provider>
    );
}

export default EventBoxContainer;