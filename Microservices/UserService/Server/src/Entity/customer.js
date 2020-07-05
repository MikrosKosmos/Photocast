const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const generator = require('./../Services/generator');
const validators = require('./../Helpers/validators');

const Cache = require('./../Helpers/cacheHelper');
const {getToken, validateToken} = require('./../Services/jwTokenGenerator');

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
      this._id = validators.validateNumber(id) ? Number(id) : false;
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
               if (this._id > 0) {
                  response[constants.ID] = this._id;
                  response[constants.CUSTOMER_FIRST_NAME] = this._firstName;
                  response[constants.CUSTOMER_LAST_NAME] = this._lastName;
                  response[constants.CUSTOMER_EMAIL] = this._email;
                  response[constants.CUSTOMER_PHONE_NUMBER] = this._phone;
                  response[constants.AUTH_ROLE] = constants.ROLE_CUSTOMER;
                  response[constants.JW_TOKEN] = getToken(response);
               } else {
                  response[constants.CUSTOMER_ID] = -1;
               }
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, response]);
            }).catch(err => {
            printer.printError(err);
            reject([constants.ERROR_LEVEL_3, err]);
         });
      });
   }

   /**
    * Method to search for customers.
    * @returns {Promise<Array>}: The response code and the search result.
    */
   getCustomer() {
      return new Promise(async (resolve, reject) => {
         const cache = new Cache();
         //Checking the cache.
         if (this._id) {
            const data = await cache.getData(constants.CUSTOMER_ID);
            if (validators.validateUndefined(data)) {
               return resolve([constants.RESPONSE_SUCESS_LEVEL_1, data]);
            }
         }
         //If cache miss.
         database.runSp(constants.SP_GET_CUSTOMER, [this._id, this._email, this._phone])
            .then(_resultSet => {
               const result = _resultSet[0][0];
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
               if (validators.validateUndefined(result) && this._id) {
                  cache.storeData(constants.CUSTOMER_ID, result);
               }
            }).catch(err => {
            printer.printError(err);
            reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
         });
      });
   }

   /**
    * Method to update the address of the customer.
    * @param address1: The address line 1 of the customer.
    * @param address2: The address line 2 of the customer.
    * @param cityId: The city id of the customer.
    * @param pincode: The pincode of the customer.
    * @param gpsLat: The latitude of the customer.
    * @param gpsLong: The longitude of the customer.
    * @param isDefault: 1 for default, else 0.
    * @param jwToken: the user token.
    * @returns {Promise<Array>}: the response code and the response.
    */
   updateCustomerAddress(address1, address2, cityId, pincode, gpsLat, gpsLong, isDefault, jwToken) {
      return new Promise((resolve, reject) => {
         const token = validateToken(jwToken);
         if (token[constants.ID] === this._id) {
            database.runSp(constants.SP_UPDATE_CUSTOMER_ADDRESS, [
               this._id,
               validators.validateString(address1) ? address1 : false,
               validators.validateString(address2) ? address2 : false,
               validators.validateNumber(cityId) ? cityId : false,
               validators.validateNumber(pincode) ? pincode : false,
               gpsLat, gpsLong,
               validators.validateNumber(isDefault) ? isDefault : 0
            ]).then(_resultSet => {
               const result = _resultSet[0][0];
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
            }).catch(err => {
               printer.printError(err);
               reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
            });
         } else {
            reject([constants.ERROR_LEVEL_4, constants.FORBIDDEN_MESSAGE]);
         }
      });
   }

   /**
    * Method to update the customer details.
    * @param password: The password of the user.
    * @param jwToken: the user token.
    * @returns {Promise<Array>}
    */
   updateCustomerDetails(password, jwToken) {
      return new Promise((resolve, reject) => {
         const tokenData = validateToken(jwToken);
         if (tokenData[constants.ID] === this._id) {
            database.runSp(constants.SP_UPDATE_CUSTOMER_DETAILS, [this._firstName, this._lastName, this._email,
               this._phone, password, this._id])
               .then(_resultSet => {
                  const result = _resultSet[0][0];
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
               }).catch(err => {
               printer.printError(err);
               reject([constants.ERROR_LEVEL_3, err]);
            });
         } else {
            reject([constants.ERROR_LEVEL_4, constants.FORBIDDEN_MESSAGE]);
         }
      });
   }

   /**
    * Method to get the Customer address.
    * @param jwToken: The token of the user.
    * @returns {Promise<Array>}: The response object and the response.
    */
   getCustomerAddress(jwToken) {
      return new Promise((resolve, reject) => {
         const token = validateToken(jwToken);
         if (token[constants.ID] === this._id) {
            database.runSp(constants.SP_GET_CUSTOMER_ADDRESS, [this._id]).then(_resultSet => {
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, _resultSet[0]]);
            }).catch(err => {
               printer.printError(err);
               reject([constants.ERROR_LEVEL_3, cons])
            });
         } else {
            reject([constants.FORBIDDEN_REQUEST_CODE, constants.FORBIDDEN_MESSAGE]);
         }
      });
   }
}

/**
 * Exporting the customer.
 */
module.exports = Customer;