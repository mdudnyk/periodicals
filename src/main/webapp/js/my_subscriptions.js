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



const alert_modal = document.getElementById("alert_modal");
const something_went_wrong = document.getElementById("something_went_wrong");
const cant_delete = document.getElementById("cant_delete");
const dont_have_rights = document.getElementById("dont_have_rights");

async function deleteSubscriptionById(id) {
    try {
        let response = await fetch('controller?cmd=DELETE_MY_SUBSCRIPTION', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: "&id=" + id,
        });
        let result = response.status;
        if (result === 200) {
            history.go(0);
        } else if (result === 568) {
            openAlertModal(cant_delete);
        } else if (result === 569) {
            openAlertModal(dont_have_rights);
        } else {
            openAlertModal(something_went_wrong);
        }
    } catch (e) {
        console.log(e);
        openAlertModal(something_went_wrong);
    }
}

function openAlertModal(alert_msg) {
    alert_msg.display = "block";
    alert_modal.style.display = "flex";
}

function closeAlertModal() {
    alert_modal.style.display = "none";
    something_went_wrong.display = "none";
    cant_delete.display = "none";
    dont_have_rights.display = "none";
}