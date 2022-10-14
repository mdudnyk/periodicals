function editTopic() {
    let input = getInputFields();
    hideAlerts();
    if (!validateInput(input)) {
        sendEditTopicRequest(input);
    }
}

async function sendEditTopicRequest(input) {
    let reqBody = new URLSearchParams();
    reqBody.append('id', topic_id.textContent);
    for (let i = 0; i < input.length; i++) {
        reqBody.append('lang', input[i].langId);
        reqBody.append('name', input[i].topicName);
    }
    try {
        let response = await fetch('controller?cmd=EDIT_TOPIC', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: reqBody,
        });
        if (response.status === 200) {
            updateValuesOfOldFields();
            success.style.display = 'block';
            success_block.style.display = 'flex';
        } else {
            alert_block.style.display = 'flex';
            if (response.status === 564) {
                dont_have_topic.style.display = 'block';
            } else {
                try_later.style.display = 'block';
            }
        }
    } catch (e) {
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
                oldName: b[i].getAttribute('topicName'),
                topicName : b[i].value,
            }
            console.log(result[i].oldName);
            console.log(result[i].topicName);
        }
    }
    return result;
}

function updateValuesOfOldFields() {
    let a = document.getElementById('new_topic_input');
    if (a !== null) {
        let b = a.getElementsByTagName('input');
        for (let i = 0; i < b.length; i++) {
            b[i].setAttribute('topicName', b[i].value);
        }
    }
}

const topic_id = document.getElementById('topic_id');
const alert_block = document.getElementById('alert_block');
const try_later = document.getElementById('try_later');
const no_values = document.getElementById('no_values');
const fill_all = document.getElementById('fill_all');
const no_changes = document.getElementById('no_changes');
const dont_have_topic = document.getElementById('dont_have_topic');
const success_block = document.getElementById('success_block');
const success = document.getElementById('success');

function hideAlerts() {
    alert_block.style.display = 'none';
    try_later.style.display = 'none';
    no_values.style.display = 'none';
    fill_all.style.display = 'none';
    no_changes.style.display = 'none';
    dont_have_topic.style.display = 'none';
    success_block.style.display = 'none';
    success.style.display = 'none';
}

function validateInput(input) {
    let isError = false;

    if (input.length === 0 || topic_id.textContent === null) {
        no_values.style.display = 'block';
        isError = true;
    } else {
        for (let a = 0; a < input.length; a++) {
            let elem = input[a];
            if (elem.topicName.length === 0) {
                fill_all.style.display = 'block';
                isError = true;
                break;
            } else if (elem.langId.length === 0) {
                try_later.style.display = 'block';
                isError = true;
                break;
            }
        }
        if (isError === false) {
            let isAtLeastOneChanged = false;
            for (let i = 0; i < input.length; i++) {
                if (input[i].oldName !== input[i].topicName) {
                    isAtLeastOneChanged = true;
                    break;
                }
            }
            if (isAtLeastOneChanged === false) {
                no_changes.style.display = 'block';
                isError = true;
            }
        }
    }
    if (isError === true) {
        alert_block.style.display = 'flex';
    }
    return isError;
}