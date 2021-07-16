
/**
 * A wrapper for the client Local Storage and Session;
 */
export const Store = {
    delimeter: "&$&h|$&p&",
    Local: 0,
    Session: 1,

    /**
     * 
     * @param {String} key - A unique key to store data.
     * @param {String} value - the Value to be stored
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    set: (key, value, type = 0) => {
      if (type === 0) {
        localStorage.setItem(key, value);
      } else {
        sessionStorage.setItem(key, value);
      }
    },

   /**
     * 
     * @param {String} key - A unique key to store data.
     * @param {Object} value - the Value to be stored
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    setObject: (key, object, type = 0) => {
      if (type === 0) {
        localStorage.setItem(key, JSON.stringify(object));
      } else {
        sessionStorage.setItem(key, JSON.stringify(object));
      }
    },
    /**
     * It returns the stored data using the key and the storage type.
     * If the value was added to by using the Store.append, it returns
     * an array.
     * @param {String} key - A unique key to store data.
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    get: (key, type = 0) => {
      let value;
      if (type === 0) {
        value = localStorage.getItem(key);
      } else {
        value = sessionStorage.getItem(key);
      }
      if(value.includes(Store.delimeter)){
        return value.split(Store.delimeter);
      }
      return value;
    },

    /**
     * It returns the object saved with Store.setObject using the key and the storage type.
     * @param {String} key - A unique key to store data.
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    getObject: (key, type = 0) => {
      if (type === 0) {
        return JSON.parse(localStorage.getItem(key));
      } else {
        return JSON.parse(sessionStorage.getItem(key));
      }
    },
    /**
     * 
     * @param {String} key - A unique key to store data.
     * @param {String} value - the Value to be appended to the existing data that was stored with the same key.
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    append: (key, value, type = 0) => {
      if (!!this.has(key, type)) {
        this.set(key, this.get(key, type) + this.delimiter + value, type);
      } else {
        this.set(key, value, type);
      }
    },
    /**
     * It returns true if the key exists in the storage type.
     * @param {String} key - A unique key to store data.
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    has: function (key, type = 0) {
      var res = this.get(key, type);
      return res !== null;
    },

    /**
     * It compares the specified value with the data stored against the 
     * specified key and returns a boolean.
     * It is case-sensitive.
     * @param {String} key - A unique key to check data.
     * @param {String} value - the Value to be check.
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    isEqual: (key, value, type = 0) => {
      if (Store.has(key, type)) {
        return Store.get(key, type) === value;
      }
      return false;
    },
  
    /**
     * It compares the specified value with the data stored against the 
     * specified key and returns a boolean.
     * @param {String} key - A unique key to check data.
     * @param {String} value - the Value to be check.
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    isEqualIgnoreCase: (key, value = "", type = 0) => {
      if (Store.has(key, type)) {
        return Store.get(key, type).toLowerCase === value.toLowerCase;
      }
      return false;
    },
  
    /**
     * It deletes entry for the specified key in the store.
     * It returns a boolean.
     * @param {String} key - A unique key to check data.
     * @param {Integer} type - Store.Local or Store.Session. Default is Store.Local
     */
    delete: (key, type = 0) => {
      if (Store.has(key, type)) {
        if (type === 0) {
          localStorage.removeItem(key);
          return true;
        } else {
          sessionStorage.removeItem(key);
          return true;
        }
      }
      return false;
    },
  };

  