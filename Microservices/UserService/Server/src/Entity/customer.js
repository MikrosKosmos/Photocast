const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const generator = require('./../Services/generator');
const validators = require('./../Helpers/validators');

const {getToken} = require('./../Services/jwTokenGenerator');

class Customer {
   /**
    * _id
    * _firstName
    * _lastName
    * _gender
    * _email
    * _phone
    * @param id
    * @param firstName
    * @param lastName
    * @param gender
    * @param email
    * @param phone
    */
   constructor(id, firstName, lastName, gender, email, phone) {
      this._id = validators.validateNumber(id) ? id : false;
      this._firstName = validators.validateString(firstName) ? firstName : false;
      this._lastName = validators.validateString(lastName) ? lastName : false;
      this._gender = validators.validateCharacter(gender) ? gender : false;
      this._email = validators.validateEmail(email) ? email : false;
      this._phone = validators.validatePhone(phone) ? phone : false;
   }
   
   /**
    * Method to create the customer.
    * @param usedReferralCode: The referral code used by the customer to signup.
    * @returns {Promise<unknown>}
    */
   createCustomer(usedReferralCode) {
      return new Promise((resolve, reject) => {
         const referralCode = generator.generateRandomToken(8);
         database.runSp(constants.SP_CREATE_CUSTOMER, [this._firstName, this._lastName,
               this._email, false, this._phone, this._gender,
               validators.validateString(usedReferralCode) ? usedReferralCode : false, referralCode])
            .then(_resultSet => {
               const result = _resultSet[0][0];
               this._id = result.id;
               let response = {};
               response[constants.CUSTOMER_ID] = this._id;
               response[constants.CUSTOMER_FIRST_NAME] = this._firstName;
               response[constants.CUSTOMER_LAST_NAME] = this._lastName;
               response[constants.CUSTOMER_EMAIL] = this._email;
               response[constants.CUSTOMER_PHONE_NUMBER] = this._phone;
               response[constants.JW_TOKEN] = getToken(response);
               resolve([constants.HTTP_SUCCESS, response]);
            }).catch(err => {
            printer.printError(err);
            reject([constants.INTERNAL_SERVER_ERROR_CODE, err]);
         });
      });
   }
}

/**
 * Exporting the customer.
 * @type {Customer}
 */
module.exports = Customer;