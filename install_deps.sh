#!/bin/bash
# 删除旧的pom.xml并下载新的
rm pom.xml && curl -O https://raw.githubusercontent.com/fe11n/ElainaInSpire/auto_build/pom.xml &
# 创建jar目录并进入
mkdir -p jar && cd jar &
# 并发下载所需的JAR文件
curl -O https://github.com/fe11n/ElainaInSpire/releases/download/build_deps/BaseMod.jar &
curl -O https://github.com/fe11n/ElainaInSpire/releases/download/build_deps/desktop-1.0.jar &
curl -O https://github.com/fe11n/ElainaInSpire/releases/download/build_deps/ModTheSpire.jar &
curl -O https://github.com/fe11n/ElainaInSpire/releases/download/build_deps/StSLib.jar &
# 等待所有后台任务完成
wait
# 输出完成信息
echo "所有文件已下载完成。"