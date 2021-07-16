import React from 'react';

function TextRule({text, type, className, children}) {
    
    return(
        <i className="text-rule">
            <i></i>
            <i className={className}>{text === undefined?children:text}</i>
            <i></i>
        </i>
    );
}

export default TextRule;