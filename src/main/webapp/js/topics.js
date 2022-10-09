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

function tryToDeleteTopic(topicID) {
    //SEND REQUEST TO CHECK IF ANY OF PUBLICATIONS IS EXIST WITH THIS TOPIC
    console.log('TEST: Topic with ID=' + topicID + ' has been deleted.');
}