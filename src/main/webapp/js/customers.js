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
    setCustomerStatusRequest(id, !switch_block.checked);
}

async function setCustomerStatusRequest(id, isBlocked) {
    console.log(isBlocked);
    try {
        let response = await fetch('controller?cmd=SET_CUSTOMER_STATUS', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: "&id=" + id + "&status=" + isBlocked,
        });
        if (response.status !== 200) {
            await cancelChangeStatusOnSwitch(isBlocked);
        }
    } catch (e) {
        console.log(e);
        await cancelChangeStatusOnSwitch(isBlocked);
    }
}

async function cancelChangeStatusOnSwitch(isBlocked) {
    await new Promise(r => setTimeout(r, 300));
    switch_block.checked = isBlocked;
}