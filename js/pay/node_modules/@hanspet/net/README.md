# Net
A library that contains APIs for maintaing sockets, creating server sent events, used for sending requests to the client and receiving responses from the server, see progress of uploading and downloading data, create event source, receive server sent events, suitable for PWAs, and helps create url queries.

## Installation

> $ npm i --save @hanspet/net


## Client
It has static methods that invokes the function passed as an argument upon a response.

1. GET Methods include:

   - getJSON
   - getText
   - getBoolean
   - getBlob
   - getArrayBuffer

2. POST Methods include:

   - postObject
   - postJSON
   - postForm
   - postFormData

3. Progress Methods

   - syncDownloadData
   - syncUploadData

4. EventSource Method.
   -sse

### GET methods structure.
All Get methods accept a url, search data, callback and an optional cache object with NO_CACHE as the default.
```javascript
  /**
   * It sends an object or query and receives JSON as response.
   * Object param must follow { key : "value"} structure and query should be key=value.
   * Callback param must be a function. It takes resonse as the first argument and error as the second argument.
   *Cache is optional and can either be any of Cache.CACHE, Cache.NO_CACHE or Cache.IF_CACHED

   * @param {String} url
   * @param {Object | String} searchData
   * @param {Function} callback
   * @param {Cache} Cache
   */
Client.getJSON(url, searchData, (data, error) => {}, cache);
```

### POST methods structure.

#### 1. post json.
```javascript
  /**
   * JSON data is '{"key": "value"}'
   * @param {String} url
   * @param {String} json
   * @param {Function} callback
   * @param {ResponseType} responseType
   */
    Client.postJSON(url, json, (data, error) => {}, responseType)
```

#### 2. post Object.
All post methods accept a url, data, callback and an optional ResponseType object with Json as the default.
```javascript
  /**
   *Object data is {key: "value"}

   * @param {String} url
   * @param {String} object
   * @param {Function} callback
   * @param {ResponseType} responseType
   */
    Client.postObject(url, object, (data, error) => {}, responseType)
```
#### 3. post form.
```javascript
  /**
   * @param {String} url
   * @param {HTMLFormElement} form
   * @param {Function} callback
   * @param {ResponseType} responseType
   */
    Client.postForm(url, form, (data, error) => {}, responseType)
```

#### 4. post form data.
```javascript
  /**
   * @param {String} url
   * @param {FormData} formData
   * @param {Function} callback
   * @param {ResponseType} responseType
   */
    Client.postFormData(url, formData, (data, error) => {}, responseType)
```

### Progress methods structure
```javascript
    Client.syncDownloadData(url, (data, percentDownloaded)=>{});

  /**
   * 
   * @param {String} url The endpoint.
   * @param {FormData} formData The instance of FormData object
   * @param {Function} callback Fires the Percentage of uploaded data.
   */
    Client.syncUploadData(url, formData, (percentUploaded)=>{})
```

### EventSource

```javascript
  /**
   * 
   * @param {String} url The request end-point.
   * @param {Array | String} evts The expected events from the server. It could be a string of array or string. To use the default event pass a null.
   * @param {Function} onSuccess The success callback when a data is received from the server. It takes a second parameter of the event name if a custom event was specified.
   * @param {Function} onError The error callback is invoked whenever connection drops or an error occured and it takes the instance of the object and a boolean as the second argument to determine if stream is closed. 
   */
Client.sse(url, eventName, (data [, eventName])=>{}, (eventObject, isClosed)=>{})
```

### ResponseType
An optional object of strings used as the last argument for the post methods.

```javascript
const ResponseType = {
  Json: "json",
  Boolean: "boolean",
  Text: "text",
  Number: "number",
  Blob: "blob",
  ArrayBuffer: "arraybuffer",
};
```

### Cache
An optional object of strings used as the last argument for the get methods. 
```javascript
const Cache = {
  CACHE: "force-cache",
  NO_CACHE: "no-cache",
  IF_CACHED: "only-if-cached"
}
```

## fetchStore

 It's a PWA API that provides previously stored data in an IndexedDb as a response and updates stored data using the Client api.
 It takes the database name as the only arguemnt and returns all Client object methods.

 It awaits responses from the server before invoking its callback if no data was found in the DB.

 If data was found in the db, It serves it, sends a request to the server, compares the response from the server with the memorized data. It replaces the old data with the new data and serves the new data.


 ```javascript
 const {get, getText, getJson, getBlob, getNumber, getArrayBuffer, getBoolean, store} = fetchStore(databaseName);
```

 It also return a store object that gets stored data without refreshing data using the Client api and set data into the DB.

## FormQuery
An API used for creating a query.

1. Without the ? character
```javascript
let query = new FormQuery()
        .append("key", "value")
        .append("key2", "value2")
        .build();
console.log(query) //key=value&key2=value2
```

1. With the ? character
```javascript
let query = new FormQuery()
        .append("key", "value")
        .append("key2", "value2")
        .build(true);
console.log(query) //?key=value&key2=value2

```
