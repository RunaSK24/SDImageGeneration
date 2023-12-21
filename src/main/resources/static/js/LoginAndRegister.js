// 要操作到的元素
let login = document.getElementById('login'); //去登录按钮
let register = document.getElementById('register'); //去注册按钮
let reg = document.getElementById("reg"); //注册按钮
let log = document.getElementById("log"); //登录按钮
let form_box = document.getElementsByClassName('form-box')[0]; //注册和登录页面背景框
let register_box = document.getElementsByClassName('register-box')[0]; //注册界面
let login_box = document.getElementsByClassName('login-box')[0]; //登录界面

function GoToReg() {
    form_box.style.transform = 'translateX(80%)'; // 移动from-box
    login_box.classList.add('hidden'); // 隐藏注册
    register_box.classList.remove('hidden'); // 显示登录
}

// 去注册按钮点击事件
register.addEventListener('click', GoToReg)

function GoToLog() {
    form_box.style.transform = 'translateX(0%)';
    register_box.classList.add('hidden');
    login_box.classList.remove('hidden');
}

// 去登录按钮点击事件
login.addEventListener('click', GoToLog)

function regAction() {
    let usernameInput = document.querySelector("#log-usernameInput");
    let emailInput = document.querySelector("#emailInput");
    let passwordInput = document.querySelector("#log-passwordInput");
    let confirmPasswordInput = document.querySelector("#confirmPasswordInput");
    if (
        usernameInput.validity.valueMissing || emailInput.validity.valueMissing ||
        passwordInput.validity.valueMissing || confirmPasswordInput.validity.valueMissing
    ) {
        alert("请完整输入注册信息");
        return;
    }else if(passwordInput.value !== confirmPasswordInput.value){
        alert("两次输入密码不同，请检查输入内容");
        return;
    }
    //构造发送请求的数据
    const data = {
        userName: usernameInput.value,
        passWord: passwordInput.value
    }

    //发送请求进行账户注册
    fetch('http://localhost:80/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' //指定以json格式发送数据
        },
        body: JSON.stringify(data)
    })
        .then(res => res.json()) //以json格式解码
        .then(res => {
            //接收到数据后判断是否成功
            if (res.code === 20011) {
                alert("注册成功，请继续完成登录");//提示注册成功
                //切换页面到登录状态
                form_box.style.transform = 'translateX(0%)';
                register_box.classList.add('hidden');
                login_box.classList.remove('hidden');
            } else {
                alert(res.msg);//失败提示
            }
        })
        .catch(error => {
            //出现异常处理
            alert("发生错误，无法连接到服务器：" + error);
        });
}

reg.addEventListener('click', regAction)

function logAction() {
    let usernameInput = document.querySelector("#usernameInput");
    let passwordInput = document.querySelector("#passwordInput");

    if (usernameInput.validity.valueMissing || passwordInput.validity.valueMissing) {
        alert("用户名或密码不能为空");
        return;
    }

    const data = {
        userName: usernameInput.value,
        passWord: passwordInput.value
    }

    //发送登录请求
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
                //保存数据
                sessionStorage.setItem('userId',res.data);
                sessionStorage.setItem('uName',usernameInput.value);
                sessionStorage.setItem('uPass',passwordInput.value);

                console.log(res.data);
                window.location.href="Main.html";//跳转界面
            } else {
                alert(res.msg);
            }
        })
        .catch(error => {
            //出现异常处理
            alert(error);
            alert("发生错误，无法连接到服务器");
        });
}

log.addEventListener('click', logAction)