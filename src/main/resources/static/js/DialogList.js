
// 需要操作的元素
let items = list.querySelectorAll(".item");
var numberOfItems = items.length;
var savedUserId  = 0;
// alert("?")
//获取保存的id
// console.log(savedUserId);

// 加载之前保存的对话：
function LoadDiaHis() {
    console.log(savedUserId)
    fetch('http://localhost:80/Dia/' + savedUserId, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json' //指定以json格式发送数据
        },
    })
        .then(res => res.json()) //以json格式解码
        .then(res => {
            console.log(res)
            //接收到数据后的处理
            if (res.code === 20041) {
                var arr = res.data;
                //将每个聊天记录添加到侧边栏
                arr.forEach((obj) => {
                    console.log(obj.did)
                    addDialog(obj.did)
                });
            } else {
                // 如果没有读取到任何对话，则弹窗并且新建一个对话
                if (numberOfItems === 1) {
                    var closeModalButton = document.getElementById("closeModal");
                    var modal = document.getElementById("myModal");
                    modal.style.display = "block";
                }
            }
        })
        .catch(error => {
            //出现异常处理
            alert(error);
            alert("发生错误，无法连接到服务器");
        });
}
function judge(){
    if (sessionStorage.getItem('userId') == null ){
        return false;
    }
    const data = {
        userName: sessionStorage.getItem('uName'),
        passWord: sessionStorage.getItem('uPass')
    }
    console.log(data)
//发送请求获取所有数据
    fetch('http://localhost:80/users/validate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' //指定以json格式发送数据
        },
        body: JSON.stringify(data)
    })
        .then(res => res.json()) //以json格式解码
        .then(res => {
            //接收到数据后的处理
            if (res.code === 21001) {
            } else {
                return false;
            }
        })
        .catch(error => {
            //出现异常处理
            alert(error);
            alert("发生错误，无法连接到服务器");
        });
    return true;
}

// 页面加载完成后加载历史记录
window.addEventListener('DOMContentLoaded', (event) => {
    if(!judge()){
        window.location.href = "../html/LoginAndRegister.html"
    }
    savedUserId = sessionStorage.getItem('userId');
    console.log(savedUserId);
    LoadDiaHis();
    const chatContainer = document.querySelector(".chat-container");
    chatContainer.scrollTop = chatContainer.scrollHeight;
    // 在这里进行你对用户 ID 的后续处理
});

// 选中时设置选中项的样式，清除其他item的选中
// 获取历史记录
function ReadFile(src){
    fetch('http://localhost:80/Dia/fileRead/' + src, {
        method: 'GET',
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
}

function setActive() {
    var chatContainer = document.querySelector(".chat-container");
    chatContainer.innerHTML = "";//移除其他对话的记录

    sessionStorage.setItem('did', this.id);
    console.log(this.id)
    items = list.querySelectorAll(".item");
    items.forEach((item) => {
        item.classList.remove('active');
    })
    this.classList.add('active');
    console.log(this.id)
    //去数据库根据diaid和userid查找历史记录的路径
    fetch('http://localhost:80/Dia/'+savedUserId+"/"+this.id, {
        method: 'GET',
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
                var source = res.data.dialogueSource;
                console.log(source)
                ReadFile(source)
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
    // current_tag.innerText = this.innerText;
}

// 向对话列表中添加一个对话
function addDialog(did) {
    modal.style.display = "none"; //先点击后出现新对话
    numberOfItems++;

    var ul = document.getElementById("list");
    var newLi = document.createElement("li");

    newLi.className = "item";
    newLi.id = did
    newLi.innerHTML = '<b  style="width: 86%;text-align: left">聊天</b>  ' +
        '<span class="close" >&times;</span>';
    newLi.addEventListener('click', setActive);

    var close = newLi.querySelector(".close");
    close.addEventListener("click", function () {
        ul.removeChild(newLi);
        fetch('http://localhost:80/Dia/'+savedUserId+"/"+did, { //删除数据库中的对话
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json' //指定以json格式发送数据
            },
            // body: JSON.stringify(data)
        })
            .then(res => res.json()) //以json格式解码
            .then(res => {
                console.log(res)
                //接收到数据后的处理
                if (res.code === 20021) {
                    confirm("对话已删除")
                    var chatContainer = document.querySelector(".chat-container");
                    chatContainer.innerHTML = "";
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
        numberOfItems--;
    });
    ul.insertBefore(newLi, ul.firstChild);
}
document.getElementById("closeModal").addEventListener("click", addNewDialog);
function addNewDialog(){
    const data = {
        uid : sessionStorage.getItem('userId')
    }
    fetch('http://localhost:80/Dia', {
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
            if (res.code === 20011) {
                location.reload();
                // addDialog()
            } else {
                alert("增加失败")
            }
        })
        .catch(error => {
            //出现异常处理
            alert(error);
            alert("发生错误，无法连接到服务器");
        });

}

// 弹出提示窗
function popWindow() {
    if (numberOfItems >= 8) {
        return
    }
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
}
document.getElementById("newDialog").addEventListener("click", popWindow);