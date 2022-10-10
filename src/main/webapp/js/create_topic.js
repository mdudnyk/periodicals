function createNewTopic() {
    let input = getInputFields();
    hideAlerts();
    if (!validateInput(input)) {
        sendNewTopicRequest(input);
    }
}

async function sendNewTopicRequest(input) {
    let bodyString = '';
    for (let i = 0; i < input.length; i++) {
        bodyString += 'lang=' + input[i].langId + '&name=' + input[i].topicName;
        if (i < input.length - 1) {
            bodyString += '&';
        }
    }
    try {
        let response = await fetch('controller?cmd=CREATE_TOPIC', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: bodyString,
        });
        if (response.status === 200) {
            clearInputFields();
            success.style.display = 'block';
            success_block.style.display = 'flex';
        } else {
            alert_block.style.display = 'flex';
            if (response.status === 563) {
                have_topic.style.display = 'block';
            } else {
                try_later.style.display = 'block';
            }
        }
    } catch (e) {
        console.log(e);
        try_later.style.display = 'block';
        alert_block.style.display = 'flex';
    }
}

function getInputFields() {
    let a = document.getElementById('new_topic_input');
    let result = [];
    if (a !== null) {
        let b = a.getElementsByTagName('input');
        for (let i = 0; i < b.length; i++) {
            result[i] = {
                langId : b[i].getAttribute('lang'),
                topicName : b[i].value,
            }
        }
    }
    return result;
}

function clearInputFields() {
    let a = document.getElementById('new_topic_input');
    let b = a.getElementsByTagName('input');
    for (let i = 0; i < b.length; i++) {
        b[i].value = '';
    }
}

const alert_block = document.getElementById('alert_block');
const try_later = document.getElementById('try_later');
const no_values = document.getElementById('no_values');
const fill_all = document.getElementById('fill_all');
const have_topic = document.getElementById('have_topic');
const success_block = document.getElementById('success_block');
const success = document.getElementById('success');

function hideAlerts() {
    alert_block.style.display = 'none';
    try_later.style.display = 'none';
    no_values.style.display = 'none';
    fill_all.style.display = 'none';
    have_topic.style.display = 'none';
    success_block.style.display = 'none';
    success.style.display = 'none';
}

function validateInput(input) {
    let isError = false;
    if (input.length === 0) {
        no_values.style.display = 'block';
        isError = true;
    } else {
        for(let i = 0; i < input.length; i++) {
            let elem = input[i];
            if (elem.topicName.length === 0) {
                fill_all.style.display = 'block';
                isError = true;
            }
            if (elem.langId.length === 0) {
                try_later.style.display = 'block';
                isError = true;
            }
        }
    }
    if (isError === true) {
        alert_block.style.display = 'flex';
    }
    return isError;
}