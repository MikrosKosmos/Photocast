const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const generator = require('./../Services/generator');
const validators = require('./../Helpers/validators');

const notificationManager = require('./../Helpers/notificationManager');

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
         if ((this._mobile && this._otp) || (this._email && this._password)) {
            database.runSp(constants.SP_VALIDATE_LOGIN, [this._mobile, this._otp, this._email, this._password])
               .then(_resultSet => {
                  const result = _resultSet[0][0];
                  if (validators.validateUndefined(result) && result.id !== -1) {
                     result[constants.JW_TOKEN] = getToken(result);
                     resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
                  } else {
                     resolve([constants.RESPONSE_SUCESS_LEVEL_1, {"id": -1},]);
                  }
               }).catch(err => {
               printer.printError(err);
               reject([constants.ERROR_LEVEL_3, err]);
            });
         } else if (this._mobile) {
         
         }
      });
   }
   
   /**
    * Method to request a 2 factor OTP.
    * @returns {Promise<Array>}:
    */
   request2Factor() {
      return new Promise((resolve, reject) => {
         try {
            this._otp = generator.generateOTP();
            const validity = generator.generateAheadTime(3);
            database.runSp(constants.SP_OTP_CREATE_CHECK, [this._mobile, this._otp, validity, 0])
               .then(async _resultSet => {
                  const otpMessage = constants.OTP_MESSAGE + " " + this._otp;
                  await notificationManager.sendSMS(otpMessage, this._mobile);
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, true]);
               }).catch(err => {
               printer.printError(err);
               reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
            });
         } catch (e) {
            printer.printError(e);
            reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
         }
      });
   }
}

/**
 * Exporting the authentication module.
 * @type {Authentication}
 */
module.exports = Authentication;