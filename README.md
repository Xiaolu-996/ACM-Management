# ACM

<p align="center">
   ACM- Management，一个基于springboot+Vue的前后端分离比赛管理系统
  	<br>
    <img src="https://img.shields.io/badge/jdk-1.8+-brightgreen.svg" ></img><img src="https://img.shields.io/badge/springboot-2.3.1-brightgreen.svg" ></img><img src="https://img.shields.io/badge/mybatisplus-3.3.2-brightgreen.svg" ></img><img src="https://img.shields.io/badge/mysql-5.7.0-brightgreen.svg" ></img><img src="https://img.shields.io/badge/vue-2.6.10-brightgreen.svg" ></img><img src="https://img.shields.io/badge/elementui-2.8.2-brightgreen.svg" ></img>
</p>

## 1.前言

ACM-Management 鉴于学校ACM比赛训练管理诞生

## 2.开始步骤

### 2.1 文件解释

- **springboot文件夹**为后端模块，可直接用idea打开运行
- **vue文件夹**为前端模块，可直接用webstorm打开运行
- **ACM.sql** 为数据库文件，可直接用navicat打开
- **题目统计格式.xlsx**，按照表格形式可批量导入题目
- **用户信息格式.xlsx**，按照表格形式可批量导入用户(批量导入的用户，默认密码为123)

### 2.2 程序运行

#### 后端：

- **将ACM.sql导入**
- 本地运行：idea修改application-dev.yml中的数据库配置(用户名，密码，**端口号**)
- 服务器运行：idea修改application.yml中active为prod，修改application-prod.yml中ip为服务器ip，数据库配置(用户名，密码，iP)

> **数据库版本请用  5.X.X 版本**

#### 前端：

- 本地运行：webstorm打开

- 安装项目依赖（阿里镜像）

 ```bash
  npm install --registry=https://registry.npm.taobao.org
 ```

- 开启服务，浏览器访问 http://localhost:9876

```
npm run serve
```

> **服务器运行请修改 public/static/config.js   文件中filesUploadUrl配置为服务器 IP**

## 3.账号密码

### 超管账号：admin    密码：admin

### 测试用户： zhang	密码：123

