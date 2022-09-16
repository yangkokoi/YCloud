import http from "./XMLHttpRequest"
import API from "./API"
import { SERVER_DOWNLOAD, SERVER_PRE_DOWNLOAD, SERVER_PREVIEW } from "./API"
import { main } from '@/store/main'

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
 * 文件创建
 * @param data 
 * @returns 
 */
 export const createFile = (data: any) => {
  return http.post(API("/resource/create"), data).then(res => {
    if (res.code == 200) {
      window.$message.success(res.msg);
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

export const preview = (uuid: string) => {
  return main().getUnsafeToken().then(token => {
    // window.open(`${SERVER_PREVIEW}/${uuid}?token=${token}`);
    return `${SERVER_PREVIEW}/${uuid}?token=${token}`;
  });
  
}

export default {
  listResource,
  createFile,
  deleteFile,
  renameFile,
  download,
  preview
}