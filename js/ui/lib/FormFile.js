import React, { useRef } from 'react';
import { HP } from '../utils/HP';
// import {BsFilePlus} from 'react-icons/bs'

function FormFile({onChange, label, id, className, labelStyle, style, name,
   required, inline, multiple = false}) {

        const fileCountRef = useRef(0);
        const inputRef = useRef();

        const handleChange = ({target}) => {
            const files = target.files;
            const data= {
                target:{
                    name: name,
                    value: multiple ? files : files[0]
                }
            }
            fileCountRef.current.textContent = files.length;
            onChange(data);
            
        }

    const onSelectFile = ()=> {
        inputRef.current.click();
    }


    return (
        <div className={inline ? "form-inline": ""} style={style}>
            <label htmlFor="upload" className={className} style={HP.combineStyles(labelStyle, mainStyle.button)} id={id}>{label ? label : "Choose File(s)"} 
             <span style={mainStyle.count} ref={fileCountRef} onClick={onSelectFile}> 0 </span>
             {/* <BsFilePlus style={mainStyle.choose} /> */}
            </label>
            <input id="upload" required={required} ref={inputRef} type="file" name={name} onChange={handleChange} multiple={multiple} style={{display: "none"}}/>
        </div>
    );
}

const mainStyle = {
    button: {
        backgroundColor: "#fefefe",
        borderRadius: '4px',
        padding: '12px 8px',
        display: 'inline',
        fontWeight: 'normal',
    },

    count: {
        backgroundColor: "#dedede",
        padding: '2px 4px',
        borderRadius: '4px',
        marginLeft: '4px'
    },

    choose: {
    
        marginLeft: '8px'
    }
}

export default FormFile;