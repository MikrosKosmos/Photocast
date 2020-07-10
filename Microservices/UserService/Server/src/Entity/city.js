const database = require('./../Services/databaseService');
const constants = require('./../Helpers/constants');
const validators = require('./../Helpers/validators');
const generator = require('./../Services/generator');
const printer = require('./../Helpers/printer');

class City {
   /**
    * _cityId
    * _cityName
    * @param cityId
    * @param cityName
    */
   constructor(cityId, cityName) {
      this._cityId = validators.validateNumber(cityId) ? cityId : false;
      this._cityName = validators.validateString(cityName) ? cityName : false;
   }

   /**
    * Method to search for cities.
    * @param stateId: The state id.
    * @returns {Promise<Array>} An Array of cities.
    */
   searchCityName(stateId) {
      return new Promise((resolve, reject) => {
         database.runSp(constants.SP_GET_CITIES, [this._cityName, stateId]).then(_resultSet => {
            const result = _resultSet[0];
            if (validators.validateUndefined(result)) {
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
            } else {
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, []]);
            }
         }).catch(err => {
            printer.printError(err);
            reject([constants.ERROR_LEVEL_3, err]);
         });
      });
   }

   /**
    * Method to search for state.
    * @returns {Promise<Array>}
    */
   searchState() {
      return new Promise((resolve, reject) => {
         database.runSp(constants.SP_GET_STATE, []).then(_resultSet => {
            const result = _resultSet[0];
            if (validators.validateUndefined(result)) {
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
            } else {
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, []]);
            }
         }).catch(err => {
            printer.printError(err);
            reject([constants.ERROR_LEVEL_3, err]);
         });
      });
   }
}

/**
 * Exporting the module.
 */
module.exports = City;