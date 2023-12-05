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
    newMessage1.innerHTML = `
        <div class="user-avatar"><img src="../images/bot.png" alt=""></div>
        <div class="message-content" style="background-color: #f7f7f7">${message1}</div>
    `;

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
    newMessage.innerHTML = `
        <div class="message-content" style="background-color: #3b3abe ;color: white">${message}</div>
        <div class="user-avatar" style="margin-left: 10px"><img src="../images/people.jpg" alt=""></div>
    `;

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
    // 获取输入框中的消息内容
    addUserMessage(message);
});

// 图片按钮（调用隐藏的选择文件组件）
document.getElementById("add-image").addEventListener('click', function() {
    document.getElementById("image-input").click();
});
// 隐藏的选择文件组件
document.getElementById("image-input").addEventListener('change', function(event) {
    var file = event.target.files[0];
    var reader = new FileReader();

    reader.onload = function(e) {
        var image = document.getElementById('preview-image');
        image.src = e.target.result;
        image.style.display = 'block';
    };
    reader.readAsDataURL(file);

    //后续实现将图片发送到后端
});