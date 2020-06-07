const constants = require('./../Helpers/constants');
const responseGenerator = require('./../Services/responseGenerator');

const auth = require('./authentication');
const customer = require('./customer');
const vendor = require('./vendor');
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
 * Method to handle the auth requests.
 * @param dataObject: the request object.
 * @returns {Promise<Array>}
 */
handlerObj.auth = (dataObject) => {
   return new Promise((resolve, reject) => {
      let promise;
      switch (dataObject.path) {
         case "auth":
            promise = auth.auth(dataObject);
            break;
         case "token":
            promise = auth.token(dataObject);
            break;
         default:
            reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_2));
      }
      promise.then(data => {
         resolve(data);
      }).catch(err => {
         reject(err);
      });
   });
};
/**
 * Method to handle the Customer requests.
 * @param dataObject: the request object.
 * @returns {Promise<Array>}
 */
handlerObj.customer = (dataObject) => {
   return new Promise((resolve, reject) => {
      let promise;
      switch (dataObject.path) {
         case "customers":
            promise = customer.customer(dataObject);
            break;
         case "address":
            promise = customer.address(dataObject);
            break;
         default:
            reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_2));
      }
      promise.then(data => {
         resolve(data);
      }).catch(err => {
         reject(err);
      });
   });
};
/**
 * Method to route the second path for vendors.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}:
 */
handlerObj.vendors = (dataObject) => {
   return new Promise((resolve, reject) => {
      let promise;
      switch (dataObject.path) {
         case "vendors":
            promise = vendor.vendor(dataObject);
            break;
         case "details":
            promise = vendor.details(dataObject);
            break;
         case "images":
            promise = vendor.images(dataObject);
            break;
         default:
            reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_2));
      }
      promise.then(data => {
         resolve(data);
      }).catch(err => {
         reject(err);
      });
   });
};
/**
 * Exporting the module.
 */
module.exports = handlerObj;