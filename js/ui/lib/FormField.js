import React, { useState } from 'react';

import PasswordField from './PasswordField';

function FormField({onChange, onBlur, label, id, className, name, type, data, placeholder, 
    readOnly, required, tag, children,defaultValue, value, inline}) {

    let defLabel = "form-field-label";
    let actvLabel = "form-field-label-active";
    
    const [labelStyle, setLabelStyle] = useState(defLabel);

    const handleChange =(e)=>{
        onChange(e);
    }


    const handleBlur =(e)=>{
        
        if(e.target.value.length === 0){
            setLabelStyle(defLabel);
        }

        if(onBlur !== undefined){
            onBlur(e);
        }
    }

    const handleFocus = (e) => {
        setLabelStyle(actvLabel);
    }

    const specInputs = () => {
        if(type === "date" || type === "time" || (value !== undefined && value !== null? value.trim().length > 0 ? true : false : false)){
            return true;
        }
        return false;
    }

    var mainTag = null;
    switch (tag) {
        case "i":
            mainTag = <input type={type} 
            id={id}
            className={className}
            placeholder={placeholder} 
            required={required} 
            name={name} 
            value={value}
            defaultValue={defaultValue}
            data-type={data}
            readOnly={readOnly}
            onFocus={handleFocus}
            onBlur = {handleBlur}
            onChange={handleChange} />
            break;
        case "s":
            mainTag = <select 
            required={required}
            id={id}
            className={className} 
            name={name} 
            value={value}
            defaultValue={defaultValue}
            data-type={data}
            readOnly={readOnly}
            onFocus={handleFocus}
            onBlur = {handleBlur}
            onChange={handleChange}>{children}</select>
            break;
        case "t":
            mainTag = <textarea 
            placeholder={placeholder} 
            id={id}
            className={className}
            required={required} 
            name={name} 
            value={value}
            defaultValue={defaultValue}
            data-type={data}
            readOnly={readOnly}
            onFocus={handleFocus}
            onBlur = {handleBlur}
            onChange={handleChange} />
            break;
        case "p":
            mainTag = <PasswordField 
            placeholder={placeholder} 
            id={id}
            className={className}
            required={required} 
            name={name}
            onBlur = {handleBlur}
            defaultValue={defaultValue}
            readOnly={readOnly}
            data-type={data}
            
            onChange={handleChange} />
            break;
        case "d":
            mainTag = <React.Fragment>
            <input type={type} 
                list={`${name}-datalist`}
                placeholder={placeholder} 
                id={id}
                className={className}
                required={required} 
                name={name} 
                data-type={data}
                value={value}
                onFocus={handleFocus}
                readOnly={readOnly}
                onBlur = {handleBlur}
                defaultValue={defaultValue}
                onChange={handleChange} />
            <datalist id={`${name}-datalist`}>
                {children}
            </datalist>
            
            </React.Fragment>
            break;
        default:
            mainTag = <input type={type} 
            placeholder={placeholder} 
            id={id}
            className={className}
            required={required} 
            name={name} 
            data-type={data}
            value={value}
            onFocus={handleFocus}
            readOnly={readOnly}
            onBlur = {handleBlur}
            defaultValue={defaultValue}
            onChange={handleChange} />
            
            break;
    }
    
    let form = <div className="form-label">
                    <label className={specInputs() === true ? actvLabel : labelStyle} onClick={handleFocus}>{label}</label>
                    {mainTag}
                </div>
    
    return inline? <div className="form-inline">{form}</div> : form 
    
}

export default FormField;