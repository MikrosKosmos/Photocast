const handlers = require('./../Handlers');
const validator = require('./validators');
const routes = {
   "posts": handlers.post,
   "likes": handlers.like,
   "comments": handlers.comment
};

const path = {};
/**
 * Method to get the routed path.
 * @param requestPath: The Requested path.
 * @returns {boolean}: path if correct request else false.
 */
path.getPath = function (requestPath) {
   if (validator.validateUndefined(routes[requestPath])) {
      return routes[requestPath];
   }
   return handlers.notFound;
};
/**
 * Sending the path.
 */
module.exports = path;