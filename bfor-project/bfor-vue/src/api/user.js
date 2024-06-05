import { localAxios } from "@/util/http-commons.js";

const local = localAxios();
const defaultURL = `/user`;

function checkId(userId, success, fail) {
  local.get(`${defaultURL}/idCheck/${userId}`).then(success).catch(fail);
}

function getSido(success, fail) {
  local.get(`${defaultURL}/getSido`).then(success).catch(fail);
}

function getGugun(sidoCode, success, fail) {
  local.get(`${defaultURL}/getGugun/${sidoCode}`).then(success).catch(fail);
}
function registerUser(user, success, fail) {
  local.post(`${defaultURL}/register`, JSON.stringify(user)).then(success).catch(fail);
}

async function userConfirm(param, success, fail) {
  await local.post(`${defaultURL}/login`, param).then(success).catch(fail);
}
async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`${defaultURL}/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`${defaultURL}/refresh`, user).then(success).catch(fail);
}

async function logout(userId, success, fail) {
  await local.get(`${defaultURL}/logout/${userId}`).then(success).catch(fail);
}

function findUserId(param, success, fail) {
  local.get(`${defaultURL}/findId`, { params: param }).then(success).catch(fail);
}

function authUser(param, success, fail) {
  local.get(`${defaultURL}/authUser`, { params: param }).then(success).catch(fail);
}

function modifyPwd(user, success, fail) {
  local.put(defaultURL, JSON.stringify(user)).then(success).catch(fail);
}

export {
  checkId,
  getSido,
  getGugun,
  registerUser,
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  findUserId,
  authUser,
  modifyPwd,
};
