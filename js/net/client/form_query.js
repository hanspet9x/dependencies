 export class FormQuery {
    #query;
  
    constructor() {
      this.#query = "";
    }
  
    /**
     * Appends name value to a form object.
     * It returns a string in the form of name = value&.
     * <b>Note:<b> the build menthod of this object must be invoked after all name value had been appended.
     * @param {String} key 
     * @param {String} value 
     */
    append = (key, value) => {
      if (value.length === 0 || value === null || value === undefined) {
        value = "null";
      }
  
      this.#query += `${encodeURI(key)}=${encodeURI(value)}&`;
      return this;
    };
  
    /**
     * It builds the query and covert name value to a string.
     * @param {Boolean} addBreak - prepend the query with the ? character if true.
     * @returns String
     */
    build = (addBreak = false) => {
      return addBreak?'?':""+this.#query.substr(0, this.#query.length - 1);
    };
  }