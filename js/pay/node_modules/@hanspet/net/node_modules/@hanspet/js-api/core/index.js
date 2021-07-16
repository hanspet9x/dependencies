const HP = {

  lorem: `Lorem ipsum dolor sit amet, 
  consectetur adipisicing elit, sed do eiusmod tempor 
    incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
     ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit 
     in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat
      non proident, sunt in culpa qui officia deserunt mollit anim id est laborum`,

  getLorem: (length) => {
    return HP.lorem.substring(0, length);
  },

  getRandomWords: (size) => {
    const alps = [
      "A",
      "S",
      "D",
      "F",
      "G",
      "H",
      "J",
      "K",
      "L",
      "Z",
      "X",
      "C",
      "V",
      "B",
      "N",
      "M",
      "Q",
      "W",
      "E",
      "R",
      "T",
      "Y",
      "U",
      "I",
      "O",
      "P",
      "a",
      "s",
      "d",
      "f",
      "g",
      "h",
      "j",
      "k",
      "l",
      "z",
      "x",
      "c",
      "v",
      "b",
      "n",
      "m",
      "q",
      "w",
      "e",
      "r",
      "t",
      "y",
      "u",
      "i",
      "o",
      "p",
      1,
      2,
      3,
      4,
      5,
      6,
      7,
      8,
      9,
      0,
    ];

    if (size > alps.length)
      throw new Error(size + " shouldn't be greater than " + alps.length);

    let bank = [];

    for (let i = 0; i < size - 1; i++) {
      bank.push(alps[Math.floor(Math.random() * alps.length - 1)]);
    }

    return bank.join("");
  },

  Date: {
    /**
     * It returns date, month and year separated by a slash.
     * @param {Date} date is an object.
     */
    getSlashedDate: function (date) {
      return `${date.getDate()}/${HP.prefixZero(
        date.getMonth() + 1
      )}/${date.getFullYear()}`;
    },

    /**
     * It takes number or string and returns the textual month e.g 0 returns January
     * @param {String|Number} monthId
     */
    getMonthText: function (monthId) {
      let month;
      switch (monthId) {
        case 0:
          month = "January";
          break;
        case 1:
          month = "February";
          break;
        case 2:
          month = "March";
          break;
        case 3:
          month = "April";
          break;
        case 4:
          month = "May";
          break;
        case 5:
          month = "June";
          break;
        case 6:
          month = "July";
          break;
        case 7:
          month = "August";
          break;
        case 8:
          month = "September";
          break;
        case 9:
          month = "October";
          break;
        case 10:
          month = "November";
          break;
        default:
          month = "December";
          break;
      }
      return month;
    },

    /**
     * Date format is yearMonthDay, it takes a second parameter that specifies the concatenated string
     * between year, month and day, default is "-";
     * it returns Month FormatedDay, Year e.g May 24th, 2020.
     * @param {String} date
     * @param {String} separator
     */
    getStyledDate: function (date, separator = "-") {
      let data = date.split(separator);
      return (
        this.getDayFormat(data[2]) +
        " " +
        this.getMonthText(parseInt(data[1]) + 1) +
        ", " +
        data[0]
      );
    },

    getStyledDate2: function (date = null) {
      let pDate = date === null || date === "" ? new Date() : date;

      return (
        this.getDayFormat(pDate.getDate()) +
        " " +
        this.getMonthText(pDate.getMonth()) +
        ", " +
        pDate.getFullYear()
      );
    },

    /**
     *It formats a number by adding suffix like "st", "nd", "rd" and "th" depending on the the last digit.
     * @param {Number | String} day
     */
    getDayFormat: function (day) {
      let data = day + "";
      let result;

      switch (parseInt(data.charAt(data.length - 1))) {
        case 1:
          result = data + "st";
          break;
        case 2:
          result = data + "nd";
          break;
        case 3:
          result = data + "rd";
          break;
        default:
          result = data + "th";
          break;
      }

      return result;
    },
  },

  Time: {
    getSecond2Minute: (sec) => {
      return sec / 60;
    },

    getMinute2Second: (min) => {
      return 60 * min;
    },

    get24CurrentTime: function (date) {
      return `${HP.dateTimePrefixZero(date.getHours())}:${HP.dateTimePrefixZero(
        date.getMinutes()
      )}:${HP.dateTimePrefixZero(date.getSeconds())}`;
    },

    get12CurrentTime: function (date) {
      function define12(hour) {
        if (hour > 12) {
          return hour - 12;
        }
        return 12;
      }
      return `${define12(
        date.getHours()
      )}:${date.getMinutes()}:${date.getSeconds()}`;
    },
  },

  /**
   * It prefix data with zero(s).
   * @param {Number} num is the data to alterate.
   * @param {String} len is the number of zeroes to add.
   */
  zeroPrefixNumber: function (num, len = 1) {
    let zeros = "0";

    if (len > 1) {
      for (let i = 0; i < len; i++) {
        zeros += "0";
      }
      return zeros + num;
    }

    return zeros + num;
  },

  dateTimePrefixZero: function (num) {
    let data = this.prefixZero(num);
    return data.length > 2 ? data.substr(1) : data;
  },

  base64ToString: (base64 = "") => {
    if (base64.includes("image")) {
      return base64.replace(/^data:image\/{png|jpg};base64,/, "");
    }
  },

  base64fromString: (string) => {
    return `data:image/png;base64,${string}`;
  },

  camelCaseToGap: (camelCase = "", capitalizeFirsLetter = false) => {


    let regex = /[A-Z]/;
    let data = "";
    Array.from(camelCase).forEach(e => {
      if (regex.test(e)) {
        data += " ";
        data += e.toLowerCase();
      } else {
        data += e;
      }
    });

    if (capitalizeFirsLetter) {
      return data.replace(data.charAt(0), data.charAt(0).toUpperCase());
    }

    return data;
  },

  gapToCamelCase: (gapCase = "", capitalizeFirsLetter = false) => {

    let data = "";
    let hasSpace = false;
    Array.from(gapCase).forEach(e => {
      if (e === " ") {
        hasSpace = true;
      } else {
        if (hasSpace) {
          data += e.toUpperCase();
          hasSpace = false;
        } else {
          data += e;
        }
      }
    });

    if (capitalizeFirsLetter) {
      return data.replace(data.charAt(0), data.charAt(0).toUpperCase());
    }

    return data;
  },

  gapToSnakeCase: (gapCase = "", capitalizeFirsLetter = false) => {
    let data = gapCase.replaceAll(" ", "_");
    if (capitalizeFirsLetter) {
      return data.replace(data.charAt(0), data.charAt(0).toUpperCase());
    }
    return data;
  },

  snakeCaseToGap: (snakeCase = "", capitalizeFirsLetter = false) => {
    let data = snakeCase.replaceAll("_", " ");
    if (capitalizeFirsLetter) {
      return data.replace(data.charAt(0), data.charAt(0).toUpperCase());
    }
    return data;
  },

  getNumberRange: (start, end) => {
    let arr = [];
    if (Number.isInteger(start) && Number.isInteger(end) && end > start) {
      for (let i = start; i <= end; i++) {
        arr.push(i);
      }
    }
    return arr;
  },

  delay: (callback, timeout) => {
    console.log(callback);
    setTimeout(callback, timeout);

  },

  getAgencyFromHostname: () => {
    var data = window.location.hostname.split(".");
    return data[0].toLowerCase() === "www" ? data[1] : data[0];
  }
};

module.exports = HP;