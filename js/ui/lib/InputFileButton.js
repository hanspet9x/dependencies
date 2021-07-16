import React, { useState } from 'react'
import { JContent } from '../services/Jpc';

function InputFileButton({onChange, name, value, accept, className, src, label, showPath = true}) {

    const [file, setFile] = useState("");

    return (
        <div className={"jpc input-file-button " + className}>
            <input type="file" name={name || "file"} value={value || file} accept={accept} id="file" onChange={(e) => {setFile(e.target.value); onChange(e); }} />
            <label htmlFor="file" >{src} {label} </label>
            <span>{showPath? JContent.extract(file, 12, file.length-1) : null}</span>
        </div>
    )
}

export default InputFileButton;