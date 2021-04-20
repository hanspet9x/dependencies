const net = require('net');

const server = new net.Server( socket => {
    
    

    socket.on("data",  (data)=>{
        console.log(data.toString("utf8"));
        socket.write(data);
    });

    socket.on("error", (e) => {
        console.log(e.message);
    })

});

server.listen(3000, "localhost", 5, ()=>{
    console.log("listening..");
})