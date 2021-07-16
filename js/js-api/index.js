
const {lorem, getLorem, getRandomWords, Date, Time, dateTimePrefixZero,
     base64ToString, base64fromString, camelCaseToGap, gapToCamelCase, gapToSnakeCase, snakeCaseToGap, 
    delay} = require('./core');

const {filterArray, getArrayOfNum, getIndexByObject, getIndexByString, 
    loop, loopInterval, loopIntervalFx, randomizeArray,sortObjectArray} = require('./array');

const {filterObject, hasEmptyValue, merge, searchEmptyValue} = require('./object');

const Values = require('./cons');

module.exports = {
    Values, lorem, getLorem, getRandomWords, Date, Time, dateTimePrefixZero,
    randomizeArray, base64ToString, base64fromString, loop, filterArray,
    filterObject, hasEmptyValue, merge, searchEmptyValue,
    loopIntervalFx, loopInterval,  camelCaseToGap, gapToCamelCase, gapToSnakeCase, snakeCaseToGap, 
    sortObjectArray, getArrayOfNum, getIndexByObject, getIndexByString, delay
}