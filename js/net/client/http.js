
export default class Client {
  static CACHE = "force-cache";
  static NO_CACHE = "no-cache";
  static IF_CACHED = "only-if-cached";

  static JSON = "json";
  static TEXT = "text";
  static BOOLEAN = "boolean";
  static NUMBER = "number";

  method = "GET";
  mode = "cors";
  credentials = "same-origin";
  headers = { "Content-Type": "application/json; charset=utf-8" };
  url = "";
  callback = null;
  type = "text";
  body = null;
  cache = Client.IF_CACHED;

  /**
   * sends an object and receives JSON as response.
   * Object param must follow { key : "value"} structure and
   * Callback param must be a method. callback also takes error as an argument.
   *
   * @param {String} url
   * @param {Object | String} searchData - object or string query
   * @param {Function} callback
   */

  static getJSON(
    url,
    searchData,
    callback = null,
    useCache = Client.NO_CACHE
  ) {
    Client.setUpGET(url, searchData, "json", callback, useCache);
  }

  static getText(
    url,
    searchData,
    callback = null,
    useCache = Client.NO_CACHE
  ) {
    Client.setUpGET(url, searchData, "text", callback, useCache);
  }

  static getBoolean(
    url,
    searchData,
    callback = null,
    useCache = Client.NO_CACHE
  ) {
    Client.setUpGET(url, searchData, "boolean", callback, useCache);
  }

  static getNumber(
    url,
    searchData,
    callback = null,
    useCache = Client.NO_CACHE
  ) {
    Client.setUpGET(url, searchData, "number", callback, useCache);
  }

  static setUpGET(
    url,
    searchData,
    type,
    callback = null,
    useCache = Client.NO_CACHE
  ) {
    let http = new Client();

    if (callback === null) throw new Error("Client: callback is undefined.");



    if (typeof searchData === "object") {
      var get = "";
      http.url = url;

      if (Object.keys(searchData).length > 0) {
        for (const key in searchData) {
          get += `${key}=${searchData[key]}&`;
        }
        http.url = url + (get.length > 0 ? http.cleanGet(get) : "");
      }

    } else {
      http.url = url+ searchData.length > 3 ?"?" + searchData: "";
    }

    http.cache = useCache;
    http.callback = callback;
    http.type = type;
    http.fetchData(http.requestGet());
  }

  static getBlob(url, callback = null, useCache = Client.NO_CACHE) {
    let http = new Client();

    if (callback !== null) {
      http.url = url;
      http.callback = callback;
      this.headers = { "Content-Type": "image/*" };
      http.type = "blob";
      http.cache = useCache;
      http.fetchData(http.requestGet());
    } else {
      let error = "Client: callback is undefined.";
      http.callback({ error: true, status: error });
    }
  }

  static getArrayBuffer(url, callback = null, useCache = Client.NO_CACHE) {
    let http = new Client();

    if (callback !== null) {
      http.url = url;
      http.callback = callback;
      this.headers = { "Content-Type": "image/*" };
      http.type = "arraybuffer";
      http.cache = useCache;
      http.fetchData(http.requestGet());
    } else {
      let error = "Client: callback is undefined.";
      http.callback({ error: true, status: error });
    }
  }

  requestGet = () => {
    return new Request(this.url, {
      method: this.method,
      cache: this.cache,
      headers: { "Cache-Control": "max-age=5" },
      mode: this.cors,
      credentials: this.credentials,
    });
  };

  /**
   *
   * @param {String} url
   * @param {Object} searchData
   * @param {Function} callback
   */
  static postObject(url, searchData, callback = null, response = "json") {
    let http = new Client();

    if (callback !== null) {
      if (typeof searchData === "object") {
        try {
          http.body = JSON.stringify(searchData);
          http.url = url;
          http.callback = callback;
          http.headers = { "Content-Type": "application/json" };
          http.type = response;
          http.setMethod("POST");

          http.fetchData(http.requestPost());
        } catch (error) {
          http.callback({ error: true, status: error });
        }
      } else {
        let error =
          'Client: getJSON parameter 2 not an object. consider { key : "value" }';
        http.callback({ error: true, status: error });
      }
    } else {
      let error = "Client: callback is undefined.";
      http.callback({ error: true, status: error });
    }
  }

  static postJSON(url, json, callback = null, response = "json") {
    let http = new Client();

    if (callback === null)
      throw new Error("Client: callback is undefined.");

    if (typeof json === "string") {
      try {
        JSON.parse(json);
        http.body = json;
        http.url = url;
        http.callback = callback;
        http.headers = { "Content-Type": "application/json" };
        http.type = response;
        http.setMethod("POST");

        http.fetchData(http.requestPost());
      } catch (error) {
        http.callback({ error: true, status: error });
      }
    } else {
      let error =
        'Client: postJSON parameter 2 not a string. consider { "key" : "value" }';
      http.callback({ error: true, status: error });
    }
  }

  /**
   * It accepts HTMLFormElement ID as searchData and returns json.
   * Response can either be json or text. It assumes json as default if not specified.
   * @param {String} url
   * @param {String} searchData
   * @param {Function} callback
   * @param {String} response
   */

  static postForm(url, FormElement, callback = null, response = "json") {
    Client.setUpPost(url, new FormData(FormElement), callback, response);
  }

  static postFormData(url, FormData, callback, response = "json") {
    Client.setUpPost(url, FormData, callback, response);
  }

  static setUpPost(url, formData, callback = null, response = "json") {
    let http = new Client();

    if (callback === null)
      throw new Error("Client: callback is undefined.");

    if (typeof formData === "object") {
      try {
        http.setMethod("POST");
        http.body = formData;
        http.url = url;
        http.callback = callback;
        http.type = response;
        http.fetchData(http.requestPostForm());
      } catch (error) {
        callback({ error: true, status: error });
      }
    } else {
      let error =
        "Client: getJSON parameter 2 not a object. parse form target.";
      callback({ error: true, status: error });
    }
  }

  static syncDownloadData = (url, callback) => {
    let xml = new XMLHttpRequest();
    let calc = 0;
    xml.open("GET", url, true);
    xml.responseType = "blob";

    xml.onload = (e) => {
      console.log("downloaded");
      callback(xml.response, 100);
    };

    xml.onerror = (e) => {
      console.log(e);
    };
    xml.onprogress = (e) => {
      console.log(xml.responseURL);
      console.log("progress");
      calc = Math.floor(e.loaded / e.total) * 100;
      callback(xml.response, calc);
    };

    xml.send();
  };

  /**
   * 
   * @param {String} url The endpoint.
   * @param {FormData} formData The instance of FormData object
   * @param {Function} callback Fires the Percentage of uploaded data.
   */
  static syncUploadData = (url, formData, callback) => {
    let xml = new XMLHttpRequest();
    let calc = 0;
    xml.open("POST", url, true);

    xml.upload.onload = (e) => {
      console.log("uploaded");
      callback(100);
    };

    xml.onerror = (e) => {
      console.error("uploading error.", e);
    };
    xml.upload.onprogress = (e) => {
      calc = Math.floor(e.loaded / e.total) * 100;
      callback(calc);
    };

    xml.send(formData);
  };

  /**
   * 
   * @param {String} url The request end-point.
   * @param {String} evts The expecrted events from the server. It could be a string of array or string. To use the default event pass a null.
   * @param {Function} onSuccess The success callback when a data is received from the server. It uses a second parameter of the event name if a custom event was specified.
   * @param {Function} onError The error callback invoked whenever connection drops or an error occured and it takes the instance of the object and a bool arg to determine if stream is closed. 
   */
  static sse = (url, evts = [], onSuccess, onError) => {

    const evt = new EventSource(url);

    evt.addEventListener("open", () => {

      if (evts.length) {

        if (Array.isArray(evts)) {
          evts.forEach(name => {
            evt.addEventListener(name, e => onSuccess(e.data, name, evt));
          });
        } else {
          evt.addEventListener(evts, e => onSuccess(e.data, evts, evt));
        }
      } else {
        evt.onmessage = e => {
          onSuccess(e.data, evt);
        };
      }
    });

    evt.onerror = e => {
      if (onError !== undefined) onError(evt, evt.CLOSED === e.target.readyState);
    }

    return evt;
  }

  objToForm = (obj) => {
    if (typeof obj === "object") {
      let form = new FormData();

      for (const key in obj) {
        form.append(key, obj[key]);
      }
      return form;
    }
    throw new Error(
      "Client Error: Invalid data argument, object required."
    );
  };

  requestPost = () => {
    let request = new Request(this.url, {
      method: this.method,
      body: this.body,
      headers: this.headers,
      mode: this.cors,
      credentials: this.credentials,
    });
    return request;
  };

  requestPostForm = () => {
    return new Request(this.url, {
      method: this.method,
      body: this.body,
      mode: this.cors,
      credentials: this.credentials,
    });
  };

  fetchData = (request) => {
    // console.log(request);
    fetch(request)
      .then((response) => {
        if (response.ok) {
          return this.getResponseByType(response);
        }
        this.callback(null, response.statusText);
      })
      .then((data) => {
        // console.log(data);
        if (this.type === "json") {
          if (data.error !== undefined && data.error !== null) {
            // console.log(data);
            this.callback(data, data.error);
          } else {
            // console.log(data);
            this.callback(data, false);
          }

        } else if (this.type === "boolean") {
          this.callback(data === "true" ? true : false, false);
        } else if (this.type === "number") {
          this.callback(parseInt(data), false);
        } else {
          this.callback(data, false);
        }
      }).catch((err) => {
        this.callback(null, err.message);
      });
  };

  getResponseByType(response) {
    if (this.type === "json") {
      return response.json();
    } else if (this.type === "text") {
      return response.text();
    } else if (this.type === "boolean") {
      return response.text();
    } else if (this.type === "number") {
      return response.text();
    } else if (this.type === "blob") {
      return response.blob();
    } else if (this.type === "arraybuffer") {
      return response.arrayBuffer();
    }
  }

  setType(type) {
    this.type = type;
  }

  /**
   * method: String = POST|GET|PUT|DELETE
   */

  setMethod = (method) => {
    this.method = method.toUpperCase();
  };

  setCORS = (cors) => {
    this.cors = cors;
  };

  setBody = (body) => {
    this.body = body;
  };

  cleanGet = (data) => {
    return `${data.substring(0, data.length - 1)}`;
  };
}


/**
 * Http result response.
 */
export const ResponseType = {
  Json: "json",
  Boolean: "boolean",
  Text: "text",
  Number: "number",
  Blob: "blob",
  ArrayBuffer: "arraybuffer",
};

export const Cache = {
  CACHE: "force-cache",
  NO_CACHE: "no-cache",
  IF_CACHED: "only-if-cached"
}