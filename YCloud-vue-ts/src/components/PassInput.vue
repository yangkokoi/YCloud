<template>
  <div class="password-page">
    <div
      ref="pwdInputs"
      class="pwd-input"
      :style="{ width: (props.num * 50) + 'px' }"
    >
      <input
        v-for="(i, index) in new Array(props.num - 1)"
        :key="index"
        v-model="password[index]"
        class="y-input"
        @input.native="nextInput(index, $event)"
      >
      <input
        v-model="password[props.num - 1]"
        class="y-input"
        @input.native="nextInput(props.num - 1, $event)"
        @keypress.enter="commit"
      >
    </div>
    <n-button
      :style="{ width: (props.num * 50) + 'px' }"
      type="success"
      @click="commit"
    >
      确认
    </n-button>
  </div>
</template>

<script setup>
import { nextTick, ref, onMounted } from "vue";
import API from "@/http/Share";

const props = defineProps({
  num: {
    type: Number,
    validator(val) {
      // The value must match one of these strings
      return typeof val === "number" && val >= 4;
    },
    default: 4
  },
  id: String,
  pass: Function,
  passOk: Function
});
const password = ref([]);
const pwdInputs = ref(null);

onMounted(() => {
  nextTick(() => {
    inputs = pwdInputs.value.children;
    for (let i of inputs) {
      i.onchange = ({ target }) => {
        if (!/^[a-zA-Z0-9]$/.test(target.value)) {
          target.value = "";
        }
      };
    }
    inputs[0].focus();
  });
});

const pass = function(pwd) {
  if (props.pass) {
    props.pass(pwd);
  } else {
    return API.pass(props.id, pwd).then(res => {
      password.value.length = 0;
    }).then(res => {
      props.passOk();
    }).catch(err => {
      password.value = [];
    });
  }
};

let inputs = [];
const nextInput = function(next, e) {
  if (e.inputType === "deleteContentBackward" && next != 0) {
    // 处理删除
    inputs[next - 1].focus();
  } else if (e.inputType === "insertText" && next != props.num - 1) {
    // 普通输入
    inputs[next + 1].focus();
  } else if (e.inputType === "insertFromPaste") {
    if (next == 0) {
      // 对第一个输入框处理粘贴事件
      let val = e.target.value;
      if (val.length >= props.num) {
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
};

const commit = function() {
  if (password.value.join("").length < props.num) {
    window.$message.warning("请输入完整的密码");
    return;
  }
  pass(password.value.join(""));
};

</script>

<style scoped lang="scss">
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
