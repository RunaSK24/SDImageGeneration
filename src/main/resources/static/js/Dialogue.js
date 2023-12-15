var message1 = "你好";
var newMessage1 = document.createElement("div");
newMessage1.className = "chat-message";
newMessage1.innerHTML = `
        <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
        <div class="message-content" style="background-color: #f7f7f7">${message1}</div>
    `;
document.querySelector(".chat-container").appendChild(newMessage1);

// 添加bot语句
function addBotMessage(message1) {
    var newMessage1 = document.createElement("div");
    newMessage1.className = "chat-message";
    if (message1.includes("imageSource")) {
        var source = "data:image/jpeg;base64,"

        fetch('http://localhost:80/Dia/Image/' + message1.split(":")[1], {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json' //指定以json格式发送数据
            },
        })
            .then(res => res.json()) //以json格式解码
            .then(res => {
                //接收到数据后的处理
                if (res.code === 20041) {
                    console.log(res);
                    source += res.data;
                    newMessage1.innerHTML = `
                    <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
                    <div class="message-content" style="background-color: #f7f7f7"><img src= ${source} ></div>`;
                } else {
                    alert(res.msg);
                }
            })
            .catch(error => {
                //出现异常处理
                alert(error);
                alert("发生错误，无法连接到服务器");
            });
    } else {
        newMessage1.innerHTML = `
        <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
        <div class="message-content" style="background-color: #f7f7f7">${message1}</div>
    `;
    }


    var chatContainer = document.querySelector(".chat-container");
    chatContainer.appendChild(newMessage1)
    chatContainer.scrollTop = chatContainer.scrollHeight;
    // 获取按钮元素
    var button = document.getElementById('send');

// 恢复按钮为可点击
    button.disabled = false;
}

// 测试机器人回复(开启则无法发送信息)
// document.getElementById("answer").addEventListener("click",addBotMessage("你好"));
var button = document.getElementById('send');

// 设置按钮为不可点击
button.addEventListener("click",()=>{
// 设置按钮为不可点击
    button.disabled = true;
})
// 用户添加数据
function addUserMessage(message) {
// 获取按钮元素


    if (message.trim() === "" || message.trim() === null) {
        return
    }
    // 创建一个新消息元素
    var newMessage = document.createElement("div");
    newMessage.style.display = "flex";
    newMessage.style.justifyContent = "flex-end";
    newMessage.className = "chat-message";
    console.log(message)
    if (message.includes("imageSource")) {

        var source = "../images/dialogImages/" + message.split(":")[1];
        newMessage.innerHTML = `
        <div class="message-content" style="background-color: #3b3abe ;color: white"> <img src= ${source} > </div>
        <div class="user-avatar" style="margin-left: 10px"><img src="../images/people.jpg" alt=""></div>
    `;
    } else {
        newMessage.innerHTML = `
        <div class="message-content" style="background-color: #3b3abe ;color: white">${message}</div>
        <div class="user-avatar" style="margin-left: 10px"><img src="../images/people.jpg" alt=""></div>
    `;
    }


    // 将新消息添加到聊天容器
    document.querySelector(".chat-container").appendChild(newMessage);

    // 清空输入框
    document.querySelector(".chat-input input").value = "";
    var chatContainer = document.querySelector(".chat-container");
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

// 监听发送按钮
document.getElementById("send").addEventListener("click", function () {
    const data = {
        uid: sessionStorage.getItem('userId'),
        did: sessionStorage.getItem('did'),
        message: document.querySelector(".chat-input input").value
    }
    // var message = document.querySelector(".chat-input input").value;
    // var did = sessionStorage.getItem('did');
    // var uid = sessionStorage.getItem('userId');
    console.log("---------------------------")
    console.log(data.did)
    console.log(data.uid)
    if (data.did == null || data.uid == null) {
        alert("请先选择对话");
        return
    }
    // 获取输入框中的消息内容
    addUserMessage(data.message);

    // 点击发送后存储历史记录
    fetch('http://localhost:80/Dia/storeTextMsg', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' //指定以json格式发送数据
        },
        body: JSON.stringify(data)
    })
        .then(res => res.json()) //以json格式解码
        .then(res => {
            console.log(res)
            //接收到数据后的处理
            if (res.code === 22001) {
                //TODO 生成图片后的处理
                // addDialog()
            } else {
                alert("系统错误,历史记录已丢失,该记录已删除,请手动删除该记录")
            }
        })
        .catch(error => {
            //出现异常处理
            alert(error);
            alert("发生错误，无法连接到服务器");
        });
});

// 图片按钮（调用隐藏的选择文件组件）
document.getElementById("add-image").addEventListener('click', function () {
    document.getElementById("image-input").click();
});
// 隐藏的选择文件组件
document.getElementById("image-input").addEventListener('change', function (event) {
    const file = event.target.files[0];

    if (file) {
        const reader = new FileReader();

        reader.addEventListener('load', function () {
            const imageDataUrl = reader.result;
            const base64 = imageDataUrl.split(',')[1];
            console.log(base64);

            //构造发送的数据
            const data = {
                uid: sessionStorage.getItem('userId'),
                did: sessionStorage.getItem('did'),
                message: base64
            }
            //发送数据
            fetch('http://localhost:80/Dia/storeImageMsg', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' //指定以json格式发送数据
                },
                body: JSON.stringify(data)
            })
                .then(res => res.json()) //以json格式解码
                .then(res => {
                    console.log(res)
                    //接收到数据后的处理
                    if (res.code === 22001) {
                        //TODO 生成图片后的处理
                        // addDialog()
                    } else {
                        alert("系统错误,历史记录已丢失,该记录已删除,请手动删除该记录")
                    }
                })
                .catch(error => {
                    //出现异常处理
                    alert(error);
                    alert("发生错误，无法连接到服务器");
                });
        });

        reader.readAsDataURL(file);
    }

    //重新加载对话
    fetch('http://localhost:80/Dia/fileRead/' + src, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' //指定以json格式发送数据
        },
        // body: JSON.stringify(data)
    })
        .then(res => res.json()) //以json格式解码
        .then(res => {
            console.log(res)
            //接收到数据后的处理
            if (res.code === 20041) {
                var lines = res.data.split("\n");
                lines.pop()
                console.log(lines)
                for (i = 0; i < lines.length; i++) {
                    var line = lines[i];
                    if (line.length === 0 || line.startsWith('#')) {
                        continue;
                    }
                    console.log(line);
                    var result = line.split("=");
                    if (result[0] === "Bot") {
                        addBotMessage(result[1]);
                    } else {
                        addUserMessage(result[1])
                    }
                }
                // addDialog()
            } else {
                alert("系统错误,历史记录已丢失,该记录已删除,请手动删除该记录")
            }
        })
        .catch(error => {
            //出现异常处理
            alert(error);
            alert("发生错误，无法连接到服务器");
        });
});