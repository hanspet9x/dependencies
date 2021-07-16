# JS-API

A helper methods and an extension to JS arrays, objects, IndexedDb, LocalStorage and SessionStorage , etc...

## Installation

> npm i --save @hanspet/js-api

The library helper methods for:

1. Arrays
2. Objects
3. List of Values
4. I/O
5. Core

## I / O

It contains a wrapper for both IndexedDb and the client local storage and session.

1. IndexedDB Wrapper

```javascript
import { IdbStore } from "@hanspet/js-api";

const idb = new IdbStore(databaseName);

idb.set(key, data, callback);
//callback is called with true or false.

idb.get(key, callback);
//also returns a promise with the value if callback was not specified.

idb.has(key, callback);
//callback is called with either true or false.
```

2. Local Storage and Session Storage Wrapper

```javascript
import { Store } from "@hanspet/js-api";
/**
 *
 * @param {String} key - A unique key to store data.
 * @param {String} value - the Value to be stored
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */
Store.set(key, value, storageType);

/**
 * It returns the stored data using the key and the storage type.
 * If the value was added to by using the Store.append, it returns
 * an array.
 * @param {String} key - A unique key to store data.
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */
Store.get(key, storageType);

/**
 * It returns true if the key exists in the storage type.
 * @param {String} key - A unique key to store data.
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */
Store.has(key, type);

/**
 *
 * @param {String} key - A unique key to store data.
 * @param {Object} value - the Value to be stored
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */
Store.setObject(key, object, type);

/**
 * It returns the object saved with Store.setObject using the key and the storage type.
 * @param {String} key - A unique key to store data.
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */

Store.getObject(key, type);

/**
 *
 * @param {String} key - A unique key to store data.
 * @param {String} value - the Value to be appended to the existing data that was stored with the same key.
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */
Store.append(key, value, type);

/**
 * It compares the specified value with the data stored against the
 * specified key and returns a boolean.
 * It is case-sensitive.
 * @param {String} key - A unique key to check data.
 * @param {String} value - the Value to be check.
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */
Store.isEqual(key, value, type);

/**
 * It compares the specified value with the data stored against the
 * specified key and returns a boolean.
 * @param {String} key - A unique key to check data.
 * @param {String} value - the Value to be check.
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */
Store.isEqualIgnoreCase(key, (value = ""), type);

/**
 * It deletes entry for the specified key in the store.
 * It returns a boolean.
 * @param {String} key - A unique key to check data.
 * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
 */
Store.delete(key, type);
```
