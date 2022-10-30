//Title image
const file_input = document.getElementById('file');
const file_form = document.getElementById('upload');
const title_img = document.getElementById('title_img');

function activate_file_input_window() {
    file_input.click();
}

file_form.addEventListener('change', handleChange);
function handleChange() {
    if (!file_input.value.length) return;
    let reader = new FileReader();
    reader.onload = logFile;
    reader.readAsDataURL(file_input.files[0]);
}
function logFile(event) {
    title_img.src = event.target.result;
}

//Topic
const topic_modal = document.getElementById("topic_modal");
const topic_modal_icon = document.getElementById("topic_modal_icon");
const show_all_topics_text = document.getElementById("show_all_topics_text");
const select_one_topic_text = document.getElementById("select_one_topic_text");
function open_topic_modal() {
    if (topic_modal.style.display === 'block') {
        topic_modal.style.display = 'none';
        topic_modal_icon.style.rotate = '0deg';
        select_one_topic_text.style.display = 'none';
        show_all_topics_text.style.display = 'block';

    } else {
        topic_modal_icon.style.transition = 'rotate 0.3s';
        topic_modal_icon.style.rotate = '180deg';
        topic_modal.style.display = 'block';
        show_all_topics_text.style.display = 'none';
        select_one_topic_text.style.display = 'block';
    }
}
function setTopic(name, id) {
    show_all_topics_text.textContent = name;
    show_all_topics_text.setAttribute('topicId', id);
    open_topic_modal();
}


//Publication frequency
const frequency_modal = document.getElementById("frequency_modal");
const frequency_modal_icon = document.getElementById("frequency_modal_icon");
const selected_frequency_text = document.getElementById("selected_frequency_text");
function open_frequency_modal() {
    if (frequency_modal.style.display === 'block') {
        frequency_modal.style.display = 'none';
        frequency_modal_icon.style.rotate = '0deg';
    } else {
        frequency_modal_icon.style.transition = 'rotate 0.3s';
        frequency_modal_icon.style.rotate = '180deg';
        frequency_modal.style.display = 'block';
    }
}
function setFrequency(parameter, period) {
    selected_frequency_text.textContent = parameter;
    selected_frequency_text.setAttribute('period', period);
    open_frequency_modal();
}
const frequency_digit = document.getElementById("frequency_digit");
function up_frequency() {
    let digit = parseInt(frequency_digit.textContent);
    if (digit < 12) {
        frequency_digit.textContent = (++digit).toString();
    }
}
function down_frequency() {
    let digit = frequency_digit.textContent;
    if (digit > 1) {
        frequency_digit.textContent = (--digit).toString();
    }
}


//Release month
const month_selector_container = document.getElementById('month_selector_container');
const month_selector_arr = month_selector_container.getElementsByClassName('month_selector');
const remove_year_btn = document.getElementById('remove_year_btn');
const add_year_btn = document.getElementById('add_year_btn');
function addNextYear() {
    const last_selector_index = month_selector_arr.length - 1;
    const last_selector = month_selector_arr[last_selector_index];
    const year = parseInt(last_selector.querySelector('.month_selector_year').textContent);
    const new_selector = last_selector.cloneNode(true);
    new_selector.querySelector('.month_selector_year').textContent = year + 1;
    new_selector.querySelector('.month_form').setAttribute('year', year + 1);
    month_selector_container.appendChild(new_selector);
    add_year_btn.style.display = 'none';
    remove_year_btn.style.display = 'block';
}
function removeLastYear() {
    const last_selector_index = month_selector_arr.length - 1;
    month_selector_arr.item(last_selector_index).remove();
    remove_year_btn.style.display = 'none';
    add_year_btn.style.display = 'block';
}


//CREATE NEW PERIODICAL

//ALERTS
const alert_block = document.getElementById('alert_block');
const try_later = document.getElementById('try_later');
const image_to_big = document.getElementById('image_to_big');
const make_changes = document.getElementById('make_changes');
const fill_all = document.getElementById('fill_all');
const have_periodical = document.getElementById('have_periodical');
const no_topic = document.getElementById('no_topic');
const not_zero = document.getElementById('not_zero');
const empty_release = document.getElementById('empty_release');
const success_block = document.getElementById('success_block');
const success = document.getElementById('success');
//PUBLICATION FIELDS
const title_image = document.getElementById('title_img');
const title_name = document.getElementById('title_input');
const topic_id = document.getElementById('show_all_topics_text');
const frequency_number = document.getElementById('frequency_digit');
const frequency_period = document.getElementById('selected_frequency_text');
const hryvnias = document.getElementById('hryvnias');
const kopecks = document.getElementById('kopecks');
const switch_block = document.getElementById('switch_block');
const month_selectors = document.getElementsByClassName('month_selector');
const county_fields = document.getElementsByClassName('publishing_county');
const language_fields = document.getElementsByClassName('publishing_language');
const description_fields = document.getElementsByClassName('materialize-textarea');
let periodical_state_at_startup;

document.onreadystatechange = function() {
    if (document.readyState === "complete") {
        periodical_state_at_startup = new Periodical();
    }
}

class Periodical {
    constructor() {
        this.title = title_name.value;
        this.topic = parseInt(topic_id.getAttribute('topicId'));
        this.frequency = {
            amount : parseInt(frequency_number.textContent),
            period : frequency_period.getAttribute('period'),
        };
        this.price = parseInt(hryvnias.value + kopecks.value);
        this.status_value = switch_block.checked;
        this.release = getReleaseMonthObj(month_selectors);
        this.country = getInputValues(county_fields);
        this.language = getInputValues(language_fields);
        this.description = getInputValues(description_fields);
    }
}

function getInputValues(fields) {
    let countryArr = [];
    for (let i = 0; i < fields.length; i++) {
        countryArr.push({
            lang : fields[i].getAttribute('lang'),
            value : fields[i].value,
        })
    }
    return countryArr;
}

function getReleaseMonthObj(elems) {
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

function createEditPeriodical(periodical_id) {
    hideAlerts();
    let new_periodical = new Periodical();
    if (isInputValid(periodical_state_at_startup, new_periodical)) {
        tryToSendCreateRequest(new_periodical, periodical_id);
    } else {
        alert_block.style.display = 'flex';
    }
    window.scrollTo(0, 0);
}

async function tryToSendCreateRequest(periodical, id) {
    const formData = new FormData();
    const dto_object = new Blob([JSON.stringify({
        periodical,
    })], {
        type: 'application/json'
    })
    formData.append('json', dto_object);
    if (file_input.files.length > 0) {
        formData.append('image', file_input.files[0]);
    }

    let requestStr;
    if (id < 1) {
        requestStr = 'http://localhost:8080/periodicals/controller?cmd=CREATE_EDIT_PERIODICAL';
    } else {
        requestStr = 'http://localhost:8080/periodicals/controller?cmd=CREATE_EDIT_PERIODICAL&id=' + id;
    }

    try {
        let response = await fetch(requestStr, {
            method: 'POST',
            body: formData,
        });
        let result = response.status;
        if (result === 200) {
            periodical_state_at_startup = periodical;
            success.style.display = 'block';
            success_block.style.display = 'flex';
        } else {
            alert_block.style.display = 'flex';
            if (result === 565) {
                have_periodical.style.display = 'block';
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

function hideAlerts() {
    alert_block.style.display = 'none';
    try_later.style.display = 'none';
    image_to_big.style.display = 'none';
    make_changes.style.display = 'none';
    fill_all.style.display = 'none';
    have_periodical.style.display = 'none';
    no_topic.style.display = 'none';
    not_zero.style.display = 'none';
    empty_release.style.display = 'none';
    success_block.style.display = 'none';
    success.style.display = 'none';
}

function isInputValid(old_periodical, new_periodical) {
    let isValid = true;
    if (file_input.files.length > 0) {
        if (file_input.files[0].size > 307200) {
            image_to_big.style.display = 'block';
            return false;
        }
    }
    if (JSON.stringify(old_periodical) === JSON.stringify(new_periodical)) {
        if (file_input.files.length < 1) {
            make_changes.style.display = 'block';
            return false;
        }
    }
    if (hasEmptyFields(new_periodical)) {
        fill_all.style.display = 'block';
        return false;
    }
    if (new_periodical.topic === 0 || isNaN(new_periodical.topic)) {
        no_topic.style.display = 'block';
        return false;
    }
    if (new_periodical.price === 0 || isNaN(new_periodical.price)) {
        not_zero.style.display = 'block';
        return false;
    }
    if (isNotSetRelease(new_periodical.release)) {
        empty_release.style.display = 'block';
        return false;
    }
    return isValid;
}

function isNotSetRelease(releaseArr) {
    for (let i = 0; i < releaseArr.length; i++) {
        let counter = 0;
        for (let j = 0; j < releaseArr[i].month.length; j++) {
            if (releaseArr[i].month[j] === false) {
                counter++;
            }
        }
        if (counter === releaseArr[i].month.length) {
            return true;
        }
    }
    return false;
}

function hasEmptyFields(periodical) {
    if (periodical.image === '') {
        return true;
    }
    if (periodical.title === '') {
        return true;
    }
    for (let i = 0; i < periodical.country.length; i++) {
        if (periodical.country[i].value === ''
            || periodical.language[i].value === ''
            || periodical.description[i].value === '') {
            return true;
        }
    }
    return false;
}