import React from 'react';
import { useRef } from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { HP } from './../utils/HP';

const diff = 2;

function CheckBox({checked = false, name, value, onChange, color = '#a2a2ff', size=18, disabled=false}) {

    const [state, setState] = useState(false);

    
    const onToggle = () => {
        if(!disabled){
            setState((state) => !state);
        }
    }

    const loaded = useRef(false);
    const prevRef = useRef(null);

    useEffect(()=>{
        if(onChange !== undefined && loaded.current && prevRef.current !== state){
            let e = {
                target: {
                    name: name,
                    value: value !== undefined ? value : state,
                    checked: state
                }
            }

            onChange(e);
            prevRef.current = state;
        }

        
    })

    useEffect(() => {
        
        setState(checked);
        loaded.current = true;
        
    }, [checked]);

    let mainStyle = {width: size, height: size, border: `solid 1px ${color}`, opacity: disabled? .5: 1};
    let innerStyle = {width: size -diff, height: size -diff, backgroundColor: `${color}`, opacity: disabled? .5: 1};

    return (
        <div style={HP.combineStyles(styles.wrapper, mainStyle)} onClick={onToggle}>
            <div style={HP.combineStyles(styles.inner, state? styles.active : styles.inactive, innerStyle)}></div>
        </div>
    );
}

const styles = {
    wrapper: {
        width: 18,
        height: 18,
        border: 'solid 1px #a2a2ff',
        borderRadius: 5,
        display: 'inline-block',
        position: 'relative',
        cursor: 'pointer',
        boxShadow: '0px 1px 2px rgba(0,0,0,.2)',
    },

    inner: {
        width: 18-diff,
        height: 18-diff,
        backgroundColor: '#a2a2ff',
        borderRadius: 4,
        position: 'absolute',
        top: 1,
        left: 1,
        boxShadow: '0px -2px 2px rgba(0,0,0,0.4) inset',
        transition: '.2s cubic-bezier(0.6, -0.28, 0.735, 0.045)'
    },
    active: {
        opacity: 1,
        transform: 'scale(1)',
    },

    inactive: {
        opacity: 0,
        transform: 'scale(0)',
    }
}

export default CheckBox;