const HP = {
    randomizeArray: (array, length = 0) => {
        length = length ? length : array.length;
        let data = [];
        let values = [];
        for (let i = 0; i < length; i++) {
            let n = Math.floor(Math.random() * (array.length - 1));

            while (values.includes(n)) {
                n = Math.floor(Math.random() * length);
            }
            values.push(n);
            data.push(array[n]);
        }
        return data;
    },
    /**
 * It helps track the last row. The last row -boolean value is returned in the callback as the second param.
 * It is useful for writing streams of data into a fd.
 * @param {Array} data An Array
 * @param {Function} callback A function with params (element, isLastRow, index)
 */
    loop: (data = [], callback) => {
        if (Array.isArray(data)) {
            for (let i = 0; i < data.length; i++) {
                callback(data[i], i + 1 >= data.length, i);
            }
        }
    },

    loopIntervalFx: (data = [], callback, { timer = 0, start = 0, count = 1 }) => {

        if (Array.isArray(data) && start < data.length) {

            setTimeout(() => {
                //get data by count...if count is greater than one it returns an array.
                if (count > 1) {

                    let curData = [];

                    let min = Math.min(data.length, start + count);

                    for (let i = start; i < min; i++) {

                        curData.push(data[start]);
                    }

                    callback(curData, start + 1 >= data.length, start);

                    HP.loopInterval(data, callback, { timer: timer, start: min, count: count });

                } else {

                    callback(data[start], start + 1 >= data.length, start);

                    HP.loopInterval(data, callback, { timer: timer, start: ++start, count: 1 });

                }

            }, timer);
        }
    },

    loopInterval: (data = [], callback, timer = 0, start = 0) => {

        if (Array.isArray(data) && start < data.length) {

            setTimeout(() => {

                callback(data[start], start + 1 >= data.length, start);

                HP.loopInterval(data, callback, timer, ++start);

            }, timer);
        }
    },

    /**
   * It fiters the provided keys from the array of objects or the object.
   * E.g Filtering name from the {name: "DJ", age: 11} returns {age: 11}.
   * E.g Filtering name from the {name: "DJ", age: 11} with a true inverse returns {name: "DJ"}.
   * @param {Array} jsObj An array of objects.
   * @param  {Array} keys An array of keys to filter.
   * @param {Boolean} inverse Defaults to false. True filters off every entry with key not specified in the keys.
   * @returns Array|Object 
   */
    filterArray: (jsObj, keys = [], inverse = false) => {
        let objs = [];

        let loop = (os) => {
            let obj = {};
            for (const key in os) {
                if (inverse) {
                    if (keys.includes(key)) {
                        obj[key] = os[key];
                    }
                } else {
                    if (!keys.includes(key)) {
                        obj[key] = os[key];
                    }
                }
            }
            return obj;
        }

        if (Array.isArray(jsObj)) {
            jsObj.forEach(e => {
                objs.push(loop(e));
            });
            return objs;
        }
    },
    getArrayOfNum: (num, startFromOne = false) => {

        if (!Number.isInteger) return [];

        if (startFromOne) {
            return Array.from(Array(num).keys()).map(e => e + 1);
        }
        return Array.from(Array(num).keys());

    },

    /**
     * It searches an array [{id: 1}, {id: 2}] using the needle {id: 1} and the key "id" returns index 0 if there is a match 
     * else, -1 if no match.
     * @param {Array} haystack Array of object to find the specified index.
     * @param {Object} needle Refrence Object that contains the same key.
     * @param {String} key The key name used in the search.
     * @returns Number
     */
    getIndexByObject: (haystack = [], needle = {}, key = "_id") => {

        if (needle.hasOwnProperty(key) && Array.isArray(haystack) && haystack.length > 0) {

            for (let i = 0; i < haystack.length; i++) {

                if (haystack[i][key] === needle[key]) {

                    return i;
                }
            }

        }
        return -1;
    },

    /**
   * It searches an array [{id: 1}, {id: 2}] using the needle 1 and the key "id" returns index 0 if there is a match 
   * else, -1 if no match.
   * @param {Array} haystack Array of object to find the specified index.
   * @param {String} needle Value to be searched.
   * @param {String} key The key name used in the search.
   * @returns Number
   */
    getIndexByString: (haystack = [], needle = "", key = "_id") => {

        if (Array.isArray(haystack) && haystack.length > 0) {

            for (let i = 0; i < haystack.length; i++) {
                if (haystack[i][key] === needle) {

                    return i;
                }
            }

        }
        return -1;
    },
    sortObjectArray: (array = [], key, reverse = false) => {
        array.sort((a, b) => {

            if (typeof a[key] === "string") {

                if (a[key].toLowerCase() < b[key].toLowerCase()) {
                    return reverse ? 1 : -1;
                } else if (a[key].toLowerCase() > b[key].toLowerCase()) {

                    return reverse ? -1 : 1;
                } else {

                    return 0;
                }


            } else if (typeof a[key] === "number") {
                return reverse ? b[key] - a[key] : a[key] - b[key];
            }

            return a[key] > b[key];
        });
    },
}

module.exports = HP;