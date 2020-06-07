const vendorHandler = {};
const constants = require('./../Helpers/constants');
const validator = require('./../Helpers/validators');
const printer = require('./../Helpers/printer');
const responseGenerator = require('./../Services/responseGenerator');

const Vendor = require('./../Entity/vendor');
/**
 * Method to handle all the vendor requests.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}
 */
vendorHandler.vendor = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_GET) {
         const vendorId = validator.validateNumber(dataObject.queryString[constants.VENDOR_ID]) ?
            dataObject.queryString[constants.VENDOR_ID] : false;
         const email = validator.validateEmail(dataObject.queryString[constants.VENDOR_EMAIL]) ?
            dataObject.queryString[constants.VENDOR_EMAIL] : false;
         const phone = validator.validatePhone(dataObject.queryString[constants.VENDOR_PHONE_NUMBER]) ?
            dataObject.queryString[constants.VENDOR_PHONE_NUMBER] : false;
         const jwToken = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (jwToken && (vendorId || email || phone)) {
            const vendor = new Vendor(vendorId, false, false, false, phone, email);
            vendor.getVendor().then(vendorDetails => {
               resolve(vendorDetails[1], vendorDetails[0]);
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else if (method === constants.HTTP_POST) {
         const firstName = validator.validateString(dataObject.postData[constants.VENDOR_FIRST_NAME]) ?
            dataObject.postData[constants.VENDOR_FIRST_NAME] : false;
         const lastName = validator.validateString(dataObject.postData[constants.VENDOR_LAST_NAME]) ?
            dataObject.postData[constants.VENDOR_LAST_NAME] : false;
         const email = validator.validateEmail(dataObject.postData[constants.VENDOR_EMAIL]) ?
            dataObject.postData[constants.VENDOR_EMAIL] : false;
         const phone = validator.validatePhone(dataObject.postData[constants.VENDOR_PHONE_NUMBER]) ?
            dataObject.postData[constants.VENDOR_PHONE_NUMBER] : false;
         const gender = validator.validateCharacter(dataObject.postData[constants.VENDOR_GENDER]) ?
            dataObject.postData[constants.VENDOR_GENDER] : false;
         const vendorType = validator.validateString(dataObject.postData[constants.VENDOR_TYPE]) ?
            dataObject.postData[constants.VENDOR_TYPE] : false;
         const brandName = validator.validateString(dataObject.postData[constants.VENDOR_BRAND_NAME]) ?
            dataObject.postData[constants.VENDOR_BRAND_NAME] : false;
         const address1 = validator.validateString(dataObject.postData[constants.VENDOR_ADDRESS_1]) ?
            dataObject.postData[constants.VENDOR_ADDRESS_1] : false;
         const address2 = validator.validateString(dataObject.postData[constants.VENDOR_ADDRESS_2]) ?
            dataObject.postData[constants.VENDOR_ADDRESS_2] : false;
         const pinCode = validator.validateNumber(dataObject.postData[constants.VENDOR_PINCODE]) ?
            dataObject.postData[constants.VENDOR_PINCODE] : false;
         const cityId = validator.validateNumber(dataObject.postData[constants.VENDOR_CITY]) ?
            dataObject.postData[constants.VENDOR_CITY] : false;
         const gpsLat = validator.validateNumber(dataObject.postData[constants.VENDOR_GPS_LAT]) ?
            dataObject.postData[constants.VENDOR_GPS_LAT] : false;
         const gpsLong = validator.validateNumber(dataObject.postData[constants.VENDOR_GPS_LAT]) ?
            dataObject.postData[constants.VENDOR_GPS_LAT] : false;
         const documentType = validator.validateString(dataObject.postData[constants.DOCUMENT_TYPE]) ?
            dataObject.postData[constants.DOCUMENT_TYPE] : false;
         const documentNumber = validator.validateString(dataObject.postData[constants.DOCUMENT_ID_NUMBER]) ?
            dataObject.postData[constants.DOCUMENT_ID_NUMBER] : false;
         const password = validator.validateString(dataObject.postData[constants.VENDOR_PASSWORD]) ?
            dataObject.postData[constants.VENDOR_PASSWORD] : false;
         if (firstName && lastName && gender && phone && email && vendorType && brandName && address1 && address2
            && cityId && pinCode && gpsLat && gpsLong && documentType && documentNumber) {
            const vendor = new Vendor(false, firstName, lastName, gender, phone, email);
            vendor.createVendor(vendorType, brandName, address1, address2, cityId, pinCode, gpsLat, gpsLong, "",
               documentType, documentNumber, password).then(response => {
               resolve(response[1], response[0]);
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else if (method === constants.HTTP_PUT) {
         const vendorId = validator.validateNumber(dataObject.postData[constants.VENDOR_ID]) ?
            dataObject.postData[constants.VENDOR_ID] : false;
         const jwToken = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         const email = validator.validateEmail(dataObject.postData[constants.VENDOR_EMAIL]) ?
            dataObject.postData[constants.VENDOR_EMAIL] : false;
         const password = validator.validateString(dataObject.postData[constants.VENDOR_PASSWORD]) ?
            dataObject.postData[constants.VENDOR_PASSWORD] : false;
         const phone = validator.validatePhone(dataObject.postData[constants.VENDOR_PHONE_NUMBER]) ?
            dataObject.postData[constants.VENDOR_PHONE_NUMBER] : false;
         const brandName = validator.validateString(dataObject.postData[constants.VENDOR_BRAND_NAME]) ?
            dataObject.postData[constants.VENDOR_BRAND_NAME] : false;
         const address1 = validator.validateString(dataObject.postData[constants.VENDOR_ADDRESS_1]) ?
            dataObject.postData[constants.VENDOR_ADDRESS_1] : false;
         const address2 = validator.validateString(dataObject.postData[constants.VENDOR_ADDRESS_2]) ?
            dataObject.postData[constants.VENDOR_ADDRESS_2] : false;
         const pinCode = validator.validateNumber(dataObject.postData[constants.VENDOR_PINCODE]) ?
            dataObject.postData[constants.VENDOR_PINCODE] : false;
         const cityId = validator.validateNumber(dataObject.postData[constants.VENDOR_CITY]) ?
            dataObject.postData[constants.VENDOR_CITY] : false;
         const gpsLat = validator.validateNumber(dataObject.postData[constants.VENDOR_GPS_LAT]) ?
            dataObject.postData[constants.VENDOR_GPS_LAT] : false;
         const gpsLong = validator.validateNumber(dataObject.postData[constants.VENDOR_GPS_LAT]) ?
            dataObject.postData[constants.VENDOR_GPS_LAT] : false;
         if (vendorId && jwToken && (email || phone || password || brandName || address1 ||
            address2 || pinCode || cityId || gpsLong || gpsLat)) {
            const vendor = new Vendor(vendorId, false, false, false, phone, email);
            vendor.updateVendor(password, brandName, address1, address2, pinCode, cityId, gpsLat,
               gpsLong, jwToken).then(response => {
               resolve(response[1], response[0]);
            }).catch(err => {
               printer.printError(err);
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
 * Method to handle the vendor bank details.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}:
 */
vendorHandler.details = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_GET) {
         const vendorId = validator.validateNumber(dataObject.queryString[constants.VENDOR_ID]) ?
            dataObject.queryString[constants.VENDOR_ID] : false;
         const jwToken = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (vendorId && jwToken) {
            const vendor = new Vendor(vendorId);
            vendor.getBankDetails(jwToken).then(response => {
               resolve(response[1], response[0]);
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else if (method === constants.HTTP_PUT) {
         const accountHolderName = validator.validateString(dataObject.postData[constants.BANK_ACCOUNT_HOLDER_NAME]) ?
            dataObject.postData[constants.BANK_ACCOUNT_HOLDER_NAME] : false;
         const accountNumber = validator.validateUndefined(dataObject.postData[constants.BANK_ACCOUNT_ACCOUNT_NUMBER]) ?
            dataObject.postData[constants.BANK_ACCOUNT_ACCOUNT_NUMBER] : false;
         const bankName = validator.validateString(dataObject.postData[constants.BANK_ACCOUNT_BANK_NAME]) ?
            dataObject.postData[constants.BANK_ACCOUNT_BANK_NAME] : false;
         const ifscCode = validator.validateString(dataObject.postData[constants.BANK_ACCOUNT_IFSC_CODE]) ?
            dataObject.postData[constants.BANK_ACCOUNT_IFSC_CODE] : false;
         const contactNumber = validator.validatePhone(dataObject.postData[constants.BANK_ACCOUNT_CONTACT_NUMBER]) ?
            dataObject.postData[constants.BANK_ACCOUNT_CONTACT_NUMBER] : false;
         const vendorId = validator.validateNumber(dataObject.postData[constants.VENDOR_ID]) ?
            dataObject.postData[constants.VENDOR_ID] : false;
         const jwToken = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (accountHolderName && accountNumber && bankName && ifscCode && jwToken) {
            const vendor = new Vendor(vendorId);
            vendor.updateBankDetails(accountHolderName, bankName, ifscCode, contactNumber, jwToken).then(response => {
               resolve(response[1], response[0]);
            }).catch(err => {
               printer.printError(err);
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
 * Exporting the vendor handler.
 */
module.exports = vendorHandler;
