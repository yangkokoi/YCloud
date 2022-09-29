<template>
  <div>
    <div v-if="shareData !== null">
      {{shareData}}
    </div>
    <div v-else class="password-page" >
      <div class="pwd-input" ref="pwdInputs" >
        <input class="y-input" v-model="password[0]" @input.native="nextInput(0, $event)" />
        <input class="y-input" v-model="password[1]" @input.native="nextInput(1, $event)"  />
        <input class="y-input" v-model="password[2]" @input.native="nextInput(2, $event)"  />
        <input class="y-input" v-model="password[3]" @input.native="nextInput(3, $event)" @keypress.enter="commit" />
      </div>
      <n-button style="width: 200px;" type="success" @click="commit">确认</n-button>
    </div>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import API from "@/http/Share"

const route = useRoute();
const uuid = ref(route.params.uuid);
const password = ref([]);
const shareData = ref(null);

onMounted(() => {
  // 项目启动时的请求，如果出现密码错误，说明此分享资源需要密码
  pass("", true);
});

const pass = function(pwd, hiddenMsg = false) {
  return API.pass(uuid.value, pwd, { hiddenMsg }).then(res => {
    password.value.length = 0;
  }).then(res => {
    list();
  }).catch(err => {
    password.value = [];
    nextTick(() => {
      inputs = pwdInputs.value.children;
      for (let i of inputs) {
        i.onchange = ({ target }) => {
          if (!/^[a-zA-Z0-9]$/.test(target.value)) {
            target.value = "";
          }
        }
      }
      inputs[0].focus();
    });
  });
}

const list = function() {
  return API.getShareList(uuid.value).then(res => {
    shareData.value = res;
    console.log(shareData.value);
  });
}

function init() {
  // 获取当前 uuid 的数据
  if (!uuid.value) return;
  list().catch(res => {
    
  });
}

const pwdInputs = ref(null);
let inputs = [];
const nextInput = function(next, e) {
  if (e.inputType === "deleteContentBackward" && next != 0) {
    // 处理删除
    inputs[next - 1].focus();
  } else if (e.inputType === "insertText" && next != 3) {
    // 普通输入
    inputs[next + 1].focus();
  } else if (e.inputType === "insertFromPaste") {
    if (next == 0) {
      // 对第一个输入框处理粘贴事件
      let val = e.target.value;
      if (val.length >= 4) {
        for (let i = 0; i < 4; i++) {
          password.value[i] = val[i];
        }
        e.target.blur();
      } else {
        e.target.value = "";
      }
    } else {
      password.value[next] = "";
    }
  }
}

const commit = function() {
  if (password.value.join("").length < 4) {
    window.$message.warning("请输入完整的密码");
    return;
  }
  pass(password.value.join(""));
}
</script>

<style lang="scss">
.password-page {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}  

.pwd-input {
  width: 200px;
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;

  input {
    text-align: center;
    width: 40px;
  }
}
</style>