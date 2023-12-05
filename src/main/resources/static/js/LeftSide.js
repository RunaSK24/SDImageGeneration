// 获取要操作的元素
let current_tag=document.querySelector('.current-tag');
let handler=document.querySelector('.handler');
let left_box=document.querySelector('.left-box');

handler.addEventListener('click',function(){
    if(!this.classList.contains('close')){
        left_box.style.width=0;
        this.classList.add('close');
    }else{
        left_box.style.width=250+'px';
        this.classList.remove('close');
    }
})