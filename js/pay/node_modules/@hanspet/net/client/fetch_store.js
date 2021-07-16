

import Client, {ResponseType} from './../client/http';
import {IdbStore} from '@hanspet/js-api/io'
/**
 * A wrapper that provides previously stored data in a IndexedDb and updates stored data using the fetch api.
 * @param {String} databaseName The database name.
 */
 export const fetchStore = (databaseName) => {

    
    let store = new IdbStore(databaseName);

    /**
     * It sends a GET request to the provided endpoint and returns a text.
     * @param {String} url The endpoint.
     * @param {Function} callback store response.
     * @param {String | Object} data Js Object e.g {name: "dj"} or FormQuery instance or String e.g "name=ade&age=17".
     * @param {Cache} cache specifies if response should be cached. Default is NO-CACHE.
     */
    const getText = (url, callback, data = {}, cache = Client.NO_CACHE) => {
      return processGet(url, callback, cache, data, ResponseType.Text);
    };
  
    /**
   * It sends a GET request to the provided endpoint and returns a boolean.
   * @param {String} url The endpoint.
   * @param {Function} callback store response.
   * @param {String | Object} data Js Object e.g {name: "dj"} or FormQuery instance or String e.g "name=ade&age=17".
   * @param {Cache} cache specifies if response should be cached. Default is NO-CACHE.
   */
    const getBoolean = (url, callback, data = {}, cache = Client.NO_CACHE) => {
      return processGet(url, callback, cache, data, ResponseType.Boolean);
    };
  
    /**
   * It sends a GET request to the provided endpoint and returns a number.
   * @param {String} url The endpoint.
   * @param {Function} callback store response.
   * @param {String | Object} data Js Object e.g {name: "dj"} or FormQuery instance or String e.g "name=ade&age=17".
   * @param {Cache} cache specifies if response should be cached. Default is NO-CACHE.
   */
    const getNumber = (url, callback, data = {}, cache = Client.NO_CACHE) => {
      return processGet(url, callback, cache, data, ResponseType.Number);
    };
  
    /**
   * It sends a GET request to the provided endpoint and returns a blob.
   * @param {String} url The endpoint.
   * @param {Function} callback store response.
   * @param {String | Object} data Js Object e.g {name: "dj"} or FormQuery instance or String e.g "name=ade&age=17".
   * @param {Cache} cache specifies if response should be cached. Default is NO-CACHE.
   */
    const getBlob = (url, callback, data = {}, cache = Client.NO_CACHE) => {
      return processGet(url, callback, cache, data, ResponseType.Blob);
    };
  
    /**
   * It sends a GET request to the provided endpoint and returns an arraybuffer.
   * @param {String} url The endpoint.
   * @param {Function} callback store response.
   * @param {String | Object} data Js Object e.g {name: "dj"} or FormQuery instance or String e.g "name=ade&age=17".
   * @param {Cache} cache specifies if response should be cached. Default is NO-CACHE.
   */
    const getArrayBuffer = (url, callback, data = {}, cache = Client.NO_CACHE) => {
      return processGet(url, callback, cache, data, ResponseType.ArrayBuffer);
    };
  
    /**
   * It sends a GET request to the provided endpoint and returns Json.
   * @param {String} url The endpoint.
   * @param {Function} callback store response.
   * @param {String | Object} data Js Object e.g {name: "dj"} or FormQuery instance or String e.g "name=ade&age=17".
   * @param {Cache} cache specifies if response should be cached. Default is NO-CACHE.
   */
    const getJson = (url, callback, data = {}, cache = Client.NO_CACHE) => {
      return processGet(url, callback, data, cache, ResponseType.Json);
    };
  
    /**
   * It sends a GET request to the provided endpoint and returns a text.
   * @param {String} url The endpoint.
   * @param {Function} callback store response.
   * @param {ResponseType} type one of the properties of the ResponseType object. Default is Json.
   * @param {String | Object} data Js Object e.g {name: "dj"} or FormQuery instance or String e.g "name=ade&age=17".
   * @param {Cache} cache specifies if response should be cached. Default is NO-CACHE.
   */
    const get = (url, callback, type = ResponseType.Json, data = {}, cache = Client.NO_CACHE) => {
  
      processGet(url, callback, data, cache, type);
    };
  
    const processGet = (url, callback = undefined, data = {}, cache, type) => {
  
      store.get(url, (res) => {
  
        if (res !== undefined && res !== null) {
          if (callback) callback(res);
        }
  
        Client.setUpGET(
          url, data, type, (nRes) => {
            if(nRes !== null && nRes !== undefined){
              store.setNew(url, nRes, res, () => {
                console.log(nRes);
                if (callback) callback(nRes);
              });
            }
          },
          cache
        );
      });
    };
  
  
    return {
      get,
      getText,
      getJson,
      getBlob,
      getNumber,
      getArrayBuffer,
      getBoolean,
      store
    };
  };