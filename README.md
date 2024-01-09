# 岗位实践课程设计项目源码

### 选题: 基于Stable Diffusion的“文生图”/“图生图”系统

## 使用说明：
1. 项目基于SpringBoot框架，使用SpringBoot运行项目后使用 "http://localhost/static/html/Main.html" 地址进入web页面
2. 项目使用阿里云数据库，若数据库失效，请使用项目中的sql文件创建新的数据库并修改 "src/main/resources/application.yml" 文件中数据库配置
3. 项目使用阿里云函数计算FC部署Stable Diffusion(SD)，若SD失效，请自行部署SD并修改 "src/main/resources/application.yml" 文件中SD的Url
