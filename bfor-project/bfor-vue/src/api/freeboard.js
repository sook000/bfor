import { localAxios } from "@/util/http-commons.js";

const local = localAxios();
const defaultURL = `/freeboard`;

function listArticle(param, success, fail) {
  local.get(defaultURL, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.get(`${defaultURL}/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  local.post(defaultURL, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
  local.get(`${defaultURL}/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.put(defaultURL, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.delete(`${defaultURL}/${articleno}`).then(success).catch(fail);
}

function listComment(articleno, success, fail) {
  local.get(`${defaultURL}/comment/${articleno}`).then(success).catch(fail);
}
function registComment(comment, success, fail) {
  console.log("boardjs comment", comment);
  local.post(`${defaultURL}/comment`, JSON.stringify(comment)).then(success).catch(fail);
}
function modifyComment(comment, success, fail) {
  local.put(`${defaultURL}/comment`, JSON.stringify(comment)).then(success).catch(fail);
}
function deleteComment(param, success, fail) {
  local.delete(`${defaultURL}/comment`, { params: param }).then(success).catch(fail);
}
function countComment(articleno, success, fail) {
  local.get(`${defaultURL}/comment/list/${articleno}`).then(success).catch(fail);
}
export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  listComment,
  registComment,
  modifyComment,
  deleteComment,
  countComment,
};
