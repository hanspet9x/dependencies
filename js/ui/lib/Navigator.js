import React, { useEffect, useMemo } from 'react';
import { connect, Provider } from 'react-redux';
import { createStore } from 'redux';

const mainState = {
    activeName: null,
    components: {}
};


const store = createStore((state = mainState, action) => {
    return { ...state, ...action.type };
});


export const navigate = (routeName) => {

    store.dispatch({type: {activeName: routeName}});
}


export const Route = ({ component, name }) => {
    return {name: name, component: component };
}

const Wrapper = ({component}) => {

    return component;
}

const stateProp = (state) => {

    return {

        component: state.activeName !== null ? state.components[state.activeName] : ""

    }
}

let NavigatorWrpapper = connect(stateProp, null)(Wrapper);


const Navigator = ({ children, active }) => {

    const allRoutes = useMemo(() => {

        let allRoutesObj = {};

        let firstName = "";

        children.forEach((e, i) => {
          
            let key = e.props.name;

            if(i === 0) firstName = key;

            allRoutesObj[key] = e.props.component;

        });

        return [firstName, allRoutesObj];

    }, [children]);


   useEffect(() => {
        const {activeName} = store.getState();
        if (activeName === null) {
            //use the navigator active if defined
            //else use the first index; 
            if(active !== undefined){
                if(allRoutes[1].hasOwnProperty(active)){
                    store.dispatch({type: {activeName: active, components: allRoutes[1]}})
                }
            }else{
                //pick the first and select it.
                store.dispatch({type: {activeName: allRoutes[0], components: allRoutes[1]}})
            }
        } else {
            //updated state detected.
        }
    }); 

    return (
        <Provider store={store}>
            <NavigatorWrpapper />
        </Provider>
    );
}

export default Navigator;