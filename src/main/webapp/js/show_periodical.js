function tryToSubscribe(id) {
    hideAlerts();
    const calendar_arr = getSelectedMonth();
    if (isSelected(calendar_arr)) {
        sendSubscribeRequest(id);
    } else {
        select_month.style.display = 'block';
        alert_block_warning.style.display = 'block';
    }
}

async function sendSubscribeRequest(id) {
    try {
        let response = await fetch('controller?cmd=SUBSCRIBE&id=' + id, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(getSelectedMonth()),
        });
        let result = response.status;
        if (result === 200) {
            reduceBalance();
            clearCheckBoxes();
            alert_block_success.style.display = 'block';
        } else {
            if (result === 567) {
                only_registered.style.display = 'block';
            } else if (result === 568) {
                top_up_balance.style.display = 'block';
            } else if (result === 569) {
                blocked_account.style.display = 'block';
            } else {
                try_later.style.display = 'block';
            }
            alert_block_warning.style.display = 'block';
        }
    } catch (e) {
        console.log(e);
        try_later.style.display = 'block';
        alert_block_warning.style.display = 'block';
    }
}

const alert_block_success = document.getElementById('alert_block_success');
const alert_block_warning = document.getElementById('alert_block_warning');
const try_later = document.getElementById('try_later');
const only_registered = document.getElementById('only_registered');
const blocked_account = document.getElementById('blocked_account');
const select_month = document.getElementById('select_month');
const top_up_balance = document.getElementById('top_up_balance');

function hideAlerts() {
    alert_block_success.style.display = 'none';
    alert_block_warning.style.display = 'none';
    select_month.style.display = 'none';
    try_later.style.display = 'none';
    blocked_account.style.display = 'none';
    only_registered.style.display = 'none';
    top_up_balance.style.display = 'none';
}

function clearCheckBoxes() {
    const elems = document.getElementsByClassName('month_selector');

    for (let i = 0; i < elems.length; i++) {
        const form = elems[i].querySelector('.month_form');
        for (let j = 0; j < form.elements.length; j++) {
            form.elements[j].checked = false;
        }
    }
}

function isSelected(calendar_arr) {
    for (let i = 0; i < calendar_arr.length; i++) {
        let month_arr = calendar_arr[i].month;
        for (let j = 0; j < month_arr.length; j++) {
            if (month_arr[j] === true) {
                return true;
            }
        }
    }
    return false;
}

function getSelectedMonth() {
    const elems = document.getElementsByClassName('month_selector');
    let resultArr = [];
    for (let i = 0; i < elems.length; i++) {
        const form = elems[i].querySelector('.month_form');
        let monthArr = [];
        for (let j = 0; j < form.elements.length; j++) {
            monthArr.push(form.elements[j].checked);
        }
        let releaseObj = {
            year : parseInt(form.getAttribute('year')),
            month : monthArr,
        }
        resultArr.push(releaseObj);
    }
    return resultArr;
}

const price_field = document.getElementById('price_per_one');
const price_value = parsePriceField(price_field);
const total_price_field = document.getElementById('total_price_field');
const main_balance_field = document.getElementById('main_balance_field');
let total_price_value = 0;

function reduceBalance() {
    let balance = parsePriceField(main_balance_field);
    balance -= total_price_value;
    main_balance_field.textContent = priceToHumanReadable(balance);
}

function parsePriceField(field) {
    let text_price = field.textContent;
    text_price = text_price.replace(',' , '');
    return parseInt(text_price);
}

function priceToHumanReadable(price_int) {
    if (price_int === 0) {
        return '0';
    }

    let price_string = price_int.toString();

    if (price_string.length === 1) {
        price_string = '0' + price_string;
    }

    const length = price_string.length;
    const pos = length - 2;
    let result = price_string.substring(0, pos) + ',' + price_string.substring(pos, length);

    if (result.length < 4) {
        result = '0' + result;
    }

    return result;
}

function countTotalPrice() {
    hideAlerts();
    const arr = getSelectedMonth();
    let counter = 0;

    for (let i = 0; i < arr.length; i++) {
        const month_array = arr[i].month;
        for (let j = 0; j < month_array.length; j++) {
            if (month_array[j] === true) {
                counter++;
            }
        }
    }
    total_price_value = counter * price_value;
    total_price_field.textContent = priceToHumanReadable(total_price_value);
}