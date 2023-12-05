var chatContainer = document.querySelector(".chat-container");

// 清空聊天容器中的所有对话
function clearChat() {
    // 移除所有子元素
    while (chatContainer.firstChild) {
        chatContainer.removeChild(chatContainer.firstChild);
    }
}