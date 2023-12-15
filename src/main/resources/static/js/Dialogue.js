var message1 = "你好";
var newMessage1 = document.createElement("div");
newMessage1.className = "chat-message";
newMessage1.innerHTML = `
        <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
        <div class="message-content" style="background-color: #f7f7f7">${message1}</div>
    `;
document.querySelector(".chat-container").appendChild(newMessage1);

// 添加bot语句
function addBotMessage(message1)  {

    var newMessage1 = document.createElement("div");
    newMessage1.className = "chat-message";
    if (message1.includes("imageSource")){
        var source = "../images/"+message1.split(":")[1];

        newMessage1.innerHTML = `
        <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
        <div class="message-content" style="background-color: #f7f7f7"><img src= ${source} ></div>
    `;
    }else{
        newMessage1.innerHTML = `
        <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
        <div class="message-content" style="background-color: #f7f7f7">${message1}</div>
    `;
    }


    var chatContainer = document.querySelector(".chat-container");
    chatContainer.appendChild(newMessage1)
    chatContainer.scrollTop = chatContainer.scrollHeight;
}
// 测试机器人回复(开启则无法发送信息)
// document.getElementById("answer").addEventListener("click",addBotMessage("你好"));

// 用户添加数据
function addUserMessage(message){
    if (message.trim()==="" || message.trim() === null){
        return
    }
    // 创建一个新消息元素
    var newMessage = document.createElement("div");
    newMessage.style.display = "flex";
    newMessage.style.justifyContent = "flex-end";
    newMessage.className = "chat-message";
    console.log(message)
    if (message.includes("imageSource")){

        var source = "../images/"+message.split(":")[1];
        newMessage.innerHTML = `
        <div class="message-content" style="background-color: #3b3abe ;color: white"> <img src= ${source} > </div>
        <div class="user-avatar" style="margin-left: 10px"><img src="../images/people.jpg" alt=""></div>
    `;
    }else{
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
document.getElementById("send").addEventListener("click", function() {
    var message = document.querySelector(".chat-input input").value;
    var did = sessionStorage.getItem('did');
    var uid = sessionStorage.getItem('userId')
    console.log("---------------------------")
    console.log(did)
    console.log(uid)
    if(did == null || uid == null){
        alert("请先选择对话");
        return
    }
    // 获取输入框中的消息内容
    addUserMessage(message);
    // 点击发送后存储历史记录

    console.log(did)
    console.log(uid)
    fetch('http://localhost:80/Dia/storeMsg/'+message+'/'+uid+'/'+did, {
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
            if (res.code === 22001) {

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
document.getElementById("add-image").addEventListener('click', function() {
    document.getElementById("image-input").click();
});
// 隐藏的选择文件组件
document.getElementById("image-input").addEventListener('change', function(event) {
    var file = event.target.files[0];

    if (file) {
        var reader = new FileReader();

        reader.addEventListener('load', function() {
            var imageDataUrl = reader.result;
            var base64 = imageDataUrl.split(',')[1];
            console.log(base64);
        });

        reader.readAsDataURL(file);
    }



    //后续实现将图片发送到后端
    fetch('http://localhost:80/Dia/fileRead/'+src, {
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
                for (i = 0; i < lines.length;i++){
                    var line = lines[i];
                    if (line.length === 0 || line.startsWith('#')) {
                        continue;
                    }
                    console.log(line);
                    var result = line.split("=");
                    if(result[0] === "Bot"){
                        addBotMessage(result[1]);
                    }else {
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