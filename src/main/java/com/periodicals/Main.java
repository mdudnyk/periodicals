package com.periodicals;


import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.PeriodicalDAOManager;
import com.periodicals.dao.manager.TopicDAOManager;
import com.periodicals.entity.*;
import com.periodicals.service.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.TopicServiceImpl;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws DAOException, ServiceException, IOException {
//        File imageFile = new File("C:\\Users\\mdudnyk\\Desktop\\image.jpeg");

        byte[] fileContent = FileUtils.readFileToByteArray(new File("C:\\Users\\mdudnyk\\Desktop\\periodicals_front\\img\\periodical_titles\\Optimized-111.jpg"));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        System.out.println(encodedString);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        FileUtils.writeByteArrayToFile(new File("C:\\Users\\mdudnyk\\Desktop\\image.jpeg"), decodedBytes);


//        Files.write(imageFile.toPath(), (byte[]) image);
    }
}
