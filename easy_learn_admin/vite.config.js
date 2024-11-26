import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  server: {
    hmr: true,
    port: 8081,
    proxy: {
      "/api": {
        target: "http://localhost:8080/",
        changeOrigin: true,
      }
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  css: {
    loaderOptions: {
      sass: {
        prependData:   // 8版本用prependData:
        `
          @import "@/styles/variables.scss";  // scss文件地址
          @import "@/styles/mixin.scss";     // scss文件地址
        `
      }
    }
  }
})


