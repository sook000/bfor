import { localAxios } from "@/util/http-commons";

const local = localAxios();

const defaultURL = "travelinfo";

function getList(param, success, fail) {
  local.get(`${defaultURL}/search`, { params: param }).then(success).catch(fail);
}

function getDetail(content_id, success, fail) {
  local.get(`${defaultURL}/${content_id}`).then(success).catch(fail);
}

function getMyFav(param, success, fail) {
  local.get(`${defaultURL}/myFav`, { params: param }).then(success).catch(fail);
}

function checkFav(param, success, fail) {
  local.get(`${defaultURL}/checkFav`, { params: param }).then(success).catch(fail);
}

function clickFav(param, success, fail) {
  local.post(`${defaultURL}/clickFav`, param).then(success).catch(fail);
}

function getBest(param, success, fail) {
  local.get(`${defaultURL}/best`, { params: param }).then(success).catch(fail);
}

export { getDetail, getList, getMyFav, checkFav, clickFav, getBest };
