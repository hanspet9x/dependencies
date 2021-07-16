

class Server{


    constructor(){
        this.http = require('http');
        // this.http.
    }

    static get( url, callback){

    }

    static post(url, callback){

    }
}


const http = require('http');

http.get('https://www.google.com',  (res) => {
    res.on('data', (data) => {
        console.log(data);
        console.log("reading.");
    })
});

