var message1 = "你好，请 选择消息/新建对话 开始生图";
var newMessage1 = document.createElement("div");
newMessage1.className = "chat-message";
newMessage1.innerHTML = `
        <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
        <div class="message-content" style="background-color: #f7f7f7">${message1}</div>
    `;
document.querySelector(".chat-container").appendChild(newMessage1);

var button = document.getElementById('send');
var imgBnt = document.getElementById('add-image');
let chatContainer = document.querySelector(".chat-container");
let image;

//bot发送文本消息
function botSendText(textMessage) {
    const newMessage = document.createElement("div");
    newMessage.className = "chat-message";
    newMessage.innerHTML = `
            <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
            <div class="message-content" style="background-color: #f7f7f7">${textMessage}</div>
        `;
    chatContainer.appendChild(newMessage)
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

//bot发送图片消息
function botSendImage(imageMessage) {
    const newMessage = document.createElement("div");
    newMessage.className = "chat-message";
    newMessage.innerHTML = `
            <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
            <div class="message-content" style="background-color: #f7f7f7"><img src= ${imageMessage} ></div>
        `;
    chatContainer.appendChild(newMessage)
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

//user发送文字消息
function userSendText(textMessage) {
    const newMessage = document.createElement("div");
    newMessage.style.display = "flex";
    newMessage.style.justifyContent = "flex-end";
    newMessage.className = "chat-message";
    newMessage.innerHTML = `
            <div class="message-content" style="background-color: #3b3abe ;color: white">${textMessage}</div>
            <div class="user-avatar" style="margin-left: 10px"><img src="../images/people.jpg" alt=""></div>
        `;
    chatContainer.appendChild(newMessage);
    chatContainer.scrollTop = chatContainer.scrollHeight;
    image = null;
}

//user发送图片消息
function userSendImage(imageMessage) {
    const newMessage = document.createElement("div");
    newMessage.style.display = "flex";
    newMessage.style.justifyContent = "flex-end";
    newMessage.className = "chat-message";
    newMessage.innerHTML = `
            <div class="message-content" style="background-color: #3b3abe ;color: white"> <img src= ${imageMessage} > </div>
            <div class="user-avatar" style="margin-left: 10px"><img src="../images/people.jpg" alt=""></div>
        `;
    chatContainer.appendChild(newMessage);
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

// 添加bot语句
function addBotMessage(message1) {
    //创建一个机器人的消息元素
    const newMessage1 = document.createElement("div");
    newMessage1.className = "chat-message";

    //判断是否为图片数据
    if (message1.includes("imageSource")) {
        //在结果前加上识别base64数据的字符串
        let source = "data:image/jpeg;base64,";

        //请求对应图片的base64数据
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
                    source += res.data;//拼接完整图片
                    //创建对应的图片元素放入消息中
                    newMessage1.innerHTML = `
                        <div class="user-avatar">
                            <img src="../images/bot.png" alt="">
                        </div>
                        <div class="message-content" style="background-color: #f7f7f7">
                            <img src= ${source} >
                        </div>`;
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
        //文本消息直接创建对应文本元素
        newMessage1.innerHTML = `
            <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
            <div class="message-content" style="background-color: #f7f7f7">${message1}</div>
        `;
    }

    //将创建的消息加入到对话框内
    const chatContainer = document.querySelector(".chat-container");
    chatContainer.appendChild(newMessage1)
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

// 测试机器人回复(开启则无法发送信息)
// document.getElementById("answer").addEventListener("click",addBotMessage("你好"));

// 用户添加数据
function addUserMessage(message) {
    if (message.trim() === "" || message.trim() === null) {
        return
    }
    // 创建一个新消息元素
    const newMessage = document.createElement("div");
    newMessage.style.display = "flex";
    newMessage.style.justifyContent = "flex-end";
    newMessage.className = "chat-message";
    console.log(message)
    if (message.includes("imageSource")) {
        var source = "data:image/jpeg;base64,";

        fetch('http://localhost:80/Dia/Image/' + message.split(":")[1], {
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
                    newMessage.innerHTML = `
                        <div class="message-content" style="background-color: #3b3abe ;color: white"> <img src= ${source} > </div>
                        <div class="user-avatar" style="margin-left: 10px"><img src="../images/people.jpg" alt=""></div>
                    `;
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
        newMessage.innerHTML = `
            <div class="message-content" style="background-color: #3b3abe ;color: white">${message}</div>
            <div class="user-avatar" style="margin-left: 10px"><img src="../images/people.jpg" alt=""></div>
        `;
    }

    // 清空输入框
    document.querySelector(".chat-input input").value = "";

    // 将新消息添加到聊天容器
    const chatContainer = document.querySelector(".chat-container");
    chatContainer.appendChild(newMessage);
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

// 监听发送按钮
document.getElementById("send").addEventListener("click", function () {
    // console.log(image)
    if ((document.querySelector(".chat-input input").value == null
            || document.querySelector(".chat-input input").value === "")
        && (image === "" || image == null)
    ) {
        alert("请先输入内容");
        return;
    }

    console.log(image);
    const data = {
        uid: sessionStorage.getItem('userId'),
        did: sessionStorage.getItem('did'),
        image: image,
        message: document.querySelector(".chat-input input").value
    }
    console.log(data)

    if (data.did == null || data.uid == null) {
        alert("请先选择对话");
        return
    }

    if (document.querySelector(".chat-input input").value == null
        || document.querySelector(".chat-input input").value === "") {
        // 输入框无消息，默认图生图
        userSendText("进行图生图");
        data.message = "进行图生图"
    } else {
        // 获取输入框中的消息内容
        userSendText(data.message);
    }

    //设置按钮不可点击
    button.disabled = true;
    imgBnt.disabled = true;

    // 点击发送后存储历史记录
    fetch('http://localhost:80/Dia/imageGeneration', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' //指定以json格式发送数据
        },
        body: JSON.stringify(data)//将data转换为json格式
    })
        .then(res => res.json()) //以json格式解码
        .then(res => {
            console.log(res)
            //接收到数据后的处理
            if (res.code === 22001) {
                //bot回复图片消息
                botSendImage("data:image/jpeg;base64," + res.data)

                //恢复按钮可用
                button.disabled = false;
                imgBnt.disabled = false;
            } else {
                alert("系统错误,生成图片失败")
            }
        })
        .catch(error => {
            //出现异常处理
            alert(error);
            alert("发生错误，无法连接到服务器");
        });

    //清空输入框
    document.querySelector(".chat-input input").value = "";

    image = "";
});

// 图片按钮（调用隐藏的选择文件组件）
document.getElementById("add-image").addEventListener('click', function () {
    document.getElementById("image-input").click();
});
// 隐藏的选择文件组件
document.getElementById("image-input").addEventListener('change', function (event) {
    const file = event.target.files[0];
    event.target.value = '';
    if (file) {
        const reader = new FileReader();

        reader.addEventListener('load', function () {
            //读取图片内容
            const imageDataUrl = reader.result;
            //将消息添加到对话框
            userSendImage(imageDataUrl);

            //提取base64图片信息
            image = imageDataUrl.split(',')[1];

            //机器人回复
            botSendText("您已经添加了一个图片了，请继续输入文字进行图像二次编辑, 或者点击发送进行图生图 >_<");
        });

        reader.readAsDataURL(file);
    }
});