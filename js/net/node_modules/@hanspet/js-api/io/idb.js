
/**
 * A wrapper for IndexedDB.
 */
export class IdbStore {

    #objectStoreName = "appStore";
    #keyPath = "url";
    #readMode = "readonly";
    #readWriteMode = "readwrite";
    #openRequest = null;
  
    constructor(dbName) {
      this.dbName = dbName;
      this.#openRequest = window.indexedDB.open(dbName, 1);
      this.#openRequest.addEventListener("upgradeneeded", this.#onUpgradeNeeded);
      this.#openRequest.addEventListener("blocked", this.#onBlocked);
      this.#openRequest.addEventListener("success", this.#onRequestSucess);
      this.#openRequest.addEventListener("error", this.#onRequestError);
    }
  
    #onRequestError = (e) => {
      console.log("error", e);
    }
  
    #onRequestSucess = (e) => {
      this.db = this.#openRequest.result;
    }
  
    #onUpgradeNeeded = (e) => {
  
      console.log("upgraded needed.");
  
      var db = e.target.result;
  
      db.onversionchange = e => this.#onVersionChange(e);
  
      var objectStore = db.createObjectStore(this.#objectStoreName, { keyPath: this.#keyPath });
  
      objectStore.createIndex(this.#keyPath, this.#keyPath, { unique: true });
  
    }
  
  
    #onVersionChange = (e) => {
      console.log("version change");
    }
  
    #onBlocked = (e) => {
      console.log("blocked");
    }
  
    get = (url, callback) => {
      return new Promise((resolve, reject) => {
  
      try {
        let oStore = this.db.transaction(this.#objectStoreName, this.#readMode).objectStore(this.#objectStoreName);
        
          oStore.index(this.#keyPath).get(url).onsuccess = (e) => {
            // console.log("FetchStore GET", e.target.result);
            if (e.target.result !== undefined) {
              
                callback && callback(e.target.result.data);
              
              resolve(e.target.result.data);
            } else {
              callback && callback(undefined);
              reject(undefined);
            }
          }
        
      } catch (error) {
        // console.error("idb get error "+error);
        callback && callback(undefined);
      }
    }).catch(e => console.error(e));
  
    }
  
    setNew = (url, data, oldData, callbak = null) => {
  
      if (JSON.stringify(data) === JSON.stringify(oldData)) {
        return;
      }
  
      let oStore = this.db.transaction(this.#objectStoreName, this.#readWriteMode).objectStore(this.#objectStoreName);
      if (url !== undefined && url !== null && url !== '') {
  
        let req = oStore.put({ [this.#keyPath]: url, data: data });
        req.onerror = (e) => {
          console.error("setting idb data error");
        };
  
        req.onsuccess = () => {
          if (callbak !== null) callbak();
        }
      }
    }
  
    set = (url, data, callbak = null) => {
      let oStore = this.db.transaction(this.#objectStoreName, this.#readWriteMode).objectStore(this.#objectStoreName);
      if (url !== undefined && url !== null && url !== '') {
  
        let req = oStore.put({ [this.#keyPath]: url, data: data });
        req.onerror = (e) => {
          console.error(false);
        };
  
        req.onsuccess = () => {
          if (callbak !== null) callbak(true);
        }
      }
    }
  
    has = (url, callback) => {

      return new Promise((resolve, reject) => {
        this.get(url, (data) => {
          callback(data !== undefined);
          resolve(data !== undefined);
        });
      }).catch(e => console.log(e));
    }
  
  }
  