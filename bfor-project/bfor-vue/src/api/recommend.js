import { localAxios } from "@/util/http-commons.js";
const local = localAxios();
const defaultURL = `/recommend`;

function listPlace(userId, success, fail) {
  local.get(`${defaultURL}/${userId}`).then(success).catch(fail);
}

export { listPlace };
