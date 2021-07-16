const HP = {
    merge: (...objects) => {
        let style = {};
        objects.forEach((e) => (style = Object.assign(style, e)));
        return style;
    },

    hasEmptyValue: (fields, callback) => {

        for (const key in fields) {
            if (fields[key] === "" || fields[key] === []) {
                callback(fields[key]);
                return true;
            }
        }
        return false;
    },

    searchEmptyValue: (fields, excludes = []) => {

        for (const key in fields) {
            if (!excludes.includes(key)) {
                if (fields[key] === "" || fields[key] === null || fields[key] === undefined) {
                    return { valid: false, field: key };
                }
            }
        }
        return { valid: true };
    },

    filterObject: (os, inverse = false) => {
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
}

module.exports = HP;