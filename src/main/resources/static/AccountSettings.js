document.addEventListener("DOMContentLoaded", function () {
    const openButton = document.getElementById("open_account_settings");
    const popup = document.getElementById("account_settings_popup");
    const closeButton = document.getElementById("close_account_settings");

    if (!openButton || !popup || !closeButton) {
        console.error("Account settings elements not found");
        return;
    }

    openButton.addEventListener("click", function () {
        popup.classList.add("show");
    });

    closeButton.addEventListener("click", function () {
        popup.classList.remove("show");
    });

    popup.addEventListener("click", function (event) {
        if (event.target === popup) {
            popup.classList.remove("show");
        }
    });
});