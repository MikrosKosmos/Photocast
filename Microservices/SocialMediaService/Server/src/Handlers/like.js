const constants = require('./../Helpers/constants');
const validator = require('./../Helpers/validators');
const printer = require('./../Helpers/printer');
const responseGenerator = require('./../Services/responseGenerator');

const Like = require('./../Entity/like');
const likeHandler = {};
/**
 * Method to handle the requests for like.
 * @param dataObject: The request object.
 * @returns {Promise<unknown>}
 */
likeHandler.like = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_GET) {
         const userId = validator.validateNumber(dataObject.queryString[constants.ID]) ?
            dataObject.queryString[constants.ID] : false;
         const postId = validator.validateNumber(dataObject.queryString[constants.LIKE_POST_ID]) ?
            dataObject.queryString[constants.LIKE_POST_ID] : false;
         const token = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (userId && postId && token) {
            const like = new Like(false, postId, userId, false, token);
            like.getLikes().then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else if (method === constants.HTTP_PUT) {
         const userId = validator.validateNumber(dataObject.postData[constants.ID]) ?
            dataObject.postData[constants.ID] : false;
         const postId = validator.validateNumber(dataObject.postData[constants.LIKE_POST_ID]) ?
            dataObject.postData[constants.LIKE_POST_ID] : false;
         const token = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (userId && postId && token) {
            const like = new Like(false, postId, userId, false, token);
            like.toggleLike().then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
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
 * Exporting the like handler.
 */
module.exports = likeHandler;