const constants = require('./../Helpers/constants');
const validator = require('./../Helpers/validators');
const printer = require('./../Helpers/printer');
const responseGenerator = require('./../Services/responseGenerator');

const Comment = require('./../Entity/comment');
const commentHandler = {};
/**
 * Method to handle the comments.
 * @param dataObject: The request object.
 * @returns {Promise<Array>}:
 */
commentHandler.comment = (dataObject) => {
   return new Promise((resolve, reject) => {
      const method = dataObject.method;
      if (method === constants.HTTP_GET) {
         const userId = validator.validateNumber(dataObject.queryString[constants.ID]) ?
            dataObject.queryString[constants.ID] : false;
         const postId = validator.validateNumber(dataObject.queryString[constants.COMMENT_POST_ID]) ?
            dataObject.queryString[constants.COMMENT_POST_ID] : false;
         const token = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         if (userId && token && postId) {
            const comment = new Comment(false, postId, userId, token);
            comment.getComments().then(response => {
               resolve(responseGenerator.generateResponse(response[1], response[0]));
            }).catch(err => {
               printer.printError(err);
               reject(responseGenerator.generateErrorResponse(constants.ERROR_MESSAGE, constants.ERROR_LEVEL_3));
            });
         } else {
            reject(responseGenerator.generateErrorResponse(constants.INSUFFICIENT_DATA_MESSAGE, constants.ERROR_LEVEL_1));
         }
      } else if (method === constants.HTTP_POST) {
         const userId = validator.validateNumber(dataObject.postData[constants.ID]) ?
            dataObject.postData[constants.ID] : false;
         const postId = validator.validateNumber(dataObject.postData[constants.COMMENT_POST_ID]) ?
            dataObject.postData[constants.COMMENT_POST_ID] : false;
         const token = validator.validateUndefined(dataObject[constants.JW_TOKEN]) ?
            dataObject[constants.JW_TOKEN] : false;
         const commentValue = validator.validateString(dataObject.postData[constants.COMMENT_COMMENT]) ?
            dataObject.postData[constants.COMMENT_COMMENT] : false;
         if (userId && postId && token && commentValue) {
            const comment = new Comment(false, postId, userId, token);
            comment.createComment(commentValue).then(response => {
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
 * Exporting the comment handler.
 */
module.exports = commentHandler;