const validators = require('./../../src/Helpers/validators');
test("Should validate Numbers when false", () => {
   expect(validators.validateNumber(false)).toBe(false);
});
test("Should validate Numbers when undefined", () => {
   expect(validators.validateNumber(undefined)).toBe(false);
});
test("Should validate Null in undefined check", () => {
   expect(validators.validateUndefined(null)).toBe(false);
});
test("Should validate decimal numbers", () => {
   expect(validators.validateNumber(100.23)).toBe(true);
});
test("should validate a correct url", () => {
   expect(validators.validateURL("http://google.com")).toBe(true);
});
test("should validate an incorrect url", () => {
   expect(validators.validateURL("http//google.com")).toBe(false);
});