import { localAxios } from "@/util/http-commons.js";
const local = localAxios();
const defaultURL = `/mypage`;

function listFreeArticle(userId, success, fail) {
  local.get(`${defaultURL}/${userId}`).then(success).catch(fail);
}

function getSidoGugun(param, success, fail) {
  local.get(`${defaultURL}/sido`, { params: param }).then(success).catch(fail);
}

function modifyUser(user, success, fail) {
  local.put(defaultURL, JSON.stringify(user)).then(success).catch(fail);
}

function deleteUser(user, success, fail) {
  local.delete(defaultURL, { params: user }).then(success).catch(fail);
}

function listzzim(userId, success, fail) {
  local.get(`${defaultURL}/zzimList/${userId}`).then(success).catch(fail);
}
function listroute(userId, success, fail) {
  local.get(`${defaultURL}/listroute/${userId}`).then(success).catch(fail);
}

function howLike(param, success, fail) {
  local.get(`${defaultURL}/like`, { params: param }).then(success).catch(fail);
}
export { listFreeArticle, getSidoGugun, modifyUser, deleteUser, listzzim, listroute, howLike };
