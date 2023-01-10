<template>
  <uploader
    ref="uploader"
    :options="options"
    :auto-start="false"
    :file-status-text="fileStatusText"
    @file-added="onFileAdded"
    @file-success="onFileSuccess"
    @file-error="onFileError"
    @file-progress="onFileProgress"
  >
    <uploader-unsupport />
    <uploader-drop>
      <uploader-btn @click="uploadBtnHandler">
        <slot>上传</slot>
      </uploader-btn>
    </uploader-drop>
    <uploader-list class="uploader-list">
      <n-collapse
        accordion
        :default-expanded-names="['1']"
      >
        <n-collapse-item
          title="文件列表"
          name="1"
        >
          <div
            v-if="!uploadFileList.length"
            class="no-file"
          >
            <i class="icon icon-empty-file" /> 暂无待上传文件
          </div>
          <uploader-file
            v-for="file in uploadFileList"
            v-else
            :key="file.id"
            :file="file"
            :list="true"
          >
            <template #default="detail">
              <div style="display: flex;align-items: center;height: 100%;">
                <div style="width: 100%;margin-right: 10px;">
                  <div>{{ showDetail(detail) }}</div>
                  <div style="display: flex;align-items: center;">
                    <span style="flex: none;">{{ showSpeed(detail) }}</span>
                    <n-progress
                      style="margin: 5px;"
                      type="line"
                      indicator-placement="inside"
                      :stroke-width="18"
                      format="e => showDetail(e,props)"
                      :percentage="percentage(detail)"
                      :color="progressColor(detail)"
                    />
                    <span style="flex: none;">{{ detail.formatedSize }}</span>
                  </div>
                </div>
                <n-button
                  v-if="detail.paused || detail.isUploading"
                  circle
                  tertiary
                  @click="pause(file)"
                >
                  <template #icon>
                    <y-icon :name="file.paused ? 'fa-circle-play' : 'fa-circle-pause'" />
                  </template>
                </n-button>
                <n-button
                  circle
                  tertiary
                  @click="remove(file)"
                >
                  <template #icon>
                    <y-icon name="fa-circle-xmark" />
                  </template>
                </n-button>
              </div>
            </template>
          </uploader-file>
        </n-collapse-item>
      </n-collapse>
    </uploader-list>
  </uploader>
</template>

<script lang="ts">
import { SERVER_UPLOAD } from "@/http/API";
import uploader from "vue-simple-uploader";
import "vue-simple-uploader/dist/style.css";
import user from "@/store/user";
import { uploadCheck } from "@/http/Explore";

console.log(uploader);

// 分片大小，20MB
const CHUNK_SIZE = 30 * 1024 * 1024;
export default {
  components: uploader,
  props: {
    uploadData: {
      type: Object,
      required: false,
      default: () => {}
    },
    uploadSuccess: {
      type: Function,
      required: false,
      default: () => {}
    }
  },
  data() {
    const data: {
      fileInput: HTMLElement | null,
      [keys: string]: any
    } = {
      fileInput: null,
      options: {
        // 上传地址
        target: SERVER_UPLOAD,
        // 是否开启服务器分片校验。默认为 true
        testChunks: true,
        // 真正上传的时候使用的 HTTP 方法,默认 POST
        uploadMethod: "post",
        // 分片大小
        chunkSize: CHUNK_SIZE,
        // 并发上传数，默认为 3
        simultaneousUploads: 2,
        query: (file, chunk, isTest) => {
          // console.log("a:", file, chunk, isTest);
          return {
            ...this.uploadData
          };
        },
        // 请求头
        headers: { Authorization: `Bearer ${user().token}` },
        /**
         * 判断分片是否上传，秒传和断点续传基于此方法
         * 这里根据实际业务来 用来判断哪些片已经上传过了 不用再重复上传了 [这里可以用来写断点续传！！！]
         */
        checkChunkUploadedByResponse: (chunk, message) => {
          console.log("chunk", chunk);
          // message是后台返回
          let res = JSON.parse(message);
          if (res.code !== 200) {
            throw new Error(res);
          }
          let data = res.data;
          if (data.uploaded === true) {
            return data.uploaded;
          }
          // 判断文件或分片是否已上传，已上传返回 true
          // 这里的 uploadedChunks 是后台返回
          // objMessage.uploaded_chunks = [2, 3, 4, 5, 6, 8, 10, 11, 12, 13, 17, 20, 21]
          // check the chunk is uploaded
          return (data.uploadedChunks || []).indexOf(chunk.offset + 1) >= 0;
        },
        parseTimeRemaining: function(timeRemaining, parsedTimeRemaining) {
          // 格式化时间
          return parsedTimeRemaining
            .replace(/\syears?/, "年")
            .replace(/\days?/, "天")
            .replace(/\shours?/, "小时")
            .replace(/\sminutes?/, "分钟")
            .replace(/\sseconds?/, "秒");
        }
      },
      // 修改上传状态
      fileStatusTextObj: {
        success: "上传成功",
        error: "上传错误",
        uploading: "正在上传",
        paused: "停止上传",
        waiting: "等待中"
      },
      uploadFileList: []
    };
    return data;
  },
  mounted() {
    this.$nextTick(() => {
      this.fileInput = document.querySelector(".uploader-btn input[type='file']");
    });
  },
  methods: {
    uploadBtnHandler(e: Event) {
      if (e.target && e.target.tagName === "INPUT") return;
      e.stopPropagation();
      this.fileInput.click();
      // (e.target as HTMLElement).querySelector("input[type='file']").click();
    },
    onFileAdded(file, event) {
      console.log("eeeee", event);
      // event.preventDefault();
      console.log("file :>> ", file);
      // 有时 fileType为空，需截取字符
      console.log("文件类型：" + file.fileType + "，文件大小：" + file.size + "B");
      // 1. todo 判断文件类型是否允许上传
      // 2. 计算文件 MD5 并请求后台判断是否已上传，是则取消上传
      console.log("校验MD5");
      this.getFileMD5(file, async (md5) => {
        if (md5 !== "") {
          // 修改文件唯一标识
          file.uniqueIdentifier = md5;
          // 请求后台判断是否上传
          const data = {
            ...this.uploadData,
            identifier: md5,
            filename: file.name,
            uploadBefore: true
          };
          uploadCheck(data).then(() => {
            this.uploadFileList.push(file);
            // 恢复上传
            file.resume();
          }).catch(() => {
            console.log("取消文件上传");
            file.error = true;
            file.cancel();
          });
        }
      });
    },
    onFileSuccess(rootFile, file, response, chunk) {
      console.log("上传成功", rootFile, file, response, chunk);
      const res = JSON.parse(response);
      window.$message.success(res.msg);
      this.uploadSuccess();
      // 这里可以做一些上传成功之后的事情，比如，如果后端需要合并的话，可以通知到后端合并
    },
    onFileError(rootFile, file, response, chunk) {
      console.log("上传出错：", rootFile, file, response, chunk);
      const res = JSON.parse(response);
      window.$message.error(res.msg);
    },
    onFileProgress(rootFile, file, chunk) {
      console.log(`当前进度：${Math.ceil(file._prevProgress * 100)}%`);
    },
    // 计算文件的MD5值
    getFileMD5(file, callback) {
      file.pause();
      let spark = new SparkMD5.ArrayBuffer();
      let fileReader = new FileReader();
      // 获取文件分片对象（注意它的兼容性，在不同浏览器的写法不同）
      let blobSlice =
        File.prototype.slice ||
        File.prototype.mozSlice ||
        File.prototype.webkitSlice;
      // 当前分片下标
      let currentChunk = 0;
      // 分片总数(向下取整)
      let chunks = Math.ceil(file.size / CHUNK_SIZE);
      // MD5加密开始时间
      let startTime = new Date().getTime();
      loadNext();
      // fileReader.readAsArrayBuffer操作会触发onload事件
      fileReader.onload = function(e) {
        // console.log("currentChunk :>> ", currentChunk);
        spark.append(e.target.result);
        if (currentChunk < chunks) {
          currentChunk++;
          loadNext();
        } else {
          // 该文件的md5值
          let md5 = spark.end();
          console.log(`MD5计算完毕：${md5}，耗时：${new Date().getTime() - startTime} ms.`);
          // 回调传值md5
          callback(md5);
        }
      };
      fileReader.onerror = function() {
        window.$message.error("文件读取错误");
        file.cancel();
      };
      // 加载下一个分片
      function loadNext() {
        const start = currentChunk * CHUNK_SIZE;
        const end = start + CHUNK_SIZE >= file.size ? file.size : start + CHUNK_SIZE;
        // 文件分片操作，读取下一分片(fileReader.readAsArrayBuffer操作会触发onload事件)
        fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end));
      }
    },
    fileStatusText(status, response) {
      if (status === "md5") {
        return "校验MD5";
      } else {
        return this.fileStatusTextObj[status];
      }
    },
    // 点击暂停
    pause(file) {
      console.log("file :>> ", file);
      if (file.paused) {
        file.resume();
      } else {
        file.pause();
      }
    },
    // 点击删除
    remove(file) {
      this.uploadFileList.forEach((item, index) => {
        if (item.id === file.id) {
          this.$nextTick(() => {
            this.uploadFileList.splice(index, 1);
          });
        }
      });
      file.cancel();
    },
    showDetail(props) {
      let fileName = props.file.name;
      let isComplete = props.isComplete;
      let timeRemaining = !isComplete ? ` 剩余时间:${props.formatedTimeRemaining}` : "";
      if (props.error) {
        return `${fileName} \t 上传失败`;
      }
      return `${fileName} \t ${timeRemaining}`;
    },
    showSpeed(props) {
      let isComplete = props.isComplete;
      // let formatUpload = this.formatFileSize(props.uploadedSize, 2);
      // let fileSize = `${props.formatedSize}`;
      // let uploaded = !isComplete ? ` ${formatUpload} / ${fileSize}` : ` 大小:${fileSize}`;
      let speed = !isComplete ? ` ${props.formatedAverageSpeed}` : "";
      return `${speed}`;
    },
    // 显示进度
    percentage(props) {
      // console.log(props);
      let progress = props.progress;
      return progress - 1 < 0 ? 0 : progress;
    },
    // 控制下进度条的颜色 ，异常的情况下显示红色
    progressColor(props) {
      console.log(props);
      if (props.isComplete) {
        return "#18A058";
      } else if (props.error) {
        return "#D03050";
      } else {
        return "#2684FE";
      }
    },
    // 点击下载
    download(file, id) {
      console.log("file:>> ", file);
      window.location.href = `/fileStorage/download/${file.uniqueIdentifier}`;
    },
    formatFileSize(bytes, decimalPoint = 2) {
      if (bytes === 0) return "0 B";
      let k = 1000;
      let sizes = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
      let i = Math.floor(Math.log(bytes) / Math.log(k));
      return (
        parseFloat((bytes / Math.pow(k, i)).toFixed(decimalPoint)) + " " + sizes[i]
      );
    }

  }
};
</script>

<style lang="scss" scoped>

</style>

<style lang="scss">
.uploader-drop {
  padding: 0;
  border: none;
  background-color: unset;

  .uploader-btn {
    padding: 0;
    border: none;
  }
}

.uploader-list {
  min-width: 300px;
  width: 35%;
  position: fixed;
  z-index: 2;
  bottom: 60px;
  right: 10px;

  .uploader-file {
    height: unset;
    line-height: normal;
  }
}
</style>
