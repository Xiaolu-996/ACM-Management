# ACM

<p align="center">
   ACM- Management，一个基于springboot+Vue的前后端分离比赛管理系统
   <br>
    <img src="https://img.shields.io/badge/jdk-1.8+-brightgreen.svg" ></img><img src="https://img.shields.io/badge/springboot-2.3.1-brightgreen.svg" ></img><img src="https://img.shields.io/badge/mybatisplus-3.3.2-brightgreen.svg" ></img><img src="https://img.shields.io/badge/mysql-5.7.0-brightgreen.svg" ></img><img src="https://img.shields.io/badge/vue-2.6.10-brightgreen.svg" ></img><img src="https://img.shields.io/badge/elementui-2.8.2-brightgreen.svg" ></img>
</p>

## 1.简介

- ACM-Management 鉴于学校ACM比赛训练管理诞生
- 人员管理-对加入ACM团队的成员进行管理
- 题目管理-对ACM团队的训练题目进行管理与分类
- 管理员管理：管理员可发布公告与刷题任务

## 2.开始步骤

### 2.1 文件解释

- **springboot文件夹**为后端模块，可直接用idea打开运行
- **vue文件夹**为前端模块，可直接用webstorm打开运行
- **ACM.sql** 为数据库文件，可直接用navicat打开
- **题目统计格式.xlsx**，按照表格形式可批量导入题目
- **用户信息格式.xlsx**，按照表格形式可批量导入用户(批量导入的用户，默认密码为123)

### 2.2 后端代码运行

#### 2.2.1 数据库密文密码生成

- util文件下jasyptUtil.java中将password换为你自己的password![image-20230322161810438](https://s2.loli.net/2023/03/22/yiLwI7uFsXkCBUP.png)
- 运行jasyptUtil.java将生成的密文放入application-dev.yml中

![image-20230322162814691](https://s2.loli.net/2023/03/22/JpdqfcBoniyhvCt.png)

![image-20230322162845995](https://s2.loli.net/2023/03/22/2At6IGgHdsSJYiX.png)

- 直接启动项目即可

> **数据库版本请用  5.7.X 版本**

#### 2.2 前端代码运行

- 本地运行：webstorm打开

- 安装项目依赖（阿里镜像）

 ```bash
  npm install --registry=https://registry.npm.taobao.org
 ```

- 开启服务，浏览器访问 http://localhost:9876

```
npm run serve
```

## 3.项目截图

### 3.1 登陆界面

#### 超管账号：admin    密码：admin

#### 测试用户： zhang    密码：123

![login](https://s2.loli.net/2023/03/22/WpH1sdQgfGixr8n.png)

### 3.2 注册界面

![registry](https://s2.loli.net/2023/03/22/I8AXTPhvJyWtiYb.png)

### 3.3 管理员后台首页

![main](https://s2.loli.net/2023/03/22/bi2cy31TuA4omFO.png)

### 3.4 普通用户后台首页

![image-20230322165829960](https://s2.loli.net/2023/03/22/SH48ElCMqA9Zvgy.png)

### 3.5 管理员题目管理

> 支持CRUD题目，将题目批量导出为excel，根据excel导入题目

![image-20230322171453335](https://s2.loli.net/2023/03/22/eSZ2z4hH5aDMlN6.png)

### 3.6 普通用户题目界面

![image-20230322171757619](https://s2.loli.net/2023/03/22/wEregc2f87jCMWs.png)



### 3.7 个人信息界面

> 可查看个人题目的提交记录

![image-20230322172529297](https://s2.loli.net/2023/03/22/wGqmcnheUH5MZSF.png)



### 3.8 用户管理界面

> 支持CRUD题目，将人员批量导出为excel，根据excel导入人员

![image-20230322171908710](https://s2.loli.net/2023/03/22/xXSEuiOhVlsmKDt.png)

### 3.9 管理员查看成员的做题情况

> 点击详情后出现该成员的刷题情况

![image-20230322172747252](/Users/xiaolu/学习/typoraImages/image-20230322172747252.png)



### 3.10 简易聊天室功能

![image-20230322172340074](https://s2.loli.net/2023/03/22/nQpoXxFR689Yvit.png)

![image-20230322172402720](https://s2.loli.net/2023/03/22/zhCqD9aApuIN4ZM.png)