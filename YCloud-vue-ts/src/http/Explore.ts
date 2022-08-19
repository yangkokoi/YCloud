import http from "./XMLHttpRequest"
import API from "./API"
import { SERVER_DOWNLOAD, SERVER_PRE_DOWNLOAD } from "./API"

/**
 * 获取指定目录下的所有文件
 * @param data 
 * @returns Promise
 */
export const listResource = (data: any) => {
  window.history.pushState("", "", `/explorer/${data.bucketName}?path=${data.path}`);
  return http.get(API("/resource"), data).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

/**
 * 文件删除
 * @param data 
 * @returns 
 */
 export const deleteFile = (id: string | number, name: string) => {
  return http.post(API("/resource/delete"), {
    id: id
  }).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

/**
 * 文件重命名
 * @param data 
 * @returns 
 */
export const renameFile = (id: string | number, name: string) => {
  return http.post(API("/resource/rename"), {
    id,
    name
  }).then(res => {
    if (res.code == 200) {
      return res.data;
    }
  });
}

export const download = (id: string | number) => {
  return http.post(SERVER_PRE_DOWNLOAD, {
    id: id
  }).then((res) => {
    if (res.code == 200) {
      window.open(`${SERVER_DOWNLOAD}?entry=${res.data}`);
    }
  });
}

export default {
  listResource,
  deleteFile,
  renameFile,
  download
}