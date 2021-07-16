import React, { useState, useRef } from 'react';
import './LinksHolder.css';
import { useEffect } from 'react';
import { useReducer } from 'react';

const reducer = (state, action) => {

    return { ...state, ...action };
}

function LinksHolder({ options = [], values = [], onChange, name }) {

    // required for listing prop.listKey and prop.list = [{listKey}]
    const [search, setSearch] = useState("");

    const [state, dispatch] = useReducer(reducer, { data: [] });

    const inputRef = useRef(null);
    const prevRef = useRef();


    var handleKeyChange = e => {

        if (e.keyCode === 32 || e.which === 32) {
            dispatch({ data: state.data.concat(search) })
            e.target.value = "";
            if (onChange) {
                let obj = {
                    target: {
                        name: name,
                        value: state.data.join(",")
                    }
                }
                onChange(obj);
            }
        }

        if (e.keyCode === 8 || e.which === 8) {
            if (state.data.length > 0) {
                state.data.pop();
                dispatch({ data: state.data })
            }
        }
    }


    var handleInputChange = e => {
        setSearch(e.target.value);
    }

    var removeData = e => {
        var evt = e.target;
        if (!e.target.id) {
            evt = e.target.parentNode;
        }
        const i = state.data.indexOf(evt.id);
        
        dispatch({data: state.data.splice(i, 1)});
    }

    useEffect(() => {
        let sValues = values.join();

        if (sValues !== prevRef.current) {
            console.log(sValues, prevRef.current);
            dispatch({ data: values });
            if (onChange) {
                let obj = {
                    target: {
                        name: name,
                        value: values.join(",")
                    }
                }
                onChange(obj);
            }
        }
        prevRef.current = sValues;
    });

    const handleFocus = () => {
        inputRef.current.focus();
        //    <input type="hidden" name={props.name} value={data.join(',')} ref={dataRef} />
    }
    return (
        <div onClick={handleFocus}>
            <div className="links-holder">
                {state.data.map((data, i) => (
                    <div className="links" key={i} id={data} onClick={removeData}>
                        <span>{data}</span>
                        <span className="close"></span>
                    </div>
                ))}
                <input list="options" onKeyDown={handleKeyChange}
                    onChange={handleInputChange} className="link-search"
                    defaultValue={search}
                    ref={inputRef}
                    style={{ width: "100px", border: "none", marginBottom: "0px", outline: 'none', backgroundColor: 'unset' }}
                    placeholder="Type and Space.." />
            </div>

            <datalist id="options" >
                {options.map((list, i) => {
                    return <option key={i} value={list}>{list}</option>
                })}
            </datalist>
        </div>
    );
}


export default LinksHolder;