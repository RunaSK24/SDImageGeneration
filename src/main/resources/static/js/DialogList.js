// 需要操作的元素
let items = list.querySelectorAll(".item");
var numberOfItems = items.length;

// 选中时设置选中项的样式，清除其他item的选中
function setActive() {
    items = list.querySelectorAll(".item");
    items.forEach((item) => {
        item.classList.remove('active');
    })
    this.classList.add('active');
    current_tag.innerText = this.innerText;
}

// 向对话列表中添加一个对话
function addDialog() {
    modal.style.display = "none"; //先点击后出现新对话
    numberOfItems++;

    var ul = document.getElementById("list");
    var newLi = document.createElement("li");

    newLi.className = "item";
    newLi.innerHTML = '<b  style="width: 86%;text-align: left">聊天</b>  ' +
        '<span class="close" >&times;</span>';
    newLi.addEventListener('click', setActive);

    var close = newLi.querySelector(".close");
    close.addEventListener("click", function () {
        ul.removeChild(newLi);
        numberOfItems--;
    });
    ul.insertBefore(newLi, ul.firstChild);
}
document.getElementById("closeModal").addEventListener("click", addDialog);

// 如果没有对话，则弹窗并且新建一个
if (numberOfItems === 1) {
    var closeModalButton = document.getElementById("closeModal");
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
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