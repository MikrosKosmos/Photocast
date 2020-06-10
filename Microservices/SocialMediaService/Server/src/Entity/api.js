const printer = require('./../Helpers/printer');
const database = require('./../Services/databaseService');
const validator = require('./../Helpers/validators');
const constants = require('./../Helpers/constants');

const Cache = require('./../Helpers/cacheHelper');

class Api {
   /**
    * _token
    * @param token
    */
   constructor(token) {
      this._token = validator.validateString(token) ? token : false;
   }

   /**
    * Method to validate the API Token.
    * @returns {Promise<Boolean>}: true if correct token, else error.
    */
   validateToken() {
      return new Promise(async (resolve, reject) => {
         try {
            const cache = new Cache();
            const token = await cache.getData(constants.API_TOKEN_KEY);
            if (token === this._token) {
               resolve(true);
            } else {
               database.runSp(constants.SP_CHECK_API_TOKEN, [this._token]).then(async _resultSet => {
                  const result = _resultSet[0][0];
                  if (validator.validateUndefined(result.id) && result.id === 1) {
                     resolve(true);
                     await cache.storeData(constants.API_TOKEN_KEY, this._token);
                  } else {
                     reject([constants.ERROR_LEVEL_4, constants.FORBIDDEN_MESSAGE]);
                  }
               }).catch(err => {
                  printer.printError(err);
                  reject([constants.ERROR_LEVEL_4, constants.FORBIDDEN_MESSAGE]);
               });
            }
         } catch (e) {
            printer.printError(e);
            reject(e);
         }
      });
   }

   /**
    * Method to log the API Status.
    * @param requestKey: the request key.
    * @param path: The path API end point.
    * @param responseCode: The response code.
    */
   logAPIStatus(requestKey, path, responseCode) {
      let apiStatus;
      if (responseCode === constants.HTTP_SUCCESS) {
         apiStatus = constants.STATUS_COMPLETED;
      } else if (responseCode === constants.HTTP_ACCEPTED_OKAY) {
         apiStatus = constants.STATUS_PROCESSING;
      } else {
         apiStatus = constants.STATUS_ERROR;
      }
      database.runSp(constants.SP_LOG_API_STATUS, [requestKey, path, apiStatus, responseCode, this._token])
         .then(_resultSet => {
            printer.printError("API Status Logged.");
         }).catch(err => {
         printer.printError(err);
      });
   }
}

/**
 * Exporting the API.
 */
module.exports = Api;