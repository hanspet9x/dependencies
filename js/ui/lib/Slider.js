import React, { useRef, useEffect, useState } from 'react';
import './Slider.css';

function Slider({children = []}) {

    const sliderRef = useRef();
    let active = 0;
    const defClass = "slider-tile";
    const actvClass = "slider-tiles active";


    const defBClass = "slider-ball";
    const actvBClass = "slider-ball active";


    const [sliderBalls, setSliderBalls] = useState([]);

    useEffect(()=>{
       
        let count = sliderRef.current.childElementCount;
        let data = [];
        for (let i = 0; i < count-1; i++) {
            data.push(i);
            
        }
        console.log(data);
        setSliderBalls(data);
       
    },[]);


    const handleSlider = (e)=>{
        let slider = sliderRef.current;
        if(e.target.id === "slider-prev"){
            prev(slider);
        }else{
            next(slider);
        }
    }

    const next = (slider)=>{
        
        let init = active;
        let num = (active >= slider.childElementCount -2) ? 0 : ++active;     
        if(num){             
                show(slider, num, init);
                active = num;
        }
    }

    const prev = (slider)=>{
        let init = active;
        let num = (active <= 0) ? -1 : --active; 

        if(num !== -1){
            show(slider, num, init);    
        }
    }


    const show=(slider, num, init)=>{
        slider.children[init].className = defClass;
        slider.lastChild.children[init].className = defBClass;
        // setTimeout(()=>{
            slider.children[num].className = actvClass;
            slider.lastChild.children[num].className = actvBClass;
        // },100);
        active = num;
    }

    return (
        <div className="slider-container">

            <div className="slider-nav" id="slider-prev" onClick={handleSlider}>‹</div>

                <ul ref={sliderRef} className="slider-wrapper">

                    {children.map((e, i) => {
                        return i === 0 ? <li key={i} className={actvClass}>{e}</li> : <li key={i} className={defClass}>{e}</li>;
                    })}

                    <div className="slider-balls-wrapper">{sliderBalls.map((e, i) => {
                       return i === 0 ? <span key={i} className={actvBClass}></span> : (<span key={i} className={defBClass}></span>);
                    })}</div>

                </ul>

            <div className="slider-nav" id="slider-next"  onClick={handleSlider}>›</div>

        </div>
    );
}

export function SliderTile({children}) {
    return(
        <React.Fragment>
        {children}
        </React.Fragment>

    );
}
export default Slider;
