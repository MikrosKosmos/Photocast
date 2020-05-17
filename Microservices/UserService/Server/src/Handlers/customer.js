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
         const id = validator.validateNumber(dataObject.queryString[constants.CUSTOMER_ID]) ?
            dataObject.queryString[constants.CUSTOMER_ID] : false;
         const email = validator.validateEmail(dataObject.queryString[constants.CUSTOMER_EMAIL]) ?
            dataObject.queryString[constants.CUSTOMER_EMAIL] : false;
         const phoneNumber = validator.validatePhone(dataObject.queryString[constants.CUSTOMER_PHONE_NUMBER]) ?
            dataObject.queryString[constants.CUSTOMER_PHONE_NUMBER] : false;
         if (id || email || phoneNumber) {
            const customer = new Customer(id, false, false, false, email, phoneNumber);
            customer.getCustomer().then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
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
         const firstName = validator.validateString(dataObject.postData[constants.CUSTOMER_FIRST_NAME]) ?
            dataObject.postData[constants.CUSTOMER_FIRST_NAME] : false;
         const lastName = validator.validateString(dataObject.postData[constants.CUSTOMER_LAST_NAME]) ?
            dataObject.postData[constants.CUSTOMER_LAST_NAME] : false;
         const phone = validator.validatePhone(dataObject.postData[constants.CUSTOMER_PHONE_NUMBER]) ?
            dataObject.postData[constants.CUSTOMER_PHONE_NUMBER] : false;
         const email = validator.validateEmail(dataObject.postData[constants.CUSTOMER_EMAIL]) ?
            dataObject.postData[constants.CUSTOMER_EMAIL] : false;
         const password = validator.validateString(dataObject.postData[constants.CUSTOMER_PASSWORD]) ?
            dataObject.postData[constants.CUSTOMER_PASSWORD] : false;
         const customerId = validator.validateNumber(dataObject.postData[constants.CUSTOMER_ID]) ?
            dataObject.postData[constants.CUSTOMER_ID] : false;
         const jwToken = validator.validateString(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (customerId && (firstName || lastName || phone || email || password) && jwToken) {
            const customer = new Customer(customerId, firstName, lastName, false, email, phone);
            customer.updateCustomerDetails(password, jwToken).then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
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
 * Method to handle the customer address requests.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}: The response code and the response.
 */
customerHandler.address = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_GET) {
         const customerId = validator.validateNumber(dataObject.queryString[constants.CUSTOMER_ID]) ?
            dataObject.queryString[constants.CUSTOMER_ID] : false;
         const jwToken = validator.validateString(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (customerId && jwToken) {
            const customer = new Customer(customerId);
            customer.getCustomerAddress(jwToken).then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else if (method === constants.HTTP_POST) {
         const address1 = validator.validateString(dataObject.postData[constants.CUSTOMER_ADDRESS_1]) ?
            dataObject.postData[constants.CUSTOMER_ADDRESS_1] : false;
         const address2 = validator.validateString(dataObject.postData[constants.CUSTOMER_ADDRESS_2]) ?
            dataObject.postData[constants.CUSTOMER_ADDRESS_2] : false;
         const cityId = validator.validateNumber(dataObject.postData[constants.CUSTOMER_CITY]) ?
            dataObject.postData[constants.CUSTOMER_CITY] : false;
         const pincode = validator.validateNumber(dataObject.postData[constants.CUSTOMER_PINCODE]) ?
            dataObject.postData[constants.CUSTOMER_PINCODE] : false;
         const isDefault = validator.validateNumber(dataObject.postData[constants.CUSTOMER_ADDRESS_IS_DEFAULT]) ?
            dataObject.postData[constants.CUSTOMER_ADDRESS_IS_DEFAULT] : 0;
         const gpsLat = validator.validateNumber(dataObject.postData[constants.CUSTOMER_GPS_LAT]) ?
            dataObject.postData[constants.CUSTOMER_GPS_LAT] : false;
         const gpsLong = validator.validateNumber(dataObject.postData[constants.CUSTOMER_GPS_LONG]) ?
            dataObject.postData[constants.CUSTOMER_GPS_LONG] : false;
         const id = validator.validateNumber(dataObject.postData[constants.CUSTOMER_ID]) ?
            dataObject.postData[constants.CUSTOMER_ID] : false;
         const jwToken = validator.validateString(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (id && address1 && cityId && pincode && gpsLat && gpsLong && jwToken) {
            const customer = new Customer(id);
            customer.updateCustomerAddress(address1, address2, cityId, pincode, gpsLat, gpsLong, isDefault, jwToken).then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
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
 * Exporting the customer handler.
 * @type {{}}
 */
module.exports = customerHandler;
