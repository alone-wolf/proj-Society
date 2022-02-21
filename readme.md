# Society

## 开发环境

### 开发工具

- Android Studio （最新版即可）
- Postman （api 调试，即可）
- VSCode （最新版即可）
- nodejs （v16.13 附近即可，最新的长期支持版是 v16.14.0 应该没问题）
- SQLiteStudio (调试数据库，由于使用了一个第三方库，SQLite 和 MySQL 用法一致，只需要修改个别配置即可，所以开发过程中使用 SQLite 更方便)

### 环境搭建

- 设置 AndroidStudio（AS）的 http 代理，必须使用 http 协议的代理（指的是 AS 设置里面必须选用 http 协议，部分代理软件提供混合协议的代理端口，只要支持 http 即可，上述必须是因为这样最省事），代理这种东西自己准备吧，实在不行再说
  - 参考 https://blog.csdn.net/liuli905306022/article/details/89927052
- AS 还有可能会遇到一个和许可/协议云云相关的东西，遇到再说
- npm 设置淘宝源

  - 参考 https://www.jianshu.com/p/c35e832a08d8 永久设置那个就行

- Android 手机需要打开 usb 调试，具体自己查吧（型号+usb 调试），操作无非就是点击版本号几次什么的

### 代码下载

- 使用 git，终端、cmd 执行
  - git clone https://github.com/alone-wolf/proj-Society.git
- 或者点击 github 页面里面那个绿色按钮，点击下载 zip，解压之后直接使用

## 软件介绍

### 架构

- server 目录下面是 主服务端，基于 nodejs 编写
- pusher 目录下是 推送服务，基于 nodejs 编写
  - pusher 下面有个推送服务注册管理界面，http://127.0.0.1:5002/admin 就行了，打开后弹窗还写这个
- Society 是 Android 端，包含普通用户客户端和后台管理员端

### 使用

- 进行本操作前，需要准备好上述开发环境
- 基于 nodejs 的 server 端和 pusher 端
  - 使用 VSCode 打开 server 或 pusher 这个文件夹，键盘快捷键 ctrl+`（就是 esc 下面那个点，注意输入法切换到英文）打开终端或称为命令行，最好是这俩文件夹一个 vscode 窗口打开一个，然后分别操作
  - 执行 npm i 这个命令，两个文件夹里都要这样操作，用于安装依赖包
  - 启动服务，都是执行命令 npm run dev，默认 server 的端口是 5001，pusher 端口是 5002（不改的话不用管）
  - Android 端需要修改服务器地址，其实也就是修改 ip 地址为你的电脑 ip，这个东西我就不说了，太基础
- Android 端
  - 打开 AS，将 Society 目录作为项目打开，之后它会进行一个 gradle sync 操作，等屏幕右下角进度条完成，左边的工程文件目录就会准备好，app 和 admin 两个文件夹（图标特殊）需要我们关注
    - /app/java/com.wh.society/api/ServerApi 文件里包含 需要设置的 ip 地址
    - /admin 这个暂时没写，之后也类似
  - 最好能搞到两部 android 手机，因为写了一个推送服务，涉及聊天、发帖、回复、动态等的操作有相关推送
  - 运行程序，AS 打开这个工程后，屏幕右上方会有一个绿色箭头（需等待 gradle sync 操作完成），箭头左面有两个下拉框，一个是设备选择器，一个是模块选择器，模块那里可以选择运行 admin 或是 app
