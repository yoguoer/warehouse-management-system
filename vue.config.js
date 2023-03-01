"use strict";
const path = require("path");
const resolve = (dir) => path.join(__dirname, dir);
module.exports = {
  // publicPath: '/iscm', // 基本路径
  publicPath: './', 
  // outputDir: 'dist', // 构建时的输出目录
  // assetsDir: 'static', // 放置静态资源的目录
  // indexPath: 'index.html', // html 的输出路径
  filenameHashing: true, // 文件名哈希值
  lintOnSave: true, // 是否在保存的时候使用 `eslint-loader` 进行检查。

  // 组件是如何被渲染到页面中的？ （ast：抽象语法树；vDom：虚拟DOM）
  // template ---> ast ---> render ---> vDom ---> 真实的Dom ---> 页面
  // runtime-only：将template在打包的时候，就已经编译为render函数
  // runtime-compiler：在运行的时候才去编译template
  runtimeCompiler: true,

  transpileDependencies: [], // babel-loader 默认会跳过 node_modules 依赖。
  productionSourceMap: true, // 是否为生产环境构建生成 source map？

  // 调整内部的 webpack 配置
  configureWebpack: () => {},
  chainWebpack: (config) => {
    // 添加别名
    config.resolve.alias
      .set("@", resolve("src"))
      // .set("assets", resolve(".src/assets"))
      // .set("api", resolve(".src/api"))
      // .set("mixins", resolve(".src/mixins"))
      // .set("utils", resolve(".src/utils"))
      // .set("components", resolve("./src/components"))
      // .set("views", resolve("src/views"));
    // 全局引入scss
    const oneOfsMap = config.module.rule("scss").oneOfs.store;
    oneOfsMap.forEach((item) => {
      item
        .use("sass-resources-loader")
        .loader("sass-resources-loader")
        .options({
          // 公用scss
          resources: "./src/assets/styles/element-variable.scss",
        })
        .end();
    });
  },
  // 配置 webpack-dev-server 行为。
  devServer: {
    hot: true,
    open: true, // 编译后默认打开浏览器
    host: "0.0.0.0", // 域名
    port: 8082, // 端口
    // port: 9111, // 端口
    https: false, // 是否https
    // 显示警告和错误
    overlay: {
      warnings: false,
      errors: true,
    },
    disableHostCheck: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        // target: 'http://120.78.171.54:8080',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/api'
        }
      },
      '/ws/*': {
        target: 'ws://127.0.0.1:8080',
        ws: true
      }
    },
  },
};
