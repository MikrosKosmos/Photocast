const constants = require('./../Helpers/constants');
const responseGenerator = require('./../Services/responseGenerator');
const handlerObj = {};
/**
 * Method to handle the Error path requests.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}: The response object with the Message and the HTTP Code.
 */
handlerObj.notFound = (dataObject) => {
   return new Promise((reject) => {
      reject([constants.INVALID_PATH, constants.HTTP_NOT_FOUND_CODE]);
   });
};

/**
 * Exporting the module.
 */
module.exports = handlerObj;q