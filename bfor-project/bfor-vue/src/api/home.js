import { localAxios } from "@/util/http-commons.js";
const local = localAxios();
const defaultURL = `/home`;

function listFreeArticle(success, fail) {
  local.get(`${defaultURL}/free`).then(success).catch(fail);
}

function listNoticeArticle(success, fail) {
  local.get(`${defaultURL}/notice`).then(success).catch(fail);
}

function listPlace(sidoCode, success, fail) {
  local.get(`${defaultURL}/${sidoCode}`).then(success).catch(fail);
}

export { listFreeArticle, listNoticeArticle, listPlace };
