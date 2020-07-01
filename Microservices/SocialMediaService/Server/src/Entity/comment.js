const constants = require('./../Helpers/constants');
const database = require('./../Services/databaseService');
const printer = require('./../Helpers/printer');
const validators = require('./../Helpers/validators');
const tokenValidator = require('./../Helpers/tokenValidation');

class Comment {
   /**
    * _id
    * _postId
    * _userId
    * _token
    * @param id
    * @param postId
    * @param userId
    * @param token
    */
   constructor(id, postId, userId, token) {
      this._id = validators.validateNumber(id) ? id : false;
      this._postId = validators.validateNumber(postId) ? postId : false;
      this._userId = validators.validateNumber(userId) ? userId : false;
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
    * Method to create comment for a post.
    * @param comment: The comment.
    * @returns {Promise<Array>}:
    */
   createComment(comment) {
      return new Promise(async (resolve, reject) => {
         try {
            const userData = await this._validateUserToken();
            const firstName = userData[constants.VENDOR_FIRST_NAME];
            const lastName = userData[constants.VENDOR_LAST_NAME];
            const role = userData[constants.AUTH_ROLE];
            database.runSp(constants.SP_CREATE_COMMENT, [this._postId, role, this._userId, firstName, lastName, comment])
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
}

/**
 * Exporting the comment class.
 * @type {Comment}
 */
module.exports = Comment;