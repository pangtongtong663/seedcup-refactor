## 种子杯后端重构

重构前项目： https://github.com/TheNeet/seedcup

## 接口文档

路径：{ip}/api/doc

例：在本地http://localhost:8087 开启服务，接口文档地址在：http://localhost:8087/api/doc

接口文档会自动扫描项目里所有controller,根据相关注解生成对应API文档

![swagger接口文档图示](http://image-holdice.test.upcdn.net/image-res/2BEB8BD5-B7E0-498B-9ED8-D0B588C773FD.png)

## 各模块说明

### common

通用模块，包含人员和队伍相关操作

### config

配置模块，放置各种框架的配置