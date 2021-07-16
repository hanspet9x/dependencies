import React, {useState} from 'react';


function FormSwitch({name, values = ["yes", "no"], onChange, value, titles=[]}) {
    
    const defaultStyle = {backgroundColor: "#eef0ee", left: "5px"};
    const activeStyle = {backgroundColor: "#ddffdd", left: "40px"};


    let defState = (value !== undefined && value === values[0])? true : false;
    
    const [state, setState] = useState(defState);

    const onSwitch =()=>{
        
        setState(!state);
        let e = {
            target:{
                name: name,
                value: state?values[0]:values[1]
            }
        }
        onChange(e);
        
    }

    let title = state? values[0] : values[1];

    if(titles !== undefined){
        title = state? titles[0] : titles[1];
    }

    return( 
        <React.Fragment>
                <div className="HPradio" style={{backgroundColor: state?activeStyle.backgroundColor: defaultStyle.backgroundColor}}>
                    <div onClick={onSwitch} style={{left: state?activeStyle.left : defaultStyle.left}}></div>
                </div>
                <span className="HpradioText">{title}</span>
                <input type="hidden" name={name} value={state} />
        </React.Fragment>
    );
}
export default FormSwitch;