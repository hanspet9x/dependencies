const net = require('net');

//event reg = {event: "name", data: "data for callback"};
class ServerSocket{

    #events = new Map();
    onData = null; //this.onData(data, socket, true); false = not json
    onBind = null;
    onError = null;
    backLog = 20;
    cleanDataUTF = false;
    static OPENING = "opening";
    static WRITE_ONLY = "writeOnly";
    static READ_ONLY = "readOnly";
    static OPEN = "open";

    constructor(port = 0, hostName = "localhost", backLog = 20){
        this.hostName = hostName;
        this.port = port;
        this.registered = new Map();
        this.sockets = [];
        this.backLog = backLog;
    }

    
    #initSocket = (socket) => {

        socket.setKeepAlive(true);

        socket.ref();

        this.sockets.push(socket);

        console.info("initSocket");

        this.#registerEvents(socket);

        socket.on("error", (e)=> {   
            console.error("socket error", e.message);
            this.onError !== null && this.onError(e);
        });
        
        socket.on("id", ({data}) => this.#registerSocket(data.data, socket));

        socket.on("data", (data) => {
            this.#socketData(data, socket);
        });

        socket.on("end", () => {
            console.log("ended..")
        });

        socket.on("close", (hasError) => {
            this.#errorOccured(socket);
        });
       
        if(this.onBind != null) this.onBind(socket);
    }

    #errorOccured = (socket) => {
     
        console.error("socket closed..removing socket");
        if(socket.id !== undefined){
            this.#unRegisterSocket(socket.id);    
        }

        this.#removeSocket(socket);
    }

    addEvent = (eventName, callback) => {
        this.on(eventName, callback);
    }

    #registerEvents = (socket) => { 
        this.#events.size > 0 && this.#events.forEach((callbacks, eventName) => {
            socket.on(eventName, callbacks);
        });
    }

    #registerSocket = (id, socket) => {
       
        if(!this.registered.has(id)){
            console.log("registering socket "+id);
            socket['id'] = id;
            this.registered.set(id, socket);
        }else{
            socket.write({error: "Id is not unique."});
        }
    };

    #unRegisterSocket = (id) => this.registered.delete(id);

    #removeSocket = (socket) => {
        for(var i = 0; i < this.sockets.length; i++){
            if(this.sockets[i] === socket){
                this.sockets.splice(i, 1);
                break;
            }
        }
    }
    
    #socketData = (data, socket)=> {
            data = this.cleanDataUTF ? this.cleanData(data): data;
            try {
                let json = JSON.parse(data);
                this.#emitEvents(json, socket);
                if(this.onData !== null) this.onData(data, socket, true);

            } catch (e) {
                if(this.onData !== null) this.onData(data, socket, false);
            }
        
    };

    cleanData = (data) => {
       
        return data.toString("utf8", 2);
    }

    #emitEvents = (json, socket) => {
        if(json.event !== undefined){
            if(socket.eventNames().includes(json.event)){
                if(json.error !== undefined && json.error+"".trim().length > 0){
                    socket.emit('error', new Error(json.error));
                }else{
                    socket.emit(json.event, {socket: socket, data: json});
                }
            }    
        }
    }

    emit = ({eventName, eventValue}, socketId) => {
        if(this.registered.has(socketId)){
            let socket = this.registered.get(socketId);
            socket.eventNames(eventName) && socket.emit(eventName, eventValue);
        }
    }

    write = (socketId, data) => {
        
       let socket = this.getSocket(socketId);
       if(socket === null) return false;
       socket && socket.write(data);
        return true;
    }

    writeStream = (socket, data=[])=>{
        if(typeof socket === "string"){
            socket = this.getSocket(socketId);
            if(socket == null) return null;
        }
        
        data.forEach(socket.write);
        
    }

    setCleanDataUTF = (clean) => {
        this.cleanDataUTF = clean;
    }

    broadcastWrite = (data) => {

        this.sockets.forEach(socket => this.isSocketOpened(socket) && socket.write(data));
    }

    broadcast = (eventName, eventValue) => {
        this.sockets.forEach( socket => {
            socket.eventNames(eventName) && socket.emit(eventName, eventValue);
        });
    }

    getSocket = (socketId) => {
        if(this.registered.has(socketId)){
            let socket = this.registered.get(socketId);
            return this.isSocketOpened(socket) ? socket : null;
        }
        return null;
    }

    hasSocket = (socketId) => {
        return this.registered.has(socketId);
    }

    isSocketOpened = (socket) => {
        return socket.readyState === ServerSocket.OPEN ? true : false;
    }

    closeSocket = (socket) => {
        socket.destroy();
    }

    on = (eventName, callback)=>{
        this.#events.set(eventName, callback);
        this.#addEventsToSockets(eventName, callback);
    }

    #addEventsToSockets = (eventName, callback)=> {
        this.sockets.forEach(socket => {
            socket.on(eventName, callback);
        });
    }

    listen = (callback = null) => {
      
        this.server = new net.Server(this.#initSocket);
        this.server.timeout = 0;
        this.server.on("error", (e) => {
            console.error("server", e.message);
            if(this.onError !== null) this.onError(e);
        });
        this.server.listen(this.port, this.hostName, this.backLog, callback);
       
    }

}

module.exports = {
    ServerSocket
}