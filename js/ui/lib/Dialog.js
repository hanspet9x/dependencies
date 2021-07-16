import React from 'react';
import { connect } from 'react-redux';
import { Provider } from 'react-redux';
import { createStore } from 'redux';


export const DIALOG_ALERT = "dig_A";
export const DIALOG_CONFIRM = "dig_C";
export const DIALOG_HIDE = "dig_H";

const getDialogAction = (type, message, callback) => {
    return {type: type, message: message, callback: callback}
}

let dispatcher = null;

export const alert = (message, callback = null)=>{
    dispatcher(getDialogAction(DIALOG_ALERT, message, callback));
}

export const confirm=(message, callback = null)=>{
    dispatcher(getDialogAction(DIALOG_CONFIRM, message, callback));
}

const mapStateToProps = (state)=>{
    return {
        type : state.dialog.type,
        message: state.dialog.message,
        confirmAction: state.dialog.callback
    }
}
    
const mapDispatchToProps = (dispatch)=> {
    dispatcher = dispatch;
    return {
        hide: ()=>dispatch(getDialogAction(DIALOG_HIDE, "", null)),
    }
}

const DialogAlert = ({message, closeDialogue, onHide})=> {

    
    const closeDialogue = ()=>{
        onHide();
    }

    
        return (
            <ul id="HPalert">
                <li>Alert</li>
                <li>{message}</li>
                <li><button onClick={closeDialogue}>ok</button></li>
            </ul>
        );
    
}

const DialogConfirm = ({onHide, onConfirm, message, confirm, hide}) => {


    const hide = ()=>{
       onHide();
    }

    const confirm = ()=>{
        onConfirm();
    }


        return (
            <ul id="HPalert">
                <li>Confirm</li>
                <li>{message}</li>
                <li>
                    <button onClick={confirm}>ok</button>
                    <button onClick={hide}>cancel</button>
                </li>
            </ul>
        );
}

/**
 * The wrapper for dialog.
 * @param {*} props
 * @returns view
 */
const Wrapper = ({hide, confirmAction, type, message}) =>{

    var boxToShow;

    const onHideAlert = () =>{
        hide();
        if(confirmAction != null){
            confirmAction(false);
        }
    }

    const onHideConfirm = () => {
        hide();
        if(confirmAction != null){
            confirmAction(true);
        }
    }


    switch (type) {
        case DIALOG_ALERT:
            boxToShow = <div id="HPalrtConfm">
                            <DialogAlert message={message} onHide={onHideAlert} />
                        </div>
            break;
        case DIALOG_CONFIRM:
            boxToShow = <div id="HPalrtConfm">
                            <DialogConfirm 
                            message={message} 
                            onHide={onHideAlert} 
                            onConfirm={onHideConfirm} />
                        </div>
            break;
        default:
            boxToShow = ""
            break;
    }
    return boxToShow
                

}


const DialogWrapper = connect(mapStateToProps, mapDispatchToProps)(Wrapper); 



const mainState = {
 
    dialog: { 
      type: DIALOG_HIDE, 
      message: "", 
      callback: null 
    }
      
  };
  
const mainReducer = (state = mainState, action) => {
    switch(action.type){
      case DIALOG_ALERT:
      case DIALOG_CONFIRM:
      case DIALOG_HIDE:
          return Object.assign({}, state, {dialog: action});
                
      default: 
          return state;
    }
  };

const store = createStore(mainReducer);

const Dialog = ({children}) => {

    return(
        <Provider store = {store}>
            {children}
            <DialogWrapper />
        </Provider>
    );
}
export default Dialog;