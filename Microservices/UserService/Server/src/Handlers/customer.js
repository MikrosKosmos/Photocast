const constants = require('./../Helpers/constants');
const validator = require('./../Helpers/validators');
const printer = require('./../Helpers/printer');
const responseGenerator = require('./../Services/responseGenerator');

const Customer = require('./../Entity/customer');
const customerHandler = {};
/**
 * Method to handle the requests for Customer.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}: The response code and the response message.
 */
customerHandler.customer = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_GET) {
         //TODO:
      } else if (method === constants.HTTP_POST) {
         const firstName = validator.validateString(dataObject.postData[constants.CUSTOMER_FIRST_NAME]) ?
            dataObject.postData[constants.CUSTOMER_FIRST_NAME] : false;
         const lastName = validator.validateString(dataObject.postData[constants.CUSTOMER_LAST_NAME]) ?
            dataObject.postData[constants.CUSTOMER_LAST_NAME] : false;
         const phone = validator.validatePhone(dataObject.postData[constants.CUSTOMER_PHONE_NUMBER]) ?
            dataObject.postData[constants.CUSTOMER_PHONE_NUMBER] : false;
         const gender = validator.validateCharacter(dataObject.postData[constants.CUSTOMER_GENDER]) ?
            dataObject.postData[constants.CUSTOMER_GENDER] : false;
         const email = validator.validateEmail(dataObject.postData[constants.CUSTOMER_EMAIL]) ?
            dataObject.postData[constants.CUSTOMER_EMAIL] : false;
         const password = validator.validateString(dataObject.postData[constants.CUSTOMER_PASSWORD]) ?
            dataObject.postData[constants.CUSTOMER_PASSWORD] : false;
         const usedReferralCode = validator.validateString(dataObject.postData[constants.CUSTOMER_USED_REFERAL_CODE]) ?
            dataObject.postData[constants.CUSTOMER_USED_REFERAL_CODE] : false;
         if (firstName && lastName && phone && gender) {
            const customer = new Customer(false, firstName, lastName, gender, email, phone);
            customer.createCustomer(usedReferralCode).then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else if (method === constants.HTTP_PUT) {
         //TODO:
      } else {
         reject(responseGenerator.generateErrorResponse(constants.INVALID_METHOD_MESSAGE, constants.ERROR_LEVEL_1));
      }
   });
};
/**
 * Exporting the customer handler.
 * @type {{}}
 */
module.exports = customerHandler;
