<template>
  <div>
    <cloud-header :title="(explorerPath.length > 0) ? explorerPath.slice(-1)[0] : bucketName">
      <template
        v-if="(explorerPath.length > 0)"
        #left
      >
        <var-button
          round
          text
          color="transparent"
          text-color="#fff"
          style="margin-right: 10px;"
          @click="intoPath(-1)"
        >
          <var-icon
            name="chevron-left"
            :size="24"
          />
        </var-button>
      </template>
    </cloud-header>
    <div>
      <var-cell
        v-for="el in fileList"
        :key="el.id"
        :title="el.name"
        border
        @click="cellClick(el)"
      >
        <template #default>
          <div class="resource-cell">
            <y-icon
              :name="(fileIconName(el.type))"
              :color="el.type === 'dir' ? '#E66E05' : '#333'"
              :size="30"
              style="flex: 0 0 30px;"
            />
            <div>
              <div class="cell-title">
                {{ el.name }}
              </div>
              <div style="font-size: 1.2rem;color: #696969;">
                {{ new Date(el.updateTime).format() }} {{ el.type === "dir" ? "" : ("- " + calcSize(el.size)) }}
              </div>
            </div>
            <y-icon
              name="fa-ellipsis"
              :size="20"
              style="flex: 0 0 20px;"
              @click="openActionSheet($event, el)"
            />
          </div>
        </template>
      </var-cell>
    </div>

    <y-popup
      v-model:show="actionSheet.visible"
      position="bottom"
      :title="clickFile?.name"
    >
      <template
        v-for="el in actionSheet.actions"
        :key="el.key"
      >
        <div
          v-if="clickFile?.dir == false || el.key !== 'download'"
          v-ripple
          class="sheet-cell"
          @click="actionSheetHandler(el)"
        >
          <y-icon
            :name="el.icon"
            :size="22"
            style="margin-right: 10px;width: 30px;"
          />
          <span>{{ el.name }}</span>
        </div>
      </template>
    </y-popup>

    <var-dialog
      v-model:show="renameDialog.visible"
      :close-on-click-overlay="false"
      @before-close="renameHandlerM"
    >
      <template #title>
        ?????????
      </template>
      <var-input
        v-model="renameDialog.value"
        placeholder="??????????????????"
        :maxlength="20"
      />
    </var-dialog>

    <var-dialog
      v-model:show="downloadDialog.visible"
      :close-on-click-overlay="false"
      :cancel-button="false"
      message-align="center"
    >
      <template #title>
        ????????????
      </template>
      <a
        :href="downloadDialog.url"
        style="text-decoration: underline;"
        target="_blank"
      >????????????</a>
    </var-dialog>

    <var-popup
      v-model:show="shareDialog.visible"
      position="bottom"
      :close-on-click-overlay="false"
      @closed="shareDialog.closed"
    >
      <var-loading
        description="loading...."
        type="circle"
        :loading="shareDialog.loading"
      >
        <div
          v-if="shareDialog.url"
          class="share-box"
        >
          <div style="margin: 20px 0;">
            <div>
              ?????????<span>{{ shareDialog.password }}</span><br>
              ???????????????<span>{{ shareDialog.deadline == null ? "??????" : new Date(shareDialog.deadline).format("YYYY-MM-DD HH:mm:ss") }}</span>
            </div>
            <div style="text-align: center;">
              <vue-qrcode
                class="qrcode"
                :value="shareDialog.url"
                :options="{ width: 200 }"
              />
            </div>
            <var-input
              v-model="shareDialog.url"
              textarea
              readonly
              placeholder="????????????"
              :rows="2"
            >
              <template #append-icon>
                <var-button
                  style="height: 100%;box-shadow: none;"
                  @click="copyUrl"
                >
                  <y-icon name="fa-copy" />??????
                </var-button>
              </template>
            </var-input>
          </div>

          <div style="text-align: right;">
            <var-button
              type="primary"
              @click="(shareDialog.visible = false)"
            >
              ??????
            </var-button>
          </div>
        </div>
        <div
          v-else
          class="share-box"
        >
          <div style="display: flex;align-items: center;margin-bottom: 10px;">
            <span style="flex: none;">???????????????</span>
            <var-switch
              v-model="shareDialog.enablePwd"
              size="25"
            />
            <var-input
              v-if="shareDialog.enablePwd"
              v-model="shareDialog.form.password"
              :minlength="4"
              :maxlength="4"
              style="margin-left: 20px;"
            />
          </div>
          <div>
            ??????????????????????????????????????????
          </div>
          <var-date-picker
            v-model="(shareDialog.form.deadline as string)"
            :min="new Date().format('YYYY-MM-DD')"
          >
            <template #year>
              <div style="width: 100%;display: flex; flex-direction: row-reverse;">
                <span @click="(shareDialog.form.deadline = null)">????????????</span>
              </div>
            </template>
            <template #date="{ year, month }">
              <span>{{ year }}???{{ month }}???</span>
            </template>
          </var-date-picker>

          <div style="text-align: right;">
            <var-button
              style="margin-right: 10px;"
              @click="(shareDialog.visible = false)"
            >
              ??????
            </var-button>
            <var-button
              type="primary"
              @click="shareHandler"
            >
              ??????
            </var-button>
          </div>
        </div>
      </var-loading>
    </var-popup>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive, inject } from "vue";
import { useRoute, useRouter } from "vue-router";
import { fileIconName } from "@/components/file-table/common.js";
import store from "@/store/temp";
import API from "@/http/Explore";
import { Dialog } from "@varlet/ui";
import VueQrcode from "@chenfengyuan/vue-qrcode";
import { copyText } from "@/utils/Tools";
import type { Resource } from "@/type/type";

import setup, { shareDialog, shareHandler } from "./index";
const { explorerPath, fileList, clickFile, renameDialog, intoPath, calcSize, refresh, renameHandler } = setup.setup();

const $route = useRoute();
const $router = useRouter();
const bucketName = ref("????????????");

const setRefresh:Function | undefined = inject("setRefresh");
if (setRefresh) {
  setRefresh(intoPath);
}

onMounted(() => {
  // console.log($route);
  explorerPath.value = $route.query.path ? ($route.query.path as string).split("/") : [];
  intoPath();
  // if (tableOperation.value) console.log(tableOperation.value);
  // ???????????? ????????????????????? ????????? 110 ??????????????? 30
});

const downloadDialog = reactive({
  visible: false,
  url: ""
});
const actionSheet = reactive({
  visible: false,
  actions: [
    {
      name: "??????",
      key: "download",
      icon: "fa-circle-down",
      action: (row: Resource) => {
        API.download(row.id, false).then(url => {
          downloadDialog.url = url;
          downloadDialog.visible = true;
        });
      }
    },
    {
      name: "??????",
      key: "share",
      icon: "fa-square-up-right",
      action(row: Resource) {
        shareDialog.visible = true;
        shareDialog.form.resourceIds = row.id + "";
      }
    },
    {
      name: "??????",
      key: "copy",
      icon: "fa-copy"
    },
    {
      name: "????????????",
      key: "dumplicate",
      icon: "fa-clone"
    },
    {
      name: "??????",
      key: "move",
      icon: "fa-file-export"
    },
    {
      name: "??????",
      key: "delete",
      icon: "fa-trash-can",
      action: (row: Resource) => {
        Dialog("???????????????").then(state => {
          if (state === "confirm") {
            API.deleteFile(row.id).then(() => {
              window.$message.success("??????????????????");
              refresh();
            });
          }
        });
      }
    },
    {
      name: "?????????",
      key: "rename",
      icon: "fa-file-pen",
      action: (row: Resource) => {
        renameDialog.value = row.name;
        renameDialog.visible = true;
      }
    }
  ]
});

const cellClick = function(row: Resource) {
  if (row.type === "dir") {
    intoPath(row.name);
  } else {
    API.preview(row.id).then(url => {
      store().setResource({
        ...row,
        url
      });
      $router.push({ name: "Preview", params: { id: row.id } });
    });
  }
};

const renameHandlerM = function(action: string, done: Function) {
  if (action !== "confirm") {
    done();
    return;
  }
  renameHandler(clickFile.value).then(() => {
    done();
  });
};

const openActionSheet = function(event: Event, file: Resource) {
  clickFile.value = file;
  actionSheet.visible = true;
  event.stopPropagation();
};
const actionSheetHandler = (action: any) => {
  if (!clickFile.value) return;
  if (action.action) {
    action.action(clickFile.value);
  }
  actionSheet.visible = false;
};

const copyUrl = async function() {
  await copyText(shareDialog.url);
  window.$message.success("????????????");
};

</script>

<style lang="scss" scoped>
.resource-cell {
  display: flex;
  justify-content: space-between;
  align-items: center;

  > div {
    flex: 1 1 auto;
    text-overflow: ellipsis;
    overflow: hidden;
    margin-left: 7px;
    margin-right: 10px;
  }

  .cell-title {
    color: #000;
    font-size: 1.6rem;
  }

}

.sheet-cell {
  display: flex;
  align-content: center;
  padding: 10px 0;
  color: #333;
}

.share-box {
  padding: 20px 20px;
}
</style>
