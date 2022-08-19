<template>
  <n-data-table
    :row-key="row => row.hash"
    :row-props="rowProps"
    :max-height="400"
    :columns="columns"
    :data="data"
    @update:checked-row-keys="handleCheck"
  />
  <n-dropdown
    placement="bottom-start"
    trigger="manual"
    :x="mouse.x"
    :y="mouse.y"
    :options="options"
    :show="showDropdownRef"
    :on-clickoutside="onClickoutside"
    @select="handleSelect"
  />
  
</template>
  
  <script setup lang="ts">
import { defineComponent, h, ref, nextTick, reactive, readonly } from 'vue'
import { useMessage, NButton } from 'naive-ui'
import type { DataTableColumns, DropdownOption } from 'naive-ui'

type MyFile = {
  filename: string
  hash: string
  size: number
  updateTime: string
  type: string
}


const data: Array<Object> = reactive([]);
data[0] = {
  filename: "巴啦啦小魔仙秘术宝典.pdf",
  hash: "121212",
  updateTime: "2022-02-02 00:00:00",
  type: "JPEG",
  size: "14839483",
}

const options: DropdownOption[] = [
  {
    label: '复制',
    key: 'duplicate'
  }, {
    label: '拷贝',
    key: 'copy'
  }, {
    label: '剪切',
    key: 'cut'
  }, {
    label: () => h('span', { style: { color: 'red' } }, '删除'),
    key: 'delete'
  }
]

const message = useMessage()
const showDropdownRef = ref(false)
const mouse = reactive({ x: 0, y: 0 })
const columns = readonly([
  {
    type: "selection",
    disabled(row: MyFile, index: number) {
      return row.hash === 'Edward King 3'
    }
  },
  {
    title: '文件名',
    key: 'filename'
  },
  {
    title: '修改日期',
    key: 'updateTime'
  },
  {
    title: '类型',
    key: 'type'
  },
  {
    title: '大小',
    key: 'size',
    render: (row: MyFile) => {
      return (row.size / 8 / Math.pow(2, 20)).toFixed(2) + "MB"
    }
  },
  {
    title: '操作',
    key: 'operation',
    render: (row: MyFile) => {
      // 渲染一个删除按钮
      return h(
        NButton,
        {
          quaternary: true,
          type: "error",
          size: "tiny",
          onClick: () => {
            deleteFile(row);
          }
        },
        () => { return "删除" }
      )
    }
  }
]);


const handleSelect = () => {
  showDropdownRef.value = false
}
const onClickoutside = () => {
  showDropdownRef.value = false
}
const rowProps = (row: Song) => {
  return {
    onContextmenu: (e: MouseEvent) => {
      message.info(JSON.stringify(row, null, 2))
      e.preventDefault()
      showDropdownRef.value = false
      nextTick().then(() => {
        showDropdownRef.value = true
        mouse.x = e.clientX
        mouse.y = e.clientY
      })
    }
  }
}
</script>