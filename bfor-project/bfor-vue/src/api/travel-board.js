import { localAxios } from "@/util/http-commons";

const local = localAxios();

const defaultURL = "travelrouteboard";

function insert(param, success, fail) {
  local.post(`${defaultURL}/insert`, param).then(success).catch(fail);
}

function getList(param, success, fail) {
  local.get(`${defaultURL}/getList`, { params: param }).then(success).catch(fail);
}

function getMaxTrbId(param, success, fail) {
  local.get(`${defaultURL}/getMaxTrbId/${param}`).then(success).catch(fail);
}

function read(param, success, fail) {
  local.get(`${defaultURL}/${param}`).then(success).catch(fail);
}

function update(param, success, fail) {
  local.post(`${defaultURL}/update`, param).then(success).catch(fail);
}

function deleteBoard(param, success, fail) {
  local.post(`${defaultURL}/${param}`).then(success).catch(fail);
}

function getOrderInfo(param, success, fail) {
  local.get(`${defaultURL}/getOrderInfo/${param}`).then(success).catch(fail);
}

function getNormalDistance(param, success, fail) {
  local.post(`${defaultURL}/getNormalDistance`, param).then(success).catch(fail);
}

function getDijkstraDistance(param, success, fail) {
  local.post(`${defaultURL}/getDijkstraDistance`, param).then(success).catch(fail);
}

function getDijkstraDistanceList(param, success, fail) {
  local.post(`${defaultURL}/getDijkstraDistanceList`, param).then(success).catch(fail);
}

function getThumbnailList(param, success, fail) {
  local.post(`${defaultURL}/getThumbnailList`, param).then(success).catch(fail);
}

export {
  insert,
  getList,
  read,
  update,
  deleteBoard,
  getMaxTrbId,
  getOrderInfo,
  getNormalDistance,
  getDijkstraDistance,
  getDijkstraDistanceList,
  getThumbnailList,
};
