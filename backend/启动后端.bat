@echo off
rem ============================================
rem 闻鸣清韵·鸟鸣识别系统 后端一键启动脚本
rem 用法：双击本文件（需先启动 MySQL，见下方说明）
rem ============================================

echo [1/2] 正在启动后端服务...
cd /d "%~dp0"

rem AI 识别环境变量（ai/ 目录随项目分发，包含绿色 Python + torch + 模型 + ffmpeg）
set "BIRD_PYTHON=%~dp0ai\python\python.exe"
set "BIRD_AI_DIR=%~dp0ai"
set "FFMPEG_PATH=%~dp0ai\ffmpeg\bin\ffmpeg.exe"

java -jar target\springboot05qmpxx3-0.0.1-SNAPSHOT.jar --server.port=8080

rem 说明：
rem 1. 数据库：需先在 33061 端口启动 MySQL，库名 springboot05qmpxx3，root/123456
rem    （项目自带 MariaDB：bin\mariadb-start.bat）
rem 2. --server.port=8080 是显式覆盖，防止系统环境变量 SERVER__PORT 干扰
rem 3. 访问地址：
rem    管理端 http://localhost:8080/springboot05qmpxx3/admin/dist/index.html  (admin/admin)
rem    用户端 http://localhost:8080/springboot05qmpxx3/front/h5/index.html
rem 4. 上传文件保存在 user.dir\upload\ 目录，静态映射已打通
rem 5. AI 识别：BIRD_PYTHON/BIRD_AI_DIR 指向 ai/ 目录，未设置时默认同目录查找
