var openModalButton = document.getElementById("newDialog");
var closeModalButton = document.getElementById("closeModal");
var modal = document.getElementById("myModal");

closeModalButton.addEventListener("click", function() {
    modal.style.display = "none";
});