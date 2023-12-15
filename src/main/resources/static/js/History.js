function readPropertiesFile(file, callback) {
    var reader = new FileReader();
    reader.onload = function (event) {
        var content = event.target.result;
        var lines = content.split('\n');
        callback(lines);
    };
    reader.readAsText(file);
}

// 使用示例
var fileInput = document.getElementById('fileInput');

fileInput.addEventListener('change', function (event) {
    var file = event.target.files[0];
    readPropertiesFile(file, function (lines) {
        for (var i = 0; i < lines.length; i++) {
            var line = lines[i].trim();
            if (line.length === 0 || line.startsWith('#')) {
                continue;
            }
            console.log(line);
            var result = line.split("=");
            if(result[0] === "Bot"){
                addBotDialog(result[1]);
            }else {
                userAddDialog(result[1])
            }
        }
    });
});

