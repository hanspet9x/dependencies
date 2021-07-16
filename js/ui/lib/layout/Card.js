import React from "react";
import { HP } from "./../../utils/HP";

export default function Card({ style, children, center, onClick = null}) {
  let custom = {
    position: "relative",
    borderRadius: 7,
    padding: 10,
    margin: 5,
    boxShadow: "0px 1px 1px rgba(10, 10, 10, .2)",
  };

  if(center !== undefined){
    let centerStyle = {
      alignItems: 'center',
      justifyContent: 'center',
      display: 'flex',
    }

    custom = HP.combineStyles(custom, centerStyle);
  }
  
  const handleClick = ()=>{
    if(onClick !== null) onClick();

  }
  return <div onClick={handleClick} style={HP.combineStyles(custom, style)}>{children}</div>;
}
