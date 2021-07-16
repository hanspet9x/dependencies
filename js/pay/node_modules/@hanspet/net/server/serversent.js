class ServerSents{

	#stream = "";
	retryCount = 5000;
	onClose = null;
	timer = null;

	constructor(request, response){
		this.response = response;
		this.request = request;
		
		this.request.on("close", ()=>{
			// console.error("server sent request closed.");
			if(this.timer !== null) clearInterval(this.timer);
			if(this.onClose != null) {
				this.onClose();
			}
		});

		this.response.writeHead(200, this.getHeader());
	}

	event = (eventName)=> {
		this.eventName = eventName;
		this.#stream += `event: ${eventName}`;
		this.#newLine();
		return this;
	}


	data = (data) => {
		this.#stream  += `data: ${data}`;
		this.#newLine();
		return this;
	}

	id = (id) => {
		this.#stream  += `id: ${id}`;
		this.#newLine();
		return this;
	}

	comment = (comment) => {
		this.#stream  += `: ${comment}`;
		this.#newLine();
		return this;
	}

	build = ()=> {
		this.#newLine(true);
		this.response.write(this.#stream);
		this.#stream = "";
		return this;
	}

	sendData = (data) => {
		this.#stream = "";
		this.event(this.eventName);
		this.data(data);
		this.build();
	}

	sendStream = (func, retry = this.retryCount)=> {

		this.timer = setInterval(()=>{
			func();
		}, retry);
		return this;
	}

	getHeader = ()=> {
		return {'Content-Type': 'text/event-stream', 'Connection': 'keep-alive', 'Cache-Control': 'no-cache'}
	}

	#newLine = (write = false)=> {
		this.#stream  += write ? "\n\n" : "\n";
	}

	#isValid = (data) => {
		if(typeof data !== "string"){
			throw new Error("Invalid Data: Server sends just string");
		}
	}
}

module.exports = ServerSents;