package com.periodicals;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFabric;
import com.periodicals.dao.manager.UserDAOManager;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.BlockingStatus;
import com.periodicals.entity.enums.Role;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DAOManagerFabric dmf = DAOManagerFabric.getInstance();
        UserDAOManager userDAOManager = dmf.getUserDAOManager();
        User user = new User(1, 1, "Myroslav", "Dudnyk", "yamahar1", "mad0013@mail.ru", Role.CUSTOMER, 100, BlockingStatus.NOT_BLOCKED);
        List<User> list;

        try {
            //add user
//            userDAOManager.createUser(user);

            //get all users
            list = userDAOManager.getAllUsersList();
            list.forEach(System.out::println);

            //get user by ID
//            User user1 = userDAOManager.getUserById(5);
//            System.out.println(user1);

            //get user by email
//            User user2 = userDAOManager.getUserByEmail("mad0013@maiel.ru");
//            System.out.println(user2);

            //update user
//            user.setFirstname("Myroslav");
//            user.setId(4);
//            userDAOManager.updateUser(user);

            //delete user by id
//            userDAOManager.deleteUser(14);


        } catch (DAOException e) {
            System.out.println(e.getMessage());
        } finally {
            dmf.closeDAO();
        }
    }
}
