const open_login = document.getElementById('open_login')
const popup_container = document.getElementById('popup_container')
const close_login = document.getElementById('close_login')

open_login.addEventListener('click', () => {
    popup_container.classList.add('show');
});

close_login.addEventListener('click', () => {
    popup_container.classList.remove('show');
});

// ========== Water Calculator ==========
function updateResults() {
    const shower  = Number(document.getElementById('shower')?.value)  || 0;
    const toilet  = Number(document.getElementById('toilet')?.value)  || 0;
    const faucet  = Number(document.getElementById('faucet')?.value)  || 0;
    const dishes  = Number(document.getElementById('dishes')?.value)  || 0;
    const laundry = Number(document.getElementById('laundry')?.value) || 0;
    const lawn    = Number(document.getElementById('lawn')?.value)    || 0;

    const total = shower + toilet + faucet + dishes + laundry + lawn;

    // Update big total number
    const totalResult = document.querySelector('.total-number');
    if (totalResult) totalResult.textContent = total;

    // Update each item in the list
    const values = document.querySelectorAll('.breakdown-value');
    if (values.length >= 6) {
        values[0].textContent = shower  + ' L';
        values[1].textContent = toilet  + ' L';
        values[2].textContent = faucet  + ' L';
        values[3].textContent = dishes  + ' L';
        values[4].textContent = laundry + ' L';
        values[5].textContent = lawn    + ' L';
    }
}

// Subit function
const calculatorForm = document.getElementById('calculator-form');
if (calculatorForm) {
    calculatorForm.addEventListener('submit', (event) => {
        event.preventDefault();
        updateResults();
    });
}