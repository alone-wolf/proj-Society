const STATUS_404 = {
  code: 404,
  message: "Not Found",
  data: {},
};

const STATUS_403 = {
  code: 403,
  message: "UnAuthed",
  data: {},
};

const STATUS_400 = {
  code: 400,
  message: "Bad Request",
  data: {},
};

const STATUS_500 = {
  code: 500,
  message: "Internal Server Error",
  data: {},
};

const STATUS_200 = function (data = {}) {
  return { code: 200, message: "OK", data };
};

module.exports = { STATUS_404, STATUS_403, STATUS_400, STATUS_500, STATUS_200 };
