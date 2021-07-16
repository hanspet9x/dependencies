import React from 'react';
import { useRef } from 'react';
import { connect } from 'react-redux';

const Vertical = "vertical";
const Horizontal = "Horizontal";

const mapStateToProps = (state) => {
    return {
        win: state
    }
}
function Wrapper({ children, title, style, onViewIn, onViewBase, onView, onViewOut, onViewTotallyOut,  dir = Vertical, win = {wX: 0, wY: 0, wW: 0, wH: 0} }) {
    const offset = 20;

    const pageRef = useRef(null);

    const _onViewIn = (scrolled) => {
        if(onViewIn !== undefined)onViewIn(scrolled);
        console.log("onViewIn", title);
    }

    const _onViewBase = (scrolled) => {
        if(onViewBase !== undefined)onViewBase(scrolled);
    }

    const _onView = (scrolled) => {
        if(onView !== undefined)onView(scrolled);
    }

  
    const _onViewOut = (scrolled) => {
        if(onViewOut !== undefined)onViewOut(scrolled);
    }


    const _onViewTotallyOut = (scrolled) => {
        if(onViewTotallyOut !== undefined)onViewTotallyOut(scrolled);
    }

    const monitor = () => {
        let page = pageRef.current;
        if (page !== null) {
            if (dir === Vertical) {
                const top = parseInt(win.wY);
                const height = parseInt(win.wH);
                const bTop = parseInt(page.offsetTop);
                const bHeight = parseInt(page.offsetHeight);
                const wAdd = top + height;
                const bAdd = bTop + bHeight;

                
                if(wAdd >= bTop && wAdd < bTop+offset){
                    _onViewIn(bTop+offset - wAdd);
                }

                if(wAdd > bAdd && wAdd < bAdd + offset){
                    _onViewBase(bAdd + offset - wAdd);
                }

                if (wAdd >= bTop && wAdd <= bAdd + bHeight) {
                   _onView(bAdd + bHeight - wAdd);
                }

                if(top > bTop && top < bTop + offset){
                    _onViewOut(bTop + offset - top);
                }

                if(wAdd >= bAdd + bHeight && wAdd <  (bAdd + bHeight + offset)){
                    _onViewTotallyOut(bAdd + bHeight + offset - wAdd);
                }

            }else if(dir === Horizontal){
                const left = parseInt(win.wX);
                const width = parseInt(win.wW);
                const bLeft = parseInt(page.offsetLeft);
                const bWidth = parseInt(page.offsetWidth);
                const wAdd = left + width;
                const bAdd = bLeft + bWidth;

                
                if(wAdd >= bLeft && wAdd < bLeft+offset){
                    onViewIn();
                }

                if(wAdd > bAdd && wAdd < bAdd + offset){
                    onViewBase();
                }

                if (wAdd >= bLeft && wAdd <= bAdd + bWidth) {
                    onView();
                }

                if(left > bLeft && left < bLeft + offset){
                    onViewOut();
                }

                if(wAdd >= bAdd + bWidth && wAdd <  (bAdd + bWidth + offset)){
                    onViewTotallyOut();
                }
            }

        }

    }

    
    if(win.wH > 0 && win.wW){
        monitor();
    }

    return (
        <div ref={pageRef} style={style}>
            {children}
        </div>
    );
}


const EventBox = connect(mapStateToProps, null)(Wrapper);
export default EventBox;