import React, { useRef, useState } from 'react';
import './Scroller.css';

function Scroller({children}) {

    const scrollerRef = useRef();
    const [indicator, setIndicator] = useState(0);
    let start = 0;
    let end = 0;
    let slide = 0;


    const handleStart = (e) => {
        start = e.touches[0].clientX;
    }

    
    const handleMove= (e) => {
        end = e.touches[0].clientX;
    }
    

    const handleEnd = (e) => {

        if(start > end){
            
           if(children.length > parseInt(e.currentTarget.id)){
            e.currentTarget.parentNode.scroll(left(e.currentTarget, 30));
            if(indicator < children.length-1) setIndicator(parseInt(e.currentTarget.id));
           }
        

        }else{

            e.currentTarget.parentNode.scroll(right(e.currentTarget, 30));
            if(indicator - 1 >= 0)setIndicator(indicator-1);
        
        }
    }

    const moveLeft = () => {
        
        let e = scrollerRef.current.children[slide];
        scrollerRef.current.scroll(left(e, 0));
        if(slide  >= 0 && slide < (scrollerRef.current.childElementCount-3))++slide;
    }

    const moveRight = () => {
        let e = scrollerRef.current.children[slide];
        scrollerRef.current.scroll(right(e, 60));
        if(slide > 0)--slide;
    }

    const left = (e, n)=>{
        return {
            top:0,
            left: e.offsetWidth + e.offsetLeft + n,
            behavior: 'smooth'
        }
    }

    // console.log(children);

    const right = (e, n)=> {
        return {
            top:0,
            left: -(e.offsetWidth - e.offsetLeft) - n,
            behavior: 'smooth'
        }
    }

    return (

        <div className="scroller-wrapper">

            <div className="scroller" ref = {scrollerRef}>

                {children.map((e, i) => (

                    <div className="scroller-item"  key={i} id={i+1} onTouchEnd={handleEnd} onTouchStart={handleStart} onTouchMove = {handleMove}>{e}</div>

                ))}

            </div>
                {children.length > 3 ? <div className="scroller-item-navigators">
                <div onClick={moveLeft}>‹</div>
                <div onClick={moveRight}>›</div>
            </div> : ""}
                <ul className="scroller-indicators">{
                    children.map((e, i) => {
                        
                        return i === indicator ? <li className="active" key={i}></li> : <li key={i}></li>
                    })}
                </ul>
        </div>
        
    );
}

export const ScrollItem = ({children})=>{


    return (
        <React.Fragment>{children}</React.Fragment>
    )
}

export default Scroller;