const fs = require('fs');

function readFile() {
    // 定义相对路径到.properties文件
    const propertiesFilePath = '../test.properties';

    // 读取.properties文件
    fs.readFile(propertiesFilePath, 'utf8', (err, data) => {
        if (err) {
            console.error('读取文件失败:', err);
            return;
        }

        // 将文件内容解析为键值对
        const properties = {};
        const lines = data.trim().split('\n');
        lines.forEach((line) => {
            var result = line.split('=');
            if (result[0] === "Bot") {
                addBotDialog(result[1]);
            } else {
                userAddDialog(result[1])
            }

        });

        // 输出解析后的键值对
    });

}

var bnt = document.getElementById("fileTest")
bnt.addEventListener("click", function () {
    // 在这里编写点击按钮后要执行的代码
    console.log("按钮被点击了！");
});