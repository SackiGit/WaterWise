const open_login = document.getElementById('open_login')
const popup_container = document.getElementById('popup_container')
const close_login = document.getElementById('close_login')
const create_account = document.getElementById('create_account')
const login_form = document.getElementById('login_form')
const register_form = document.getElementById('register_form')
const back_to_login = document.getElementById('back_to_login')
const close_register = document.getElementById('close_register')

open_login.addEventListener('click', () => {
    popup_container.classList.add('show');
});

close_login.addEventListener('click', () => {
    popup_container.classList.remove('show');
});

create_account.addEventListener('click',()=> {
login_form.style.display = 'none';
register_form.style.display='block';
});

back_to_login.addEventListener('click',()=> {
register_form.style.display='none';
login_form.style.display = 'block';
});

close_register.addEventListener('click', () => {
    popup_container.classList.remove('show');
});