//  --- MODALS HANDLING ---
const lang_modal = document.getElementById("lang_modal");
const lang_modal_icon = document.getElementById("lang_modal_icon");
function open_lang_modal() {
    if (lang_modal.style.display === 'block') {
        lang_modal.style.display = 'none';
        lang_modal_icon.style.rotate = '0deg';
    } else {
        lang_modal_icon.style.transition = 'rotate 0.3s';
        lang_modal_icon.style.rotate = '180deg';
        lang_modal.style.display = 'block';
    }
}

const profile_modal = document.getElementById("profile_modal");
function open_profile_modal() {
    if (profile_modal.style.display === 'block') {
        profile_modal.style.display = 'none';

    } else {
        profile_modal.style.display = 'block';
    }
}

const sign_in_modal = document.getElementById("sign_in_modal");
function open_sign_in_modal() {
    if (sign_in_modal.style.display === 'flex') {
        sign_in_modal.style.display = 'none';

    } else {
        sign_in_modal.style.display = 'flex';
    }
}

const sign_up_modal = document.getElementById("sign_up_modal");
function open_sign_up_modal() {
    if (sign_up_modal.style.display === 'flex') {
        sign_up_modal.style.display = 'none';

    } else {
        sign_up_modal.style.display = 'flex';
    }
}

function close_modals() {
    sign_in_modal.style.display = 'none';
    sign_up_modal.style.display = 'none';
}

//  --- SIGN IN LOGIC ---
//fields
const sign_in_email = document.getElementById('sign_in_email');
const sign_in_password = document.getElementById('sign_in_password');
//alerts
const si_alert_block = document.getElementById('si_alert_block');
const si_wrong_email_password = document.getElementById('si_wrong_email_password');
const si_fill_all = document.getElementById('si_fill_all');
const si_email_not_valid = document.getElementById('si_email_not_valid');
const si_password_not_valid = document.getElementById('si_password_not_valid');
const si_try_later = document.getElementById('si_try_later');

function clearSignInInput() {
    sign_in_email.value = '';
    sign_in_password.value = '';
}

function clearSignInModalAlerts() {
    si_alert_block.style.display = 'none';
    si_wrong_email_password.style.display = 'none';
    si_fill_all.style.display = 'none';
    si_email_not_valid.style.display = 'none';
    si_password_not_valid.style.display = 'none';
    si_try_later.style.display = 'none';
}

async function tryToSignIn() {
    clearSignInModalAlerts();

    if(checkSignInInput()) {
        si_alert_block.style.display = 'none';
        try {
            let response = await fetch('controller?cmd=SIGN_IN', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'email=' +  sign_in_email.value + '&password=' +  sign_in_password.value,
            });
            if (response.status === 200) {
                history.go(0);
            } else {
                si_alert_block.style.display = 'flex';
                if (response.status === 460) {
                    si_wrong_email_password.style.display = 'block';
                } else {
                    si_try_later.style.display = 'block';
                }
            }
        } catch (e) {
            si_try_later.style.display = 'block';
            si_alert_block.style.display = 'flex';
        }
    } else {
        si_alert_block.style.display = 'flex';
    }
}

function checkSignInInput() {
    let isValid = true;
    //no empty fields
    if (sign_in_email.value === '' || sign_in_password.value === '') {
        si_fill_all.style.display = 'block';
        return false;
    }
    //email is valid
    if (!validateEmail(sign_in_email.value)) {
        si_email_not_valid.style.display = 'block';
        isValid = false;
    }
    //password is valid
    if (!validatePassword(sign_in_password.value)) {
        si_password_not_valid.style.display = 'block';
        isValid = false;
    }

    return isValid;
}

//  --- SIGN UP LOGIC ---
//fields
const sign_up_firstname = document.getElementById('sign_up_firstname');
const sign_up_lastname = document.getElementById('sign_up_lastname');
const sign_up_email = document.getElementById('sign_up_email');
const sign_up_password_1 = document.getElementById('sign_up_password_1');
const sign_up_password_2 = document.getElementById('sign_up_password_2');
//alerts
const su_alert_block = document.getElementById('su_alert_block');
const su_fill_all = document.getElementById('su_fill_all');
const su_firstname_not_valid = document.getElementById('su_firstname_not_valid');
const su_lastname_not_valid = document.getElementById('su_lastname_not_valid');
const su_email_not_valid = document.getElementById('su_email_not_valid');
const su_password_not_valid = document.getElementById('su_password_not_valid');
const su_pass_identical = document.getElementById('su_pass_identical');
const su_user_exists = document.getElementById('su_user_exists');
const su_try_later = document.getElementById('su_try_later');

function clearSignUpInput() {
    sign_up_firstname.value = '';
    sign_up_lastname.value = '';
    sign_up_email.value = '';
    sign_up_password_1.value = '';
    sign_up_password_2.value = '';
}

function clearSignUpModalAlerts() {
    su_alert_block.style.display = 'none';
    su_fill_all.style.display = 'none';
    su_firstname_not_valid.style.display = 'none';
    su_lastname_not_valid.style.display = 'none';
    su_email_not_valid.style.display = 'none';
    su_password_not_valid.style.display = 'none';
    su_pass_identical.style.display = 'none';
    su_user_exists.style.display = 'none';
    su_try_later.style.display = 'none';
}

async function tryToSignUp() {
    clearSignUpModalAlerts();

    if(checkSignUpInput()) {
        su_alert_block.style.display = 'none';
        try {
            let response = await fetch('controller?cmd=SIGN_UP', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'email=' +  sign_up_email.value + '&password=' +  sign_up_password_1.value +
                '&firstname=' + sign_up_firstname.value + '&lastname=' + sign_up_lastname.value,
            });
            if (response.status === 200) {
                history.go(0);
            } else {
                su_alert_block.style.display = 'flex';
                su_try_later.style.display = 'block';
            }
        } catch (e) {
            si_try_later.style.display = 'block';
            si_alert_block.style.display = 'flex';
        }
    } else {
        su_alert_block.style.display = 'flex';
    }
}

function checkSignUpInput() {
    let isValid = true;
    //no empty fields
    if (sign_up_firstname.value === '' || sign_up_lastname.value === '' ||
        sign_up_email.value === '' || sign_up_password_1.value === '' ||
        sign_up_password_2.value === '') {
        su_fill_all.style.display = 'block';
        return false;
    }
    //firstname valid
    if (!validateFirstLastName(sign_up_firstname.value)) {
        su_firstname_not_valid.style.display = 'block';
        isValid = false;
    }
    //lastname valid
    if (!validateFirstLastName(sign_up_lastname.value)) {
        su_lastname_not_valid.style.display = 'block';
        isValid = false;
    }
    //email is valid
    if (!validateEmail(sign_up_email.value)) {
        su_email_not_valid.style.display = 'block';
        isValid = false;
    }
    //password is valid
    if (!validatePassword(sign_up_password_1.value)) {
        su_password_not_valid.style.display = 'block';
        isValid = false;
    } 
    else {
        if (sign_up_password_1.value !== sign_up_password_2.value) {
            su_pass_identical.style.display = 'block';
            isValid = false;
        }
    }
    return isValid;
}

//  --- FIELD VALIDATORS ---
//email
function validateEmail(email) {
    const regex = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
    return regex.test(email);
}
//password
function validatePassword(password) {
    const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return regex.test(password);
}
//Firstname-Lastname
function validateFirstLastName(string) {
    const regex =  /^([А-Я][а-яё]{1,23}|[A-Z][a-z]{1,23})$/;
    return regex.test(string);
}