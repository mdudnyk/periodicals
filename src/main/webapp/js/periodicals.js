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

let periodical_to_delete_id;
const delete_modal = document.getElementById("delete_modal");
const periodical_to_delete_name = document.getElementById("periodical_name");
function deletePeriodicalById(id, periodical_name) {
    periodical_to_delete_id = id;
    periodical_to_delete_name.textContent = periodical_name;
    delete_modal.style.display = "flex";
}
function closeDeleteModal() {
    delete_modal.style.display = "none";
}
async function confirmDeleteModal() {
    await fetch('controller?cmd=DELETE_PERIODICAL', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: "&id=" + periodical_to_delete_id,
    });
    history.go(0);
}