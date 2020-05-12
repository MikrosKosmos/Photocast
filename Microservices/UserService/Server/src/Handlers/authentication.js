const constants = require('./../Helpers/constants');
const validator = require('./../Helpers/validators');
const printer = require('./../Helpers/printer');
const responseGenerator = require('./../Services/responseGenerator');

const Authentication = require('./../Entity/authentication');
const authHandler = {};
/**
 * Method to handle all the authentication requests.
 * @param dataObject: The request data
 * @returns {Promise<Array>}
 */
authHandler.auth = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_POST) {
         const email = validator.validateEmail(dataObject.postData[constants.AUTH_EMAIL]) ?
            dataObject.postData[constants.AUTH_EMAIL] : false;
         const password = validator.validateString(dataObject.postData[constants.AUTH_PASSWORD]) ?
            dataObject.postData[constants.AUTH_PASSWORD] : false;
         const phoneNumber = validator.validatePhone(dataObject.postData[constants.AUTH_PHONE]) ?
            dataObject.postData[constants.AUTH_PHONE] : false;
         const otp = validator.validateNumber(dataObject.postData[constants.AUTH_OTP]) ?
            dataObject.postData[constants.AUTH_OTP] : false;
         if ((email && password) || (phoneNumber && otp)) {
            const auth = new Authentication(phoneNumber, otp, email, password);
            auth.validate().then(result => {
               resolve(responseGenerator.generateResponse(result[1], result[0]));
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else {
         reject(responseGenerator.generateErrorResponse(constants.INVALID_METHOD_MESSAGE, constants.ERROR_LEVEL_1));
      }
   });
};
/**
 * Exporting the authentication.
 * @type {{}}
 */
module.exports = authHandler;