import React from 'react';

function FormRadio({label, name, onChange, value, first, last}) {
    let addCN = first !== undefined ? "form-radio-first" : "";
    addCN = last !== undefined && first === "undefined"?"form-radio-last":"";
    return (
        <div className={`form-radio ${addCN}`}>
            <label>{label}</label>
            <input type="radio" value={value} name={name} onChange={onChange} />
        </div>
    );
}

export default FormRadio;