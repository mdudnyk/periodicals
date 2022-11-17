const amount_modal = document.getElementById("amount_modal");
const amount_modal_icon = document.getElementById("amount_modal_icon");
function open_amount_modal() {
    if (amount_modal.style.display === 'block') {
        amount_modal.style.display = 'none';
        amount_modal_icon.style.rotate = '0deg';
    } else {
        amount_modal_icon.style.transition = 'rotate 0.3s';
        amount_modal_icon.style.rotate = '180deg';
        amount_modal.style.display = 'block';
    }
}


let switch_block;

function changeStatus(id) {
    switch_block = document.getElementById('switch_' + id);
    setCustomerStatusRequest(id, switch_block.checked);
}

async function setCustomerStatusRequest(id, isActive) {
    try {
        let response = await fetch('controller?cmd=SET_CUSTOMER_STATUS', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: "&id=" + id + "&status=" + isActive,
        });
        if (response.status !== 200) {
            cancelChangeStatusOnSwitch(isActive);
        }
    } catch (e) {
        console.log(e);
        cancelChangeStatusOnSwitch(isActive);
    }
}

function cancelChangeStatusOnSwitch(isActive) {
    switch_block.checked = !isActive;
}