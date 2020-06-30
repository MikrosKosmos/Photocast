const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const generator = require('./../Services/generator');
const validators = require('./../Helpers/validators');
const encrypterDecrypter = require('./../Helpers/encrypterDecrypter');
const s3Helper = require('./../Helpers/s3Helper');

const NetworkHelper = require('./../Helpers/networkHelper');

class Post {
   /**
    * _id
    * _vendorId
    * _firstName
    * _lastName
    * _token
    * @param id
    * @param vendorId
    * @param firstName
    * @param lastName
    * @param token
    */
   constructor(id, vendorId, firstName, lastName, token) {
      this._id = validators.validateNumber(id) ? id : false;
      this._vendorId = validators.validateNumber(vendorId) ? vendorId : false;
      this._firstName = validators.validateString(firstName) ? firstName : false;
      this._lastName = validators.validateString(lastName) ? lastName : false;
      this._token = validators.validateString(token) ? token : false;
   }

   /**
    * Method to validate the user token.
    * @returns {Promise<Object>}: the user data if valid, else false.
    * @private
    */
   _validateUserToken() {
      return new Promise((resolve, reject) => {
         const headers = {}, body = {};
         headers[constants.API_AUTH_KEY] = encrypterDecrypter.decrypt(process.env[constants.MICROSERVICE_AUTH_KEY_VALUE]);
         body[constants.JW_TOKEN] = this._token;
         const networkHelper = new NetworkHelper(constants.USER_SERVICE_HOST, constants.USER_SERVICE_VALIDATE_TOKEN_PATH,
            constants.HTTP_POST, null, body, headers, constants.USER_SERVICE_PORT);
         networkHelper.request().then(response => {
            const isValid = response[constants.RESPONSE_KEY][constants.IS_VALID];
            if (isValid) {
               this._vendorId = response[constants.RESPONSE_KEY][constants.USER_DATA][constants.ID];
               resolve(response[constants.RESPONSE_KEY][constants.USER_DATA]);
            } else {
               reject(false);
            }
         }).catch(err => {
            printer.printError(err);
            reject(err);
         });
      });
   }

   /**
    * Method to create a post for a vendor.
    * @param postDesc: The
    * @param imageData
    * @param fileExtension
    * @returns {Promise<unknown>}
    */
   createPost(postDesc, imageData, fileExtension) {
      return new Promise((resolve, reject) => {
         this._validateUserToken().then(userData => {
            if (userData[constants.AUTH_ROLE] === constants.ROLE_VENDOR) {
               const fileName = generator.generateRandomToken(8) + "." + fileExtension;
               const url = constants.IMAGES_BASE_URL + fileName;
               let promises = [];
               promises.push(s3Helper.uploadFile(imageData, fileName, false));
               promises.push(database.runSp(constants.SP_CREATE_POST, [this._vendorId, postDesc, url,
                  userData[constants.VENDOR_FIRST_NAME], userData[constants.VENDOR_LAST_NAME]]));
               Promise.all(promises).then(results => {
                  const dbResultSet = results[1];
                  const result = dbResultSet[0][0];
                  if (validators.validateUndefined(result) && result.id > 0) {
                     resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
                  } else {
                     resolve([constants.RESPONSE_SUCESS_LEVEL_1, {id: -1}]);
                  }
               }).catch(err => {
                  reject([constants.ERROR_LEVEL_3, err]);
               });
            } else {
               printer.printError("Invalid role to create a post.");
               resolve([constants.RESPONSE_SUCESS_LEVEL_1, {id: -1}]);
            }
         }).catch(err => {
            reject([constants.ERROR_LEVEL_4, err]);
         });
      });
   }

   /**
    * Method to get the posts
    * @param initialValue
    * @param limit
    * @param isSelf
    * @returns {Promise<unknown>}
    */
   getPosts(initialValue, limit, isSelf) {
      return new Promise(async (resolve, reject) => {
         this._validateUserToken().then(() => {
            database.runSp(constants.SP_GET_POST, [initialValue, limit, isSelf, this._vendorId])
               .then(_resultSet => {
                  resolve([constants.RESPONSE_SUCESS_LEVEL_1, _resultSet[0]]);
               }).catch(err => {
               printer.printError([constants.ERROR_LEVEL_3, err]);
            });
         }).catch(err => {
            reject([constants.ERROR_LEVEL_4, err]);
         });
      });
   }
}

/**
 * Exporting the post class.
 */
module.exports = Post;