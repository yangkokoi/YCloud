- [ ] 在`src\views\Explore.vue`中，表格组件的高度计算有问题，不知道为什么减去头部（60px）的高度后还是会超出内容

  ```javascript
  // script
  const ref1 = ref(null);
  const getHeight = computed({
    get: () => {
      // if (ref1.value) console.log(ref1.value.$el);
      // 此处计算 需要减去头部的 头部的 110 和面包屑的 30
      return ref1.value ? ref1.value.$el.clientHeight - 145 + "px" : null;
    },
    set: () => {}
  })
  ```


- [x] 在`src\views\Explore.vue`中，不能将string复制给string[]
``` javascript
const om = onMounted(() => {
  console.log(useRoute().params)
  expolerPath.value = useRoute().params.path;
});
```

