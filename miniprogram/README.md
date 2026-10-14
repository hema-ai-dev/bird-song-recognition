# 微信小程序（miniprogram/）配置说明

> 本目录为 uni-app 编译产物。项目 AI 识别入口在用户端网页（frontend-web），小程序仅含浏览功能。

## 1. 必须修改的两处

### ① appid（project.config.json 第 17 行）

```json
{
  "appid": "touristappid",   // ← 替换为你的小程序 AppID
  "projectname": "..."
}
```

- 获取方式：微信公众平台 → 注册小程序 → 「开发」→「开发设置」→ AppID
- 没有 AppID 时可在微信开发者工具选择「测试号」模式本地预览（部分能力受限）

### ② 后端地址（common/vendor.js，搜索 `localhost`）

```js
var t={url:"http://localhost:8080/springboot05qmpxx3/"};
```

- **本地开发**：手机与电脑同一局域网 → 改为电脑局域网 IP，如 `http://192.168.1.100:8080/springboot05qmpxx3/`
- **真机体验**：小程序要求 https 域名 → 部署后端到服务器 + 备案域名 + 微信公众平台「开发设置」配置 request 合法域名
- **联调技巧**：开发者工具「详情」→「本地设置」勾选「不校验合法域名」，可临时用 http

## 2. 三端地址配置对照表

| 端 | 文件 | 当前值 |
|---|---|---|
| 小程序 | `miniprogram/common/vendor.js` | `http://localhost:8080/springboot05qmpxx3/` |
| 用户端 H5 | `frontend-web/api/base.js` | `http://localhost:8080/springboot05qmpxx3/` |
| 管理端 | `frontend-admin/src/utils/base.js` | `http://localhost:8080/springboot05qmpxx3/` |

> 管理端/用户端改完源码需重新构建（管理端 `npm run build` → 产物拷到 backend 的 resources 下；H5 同理）。

## 3. 已知限制（如实声明）

- **小程序不含识别入口**：`bird_recognition` 接口仅用户端网页调用。README 与论文请以网页端演示识别为准
- **appid 未配置**：`touristappid` 为占位符，直接预览会报错
- **baseUrl 直连 localhost**：真机不可达，必须按第 ① 步修改

## 4. 在微信开发者工具中打开

1. 下载安装 [微信开发者工具](https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html)
2. 「导入项目」→ 目录选择本 `miniprogram/` 文件夹
3. 填入 AppID（或选测试号）
4. 编译预览
