const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const generator = require('./../Services/generator');
const validators = require('./../Helpers/validators');
const s3Helper = require('./../Helpers/s3Helper');
const tokenValidator = require('./../Helpers/tokenValidation');

class Like {
   /**
    * _id
    * _postId
    * _userId
    * _role
    * @param id
    * @param postId
    * @param userId
    * @param role
    * @param token
    */
   constructor(id, postId, userId, role, token) {
      this._id = validators.validateNumber(id) ? id : false;
      this._postId = validators.validateNumber(postId) ? postId : false;
      this._userId = validators.validateNumber(userId) ? userId : false;
      this._role = validators.validateString(role) ? role : false;
      this._token = validators.validateUndefined(token) ? token : false;
   }

   /**
    * Method to validate the user token.
    * @returns {Promise<Object>}: The user data.
    * @private
    */
   _validateUserToken() {
      return new Promise((resolve, reject) => {
         tokenValidator.validateToken(this._token, this._userId).then(userData => {
            resolve(userData);
         }).catch(err => {
            printer.printError(err);
            reject(err);
         });
      });
   }

   /**
    * Method to like or unlike a post.
    * @returns {Promise<Array>}:response success level and response.
    */
   toggleLike() {
      return new Promise(async (resolve, reject) => {
         try {
            const userData = await this._validateUserToken();
            const firstName = userData[constants.VENDOR_FIRST_NAME];
            const lastName = userData[constants.VENDOR_LAST_NAME];
            database.runSp(constants.SP_LIKE_UNLIKE, [this._postId, this._userId, firstName, lastName, this._role])
               .then(_resultSet => {
                  const result = _resultSet[0][0];
                  if (validators.validateUndefined(result)) {
                     resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
                  } else {
                     resolve([constants.RESPONSE_SUCESS_LEVEL_1, {id: -1}]);
                  }
               }).catch(err => {
               reject([constants.ERROR_LEVEL_3, err]);
            });
         } catch (e) {
            reject([constants.ERROR_LEVEL_4, e]);
         }
      });
   }

   /**
    * Method to get the likes of the post.
    * @returns {Promise<Array>}: the response code and the response.
    */
   getLikes() {
      return new Promise(async (resolve, reject) => {
         try {
            const userData = await this._validateUserToken();
            if (validators.validateUndefined(userData)) {
               database.runSp(constants.SP_GET_LIKES, [this._postId]).then(_resultSet => {
                  const result = _resultSet[0];
                  if (validators.validateUndefined(result)) {
                     resolve([constants.RESPONSE_SUCESS_LEVEL_1, result]);
                  } else {
                     resolve([constants.RESPONSE_SUCESS_LEVEL_1, []]);
                  }
               }).catch(err => {
                  reject([constants.ERROR_LEVEL_3, err]);
               });
            } else {
               reject([constants.ERROR_LEVEL_4, constants.FORBIDDEN_MESSAGE]);
            }
         } catch (e) {
            reject([constants.ERROR_LEVEL_4, e]);
         }
      });
   }
}

/**
 * Exporting the Like Class.
 */
module.exports = Like;