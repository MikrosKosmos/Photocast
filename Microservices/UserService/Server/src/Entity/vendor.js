const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const generator = require('./../Services/generator');
const validators = require('./../Helpers/validators');
const tokenGenerator = require('./../Services/jwTokenGenerator');

class Vendor {
   /**
    * _id
    * _firstName
    * _lastName
    * _gender
    * _phone
    * _email
    * @param id
    * @param firstName
    * @param lastName
    * @param gender
    * @param phone
    * @param email
    */
   constructor(id, firstName, lastName, gender, phone, email) {
      this._id = validators.validateNumber(id) ? Number(id) : false;
      this._firstName = validators.validateString(firstName) ? firstName : false;
      this._lastName = validators.validateString(lastName) ? lastName : false;
      this._gender = validators.validateCharacter(gender) ? gender : false;
      this._phone = validators.validatePhone(phone) ? phone : false;
      this._email = validators.validateEmail(email) ? email : false;
   }
   
   /**
    * Method to create a new Vendor.
    * @param vendorType: The type of the vendor.
    * @param brandName: The brand name of the vendor.
    * @param address1: The address line 1.
    * @param address2: The address line 2.
    * @param cityId: The city Id.
    * @param pincode: The pincode.
    * @param gpsLat: The GPS Lat.
    * @param gpsLong: The GPS Long.
    * @param usedReferralCode: The referral code he used.
    * @param documentType: The type of identification document.
    * @param documentId: The identification document id.
    * @returns {Promise<unknown>}
    */
   createVendor(vendorType, brandName, address1, address2, cityId, pincode, gpsLat, gpsLong, usedReferralCode, documentType, documentId) {
      return new Promise((resolve, reject) => {
         const referralCode = generator.generateRandomToken(8);
         database.runSp(constants.SP_CREATE_VENDOR, [this._firstName, this._lastName, this._email, this._phone, this._gender,
               vendorType, brandName, address1, address2, cityId, pincode, gpsLat, gpsLong,
               usedReferralCode, referralCode, documentType, documentId])
            .then(_resultSet => {
               const result = _resultSet[0][0];
               if (validators.validateUndefined(result) && result[constants.ID] > 0) {
                  let response = {};
                  this._id = result[constants.ID];
                  response[constants.ID] = this._id;
                  response[constants.VENDOR_FIRST_NAME] = this._firstName;
                  response[constants.VENDOR_LAST_NAME] = this._lastName;
                  response[constants.VENDOR_PHONE_NUMBER] = this._phone;
                  response[constants.VENDOR_GENDER] = this._gender;
                  response[constants.VENDOR_EMAIL] = this._email;
                  response[constants.VENDOR_TYPE] = vendorType;
                  response[constants.VENDOR_BRAND_NAME] = brandName;
                  response[constants.JW_TOKEN] = tokenGenerator.getToken(response);
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, response]);
               } else {
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, {"id": 1}]);
               }
            }).catch(err => {
            printer.printError(err);
            reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
         });
      });
   }
}

/**
 * Exporting the class.
 * @type {Vendor}
 */
module.exports = Vendor;