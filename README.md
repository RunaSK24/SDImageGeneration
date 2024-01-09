# 岗位实践课程设计项目源码

### 选题: 基于Stable Diffusion的“文生图”/“图生图”系统

## 使用说明：
1. 项目基于SpringBoot框架，使用SpringBoot运行项目后使用"http://localhost/static/html/Main.html"地址进入web页面
2. 项目使用阿里云数据库，若数据库失效，请修改src/main/resources/application.yml文件中数据库配置
3. 项目使用阿里云函数计算FC部署Stable Diffusion，若Stable Diffusion失效，请修改src/main/resources/application.yml文件中SD的Url
