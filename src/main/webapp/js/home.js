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