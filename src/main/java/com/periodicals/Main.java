package com.periodicals;


import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.TopicDAOManager;
import com.periodicals.entity.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws DAOException {
        DAOManagerFactory managerFactory = DAOManagerFactory.getInstance();
        TopicDAOManager topicDAOManager = managerFactory.getTopicDAOManager();

//        Topic topic = new Topic(1);
//        TopicTranslate tt1 = new TopicTranslate(topic.getId(), "ua","Автомобілі");
//        TopicTranslate tt2 = new TopicTranslate(topic.getId(),"en","Automotive");
//        topic.addTranslate(tt1);
//        topic.addTranslate(tt2);
//        topicDAOManager.createTopic(topic);
//
//
//        Topic topic2 = new Topic(2);
//        TopicTranslate tt12 = new TopicTranslate(topic2.getId(), "ua","Журнали");
//        TopicTranslate tt22 = new TopicTranslate(topic2.getId(),"en","Magazines");
//        topic2.addTranslate(tt12);
//        topic2.addTranslate(tt22);
//        topicDAOManager.createTopic(topic2);
//
//        Topic topic3 = new Topic(3);
//        TopicTranslate tt123 = new TopicTranslate(topic3.getId(), "ua","Газети");
//        TopicTranslate tt223 = new TopicTranslate(topic3.getId(),"en","Newspapers");
//        topic3.addTranslate(tt123);
//        topic3.addTranslate(tt223);
//        topicDAOManager.createTopic(topic3);

        List<Topic> topics = topicDAOManager.getAllTopicsByLocale("ua");
        for (Topic t : topics) {
            System.out.println(t);
        }

        topics = topicDAOManager.getAllTopics();
        for (Topic t : topics) {
            System.out.println(t);
        }
    }
}