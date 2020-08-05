const cityHandler = {};
const constants = require('./../Helpers/constants');
const validator = require('./../Helpers/validators');
const printer = require('./../Helpers/printer');
const responseGenerator = require('./../Services/responseGenerator');

const City = require('./../Entity/city');
/**
 * Method to handle the city search.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}
 */
cityHandler.city = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_GET) {
         const cityName = validator.validateString(dataObject.queryString[constants.CITY_NAME]) ?
            dataObject.queryString[constants.CITY_NAME] : false;
         const stateID = validator.validateNumber(dataObject.queryString[constants.CITY_STATE_ID]) ?
            dataObject.queryString[constants.CITY_STATE_ID] : false;
         if (cityName || stateID) {
            const city = new City(false, cityName);
            city.searchCityName(stateID).then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
            }).catch(err => {
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
 * Method to search for state.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}
 */
cityHandler.state = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_GET) {
         const city = new City();
         city.searchState().then(response => {
            resolve(responseGenerator.generateResponse(response[1], response[0]));
         }).catch(err => {
            reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
         });
      } else {
         reject(responseGenerator.generateErrorResponse(constants.INVALID_METHOD_MESSAGE, constants.ERROR_LEVEL_1));
      }
   });
};
/**
 * Exporting the module.
 */
module.exports = cityHandler;