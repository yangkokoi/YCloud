import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import legacy from '@vitejs/plugin-legacy'
import Components from 'unplugin-vue-components/vite'
import { NaiveUiResolver, VarletUIResolver } from 'unplugin-vue-components/resolvers'
import autoImport from 'unplugin-auto-import/vite'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    Components({
      resolvers: [NaiveUiResolver(), VarletUIResolver()]
    }),
    autoImport({
      resolvers: [VarletUIResolver({ autoImport: true })]
    }),
    legacy({
      targets: ['chrome 52'],
      additionalLegacyPolyfills: ['regenerator-runtime/runtime'],
      // renderLegacyChunks: true,
      // polyfills: [
      //   'es.symbol',
      //   'es.array.filter',
      //   'es.promise',
      //   'es.promise.finally',
      //   'es/map',
      //   'es/set',
      //   'es.array.for-each',
      //   'es.object.define-properties',
      //   'es.object.define-property',
      //   'es.object.get-own-property-descriptor',
      //   'es.object.get-own-property-descriptors',
      //   'es.object.keys',
      //   'es.object.to-string',
      //   'web.dom-collections.for-each',
      //   'esnext.global-this',
      //   'esnext.string.match-all'
      // ]
    })
  ],
  resolve: {
    // https://cn.vitejs.dev/config/#resolve-alias
    alias: {
      // 设置路径
      '~': resolve(__dirname, './'),
      // 设置别名
      '@': resolve(__dirname, './src')
    },
    // https://cn.vitejs.dev/config/#resolve-extensions
    extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
  },
  server: {
    port: 3000,
    host: true,
    open: false,
    proxy: {
      // https://cn.vitejs.dev/config/#server-proxy
      // mock 服务
      '/dev-api/@': {
        // target: 'http://192.168.1.9:9999',
        target: 'https://console-mock.apipost.cn/app/mock/project/f693ad11-9d36-45a4-ef52-f44f790c6bbf',
        changeOrigin: true,
        rewrite: (p) => {
          return p.replace(/^\/dev-api\/@/, '')
        }
      }
    }
  },
  css: {
    preprocessorOptions: {
      scss: {
        // 两种方式都可以
        // additionalData: '@import "@/assets/scss/global.scss";'
      }
    }
  },
  optimizeDeps: {
    exclude: []
  },
  build: {
    target: 'es2015',
    // rollup 配置
    rollupOptions: {
      output: {
      }
    }
  }
})
