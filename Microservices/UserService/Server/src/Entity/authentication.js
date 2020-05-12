const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const generator = require('./../Services/generator');
const validators = require('./../Helpers/validators');

const {getToken} = require('./../Services/jwTokenGenerator');

class Authentication {
   /**
    * _mobile
    * _otp
    * _email
    * _password
    * @param mobile
    * @param otp
    * @param email
    * @param password
    */
   constructor(mobile, otp, email, password) {
      this._mobile = validators.validatePhone(mobile) ? mobile : false;
      this._otp = validators.validateNumber(otp) ? otp : false;
      this._email = validators.validateEmail(email) ? email : false;
      this._password = validators.validateString(password) ? password : false;
   }
   
   /**
    * Method to validate the token.
    * @returns {Promise<Array>}: Response object and the response code.
    */
   validate() {
      return new Promise((resolve, reject) => {
         database.runSp(constants.SP_VALIDATE_LOGIN, [this._mobile, this._otp, this._email, this._password])
            .then(_resultSet => {
               const result = _resultSet[0][0];
               if (validators.validateUndefined(result) && result.id !== -1) {
                  result[constants.JW_TOKEN] = getToken(result);
                  resolve([constants.HTTP_SUCCESS, result]);
               } else {
                  resolve([constants.HTTP_SUCCESS, {"id": -1},]);
               }
            }).catch(err => {
            printer.printError(err);
            reject([constants.INTERNAL_SERVER_ERROR_CODE, err]);
         });
      });
   }
}

/**
 * Exporting the authentication module.
 * @type {Authentication}
 */
module.exports = Authentication;