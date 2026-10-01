const open_login = document.getElementById('open_login')
const popup_container = document.getElementById('popup_container')
const close_login = document.getElementById('close_login')

open_login.addEventListener('click', () => {
    popup_container.classList.add('show');
});

close_login.addEventListener('click', () => {
    popup_container.classList.remove('show');
});