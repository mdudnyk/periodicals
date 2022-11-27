package com.periodicals;

import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;

public class Main {
    public static void main(String[] args)  {
        User user1 = new User(1, "uk", "Myroslav", "Dudnyk", "yamahar1",
                "mad0013@mail.ru", UserRole.CUSTOMER, 100, false);
    }
}