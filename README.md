# 种子杯后端重构

重构前项目： https://github.com/TheNeet/seedcup

## 接口文档

采用yapi接口管理平台

路径：http://yapi.holdice.club

## 数据库设计

### 原来的设计

如图:(看不清可以在浏览器打开图片，或者看[pdf版](http://image-holdice.test.upcdn.net/seedcup_sqlite3.pdf))

![重构前的设计](http://image-holdice.test.upcdn.net/image-res/seedcup_sqlite3.jpg)

由图不难发现，原本的数据库设计在种子杯必要模块的基础上由很多django自带的模块及其生成响应的表

- 用户和权限系统
    - auth_user 自带的用户
    - auth_group 自带的群组
    - auth_permission 自带的权限表
    - auth_user_user_permissions auth_group_permissions auth_user_groups 与上面相对应的俩俩之间的多对多关系表
    - django_session django默认使用session管理登录状态，这是后端存储session的表

- 自带管理端页面
    - django_content_type 对应app的模型，用于细化到模型的权限管理，前面提到的auth_permission就与之对应，即权限可以按模型划分
    - django_admin_log 管理端修改日志，包括修改了什么content_type，修改者等信息
    

- 开发相关
    - django_migrations 记录每一次数据库修改的迁移

虽然django给开发人员集成了可以说比较完备的权限系统，可权限系统基本没有被用到，代码里也没有见到权限管理，数据库里除去django自己生成的内容，其余都是空的。

关于管理端，django的admin确实好用，但事实上这些管理端所需要修改的字段并不多，完全可以整合进后端进行相应的权限控制或者管理端控制，
这样可以更好的控制流程和相应逻辑，避免之前开发因为管理人员直接修改数据库字段而导致数据一致性或完整性被破坏。

关于django的数据库迁移工具 migrations，确实是一个非常好的工具，但是由于版本管理不当或使用不当也会导致很糟糕的结果。

### 现在的设计

如图：

![重构后设计](http://image-holdice.test.upcdn.net/image-res/seedcup_db.png)

由于原来的数据库实在太过离谱，使用django自带的user却自己用member表示选手，并用一个外键和user一一对应起来。  
这里就使用一个user表示选手，相较原来的数据库去除了很多无用字段。team表示队伍，user.team_id 字段表示用户所属的队伍（未使用外键），-1代表尚未加入队伍  
这样设计有个目的是为了让选手注册和队伍报名分开来，不要一次性给整个队伍报名。