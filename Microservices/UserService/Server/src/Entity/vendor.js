const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const generator = require('./../Services/generator');
const validators = require('./../Helpers/validators');
const tokenGenerator = require('./../Services/jwTokenGenerator');
const s3Helper = require('./../Helpers/s3Helper');

const CacheHelper = require('./../Helpers/cacheHelper');

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
    * @param password: the vendor password.
    * @returns {Promise<unknown>}
    */
   createVendor(vendorType, brandName, address1, address2, cityId, pincode, gpsLat, gpsLong, usedReferralCode, documentType, documentId, password) {
      return new Promise((resolve, reject) => {
         const referralCode = generator.generateRandomToken(8);
         database.runSp(constants.SP_CREATE_VENDOR, [this._firstName, this._lastName, this._email, this._phone, this._gender,
            vendorType, brandName, address1, address2, cityId, pincode, gpsLat, gpsLong,
            usedReferralCode, referralCode, documentType, documentId, password])
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
                  response[constants.AUTH_ROLE] = constants.ROLE_VENDOR;
                  response[constants.VENDOR_BRAND_NAME] = brandName;
                  response[constants.JW_TOKEN] = tokenGenerator.getToken(response);
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, response]);
               } else {
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, {"id": -1}]);
               }
            }).catch(err => {
            printer.printError(err);
            reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
         });
      });
   }

   /**
    * Method to get the vendor details.
    * @returns {Promise<Array>}: The response code and the response object.
    */
   getVendor() {
      return new Promise(async (resolve, reject) => {
         try {
            //Checking the cache.
            const cache = new CacheHelper();
            if (this._id) {
               const data = await cache.getData(constants.VENDOR_ID);
               if (validators.validateUndefined(data)) {
                  return resolve([constants.RESPONSE_SUCESS_LEVEL_1, data]);
               }
            }
            //If cache miss.
            database.runSp(constants.SP_GET_VENDOR, [this._id, this._email, this._phone]).then(_resultSet => {
               const result = _resultSet[0][0];
               if (validators.validateUndefined(result)) {
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
               } else {
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, {"id": -1}]);
               }
               //storing data in cache.
               if (this._id) {
                  cache.storeData(constants.VENDOR_ID, result);
               }
            }).catch(err => {
               printer.printError(err);
               reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
            });
         } catch (e) {
            printer.printError(e);
            reject(e);
         }
      });
   }

   /**
    * Method to update the vendor details.
    * @param password: The password of the vendor.
    * @param brandName: The brand name.
    * @param address1: The address line 1.
    * @param address2: The address line 2.
    * @param pinCode: The pincode.
    * @param city: The city.
    * @param gpsLat: The GPS coordinates.
    * @param gpsLong: The GPS coordinates.
    * @param jwToken: the JW Token.
    * @returns {Promise<Array>}: The response code and the object.
    */
   updateVendor(password, brandName, address1, address2, pinCode, city, gpsLat, gpsLong, jwToken) {
      return new Promise((resolve, reject) => {
         const token = tokenGenerator.validateToken(jwToken);
         if (token[constants.ID] === this._id) {
            database.runSp(constants.SP_UPDATE_VENDOR, [this._id, this._email, password, this._phone,
               brandName, address1, address2, pinCode, city, gpsLat, gpsLong]).then(_resultSet => {
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
    * Method to create or update the bank details of the vendor.
    * @param accountHolderName: The account holder name.
    * @param accountNumber: the account number of the user.
    * @param bankName: The bank name.
    * @param ifscCode: The IFSC Code.
    * @param contactNumber: The number of the vendor.
    * @param jwToken: the Jw token.
    * @returns {Promise<Array>}: The response code and the object.
    */
   updateBankDetails(accountHolderName, accountNumber, bankName, ifscCode, contactNumber, jwToken) {
      return new Promise((resolve, reject) => {
         const token = tokenGenerator.validateToken(jwToken);
         if (token[constants.ID] === this._id) {
            database.runSp(constants.SP_BANK_DETAILS, [this._id, "tbl_VendorMaster", accountHolderName,
               accountNumber, bankName, ifscCode, contactNumber, "", 0, 1]).then(_resultSet => {
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, _resultSet[0][0]]);
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
    * Method to get the bank details.
    * @param jwToken: The token of the user.
    * @returns {Promise<unknown>}
    */
   getBankDetails(jwToken) {
      return new Promise((resolve, reject) => {
         const token = tokenGenerator.validateToken(jwToken);
         if (token[constants.ID] === this._id) {
            database.runSp(constants.SP_GET_BANK_DETAILS, [this._id]).then(_resultSet => {
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, _resultSet[0][0]]);
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
    * Method to upload the images for vendor.
    * This method is used to upload the profile picture
    * and other images for the vendor.
    * @param imageData: The image data.
    * @param imageType: The type of the images.
    * @param fileExtension: the extension of the file.
    * @param jwToken: the token of the user.
    * @returns {Promise<URL>}: The url of the profile picture.
    */
   uploadPictures(imageData, imageType, fileExtension, jwToken) {
      return new Promise((resolve, reject) => {
         const tokenData = tokenGenerator.validateToken(jwToken);
         if (tokenData[constants.ID] === this._id) {
            const imageKey = generator.generateRandomToken(16) + "." + fileExtension;
            const isSecure = (imageType === constants.IMAGE_TYPE_DOCUMENT);
            const baseUrl = (isSecure) ? constants.DOCUMENTS_BASE_URL : constants.IMAGES_BASE_URL;
            const imageUrl = baseUrl + imageKey;
            let promises = [];
            promises.push(s3Helper.uploadFile(imageData, imageKey, isSecure));
            promises.push(database.runSp(constants.SP_VENDOR_UPDATE_IMAGE,
               [imageType, imageKey, imageUrl, this._id]));
            Promise.all(promises).then(results => {
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, imageUrl]);
            }).catch(errs => {
               printer.printError(errs);
               reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
            });
         } else {
            reject([constants.ERROR_LEVEL_4, constants.FORBIDDEN_MESSAGE]);
         }
      });
   }

   /**
    * Method to get the images of the vendor.
    * It can be used to search for profile image or other images.
    * @param imageType: The type of the images.
    * @param jwToken: The token of the user.
    * @returns {Promise<Array>}: An array of images.
    */
   getImages(imageType, jwToken) {
      return new Promise((resolve, reject) => {
         const tokenData = tokenGenerator.validateToken(jwToken);
         if (tokenData[constants.ID] === this._id) {
            database.runSp(constants.SP_GET_VENDOR_IMAGES, [this._id, imageType]).then(_resultSet => {
               const result = _resultSet[0];
               if (validators.validateUndefined(result)) {
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
               } else {
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, [{"id": -1}]]);
               }
            }).catch(err => {
               printer.printError(err);
               reject([constants.ERROR_LEVEL_3, constants.ERROR_MESSAGE]);
            });
         } else {
            reject([constants.ERROR_LEVEL_4, constants.FORBIDDEN_MESSAGE]);
         }
      });
   }
}

/**
 * Exporting the class.
 */
module.exports = Vendor;