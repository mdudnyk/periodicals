package com.periodicals.util;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CommandAccessChecker {
    private static final Map<String, Boolean> accessCustomerMap = new HashMap<>();
    private static final Map<String, Boolean> accessAdminMap = new HashMap<>();
    private static final Map<String, Boolean> accessGuestMap = new HashMap<>();

    static {
        accessCustomerMap.put("HomePageCommand", true);
        accessCustomerMap.put("SetLocaleCommand", true);
        accessCustomerMap.put("SignInCommand", false);
        accessCustomerMap.put("SignUpCommand", false);
        accessCustomerMap.put("SignOutCommand", true);
        accessCustomerMap.put("TopicsPageCommand", false);
        accessCustomerMap.put("NewTopicPageCommand", false);
        accessCustomerMap.put("CreateTopicCommand", false);
        accessCustomerMap.put("EditTopicPageCommand", false);
        accessCustomerMap.put("EditTopicCommand", false);
        accessCustomerMap.put("DeleteTopicCommand", false);
        accessCustomerMap.put("PeriodicalsPageCommand", false);
        accessCustomerMap.put("NewPeriodicalPageCommand", false);
        accessCustomerMap.put("EditPeriodicalPageCommand", false);
        accessCustomerMap.put("CreateEditPeriodicalCommand", false);
        accessCustomerMap.put("DeletePeriodicalCommand", false);
        accessCustomerMap.put("ShowPeriodicalCommand", true);
        accessCustomerMap.put("SubscribeCommand", true);
        accessCustomerMap.put("TopUpPageCommand", true);
        accessCustomerMap.put("TopUpBalanceCommand", true);
        accessCustomerMap.put("GetPaymentResultCommand", false);
        accessCustomerMap.put("MySubscriptionsCommand", true);
        accessCustomerMap.put("ErrorPageCommand", true);


        accessAdminMap.put("HomePageCommand", true);
        accessAdminMap.put("SetLocaleCommand", true);
        accessAdminMap.put("SignInCommand", false);
        accessAdminMap.put("SignUpCommand", false);
        accessAdminMap.put("SignOutCommand", true);
        accessAdminMap.put("TopicsPageCommand", true);
        accessAdminMap.put("NewTopicPageCommand", true);
        accessAdminMap.put("CreateTopicCommand", true);
        accessAdminMap.put("EditTopicPageCommand", true);
        accessAdminMap.put("EditTopicCommand", true);
        accessAdminMap.put("DeleteTopicCommand", true);
        accessAdminMap.put("PeriodicalsPageCommand", true);
        accessAdminMap.put("NewPeriodicalPageCommand", true);
        accessAdminMap.put("EditPeriodicalPageCommand", true);
        accessAdminMap.put("CreateEditPeriodicalCommand", true);
        accessAdminMap.put("DeletePeriodicalCommand", true);
        accessAdminMap.put("ShowPeriodicalCommand", true);
        accessAdminMap.put("SubscribeCommand", false);
        accessAdminMap.put("TopUpPageCommand", false);
        accessAdminMap.put("TopUpBalanceCommand", false);
        accessAdminMap.put("GetPaymentResultCommand", false);
        accessAdminMap.put("MySubscriptionsCommand", false);
        accessAdminMap.put("ErrorPageCommand", true);


        accessGuestMap.put("HomePageCommand", true);
        accessGuestMap.put("SetLocaleCommand", true);
        accessGuestMap.put("SignInCommand", true);
        accessGuestMap.put("SignUpCommand", true);
        accessGuestMap.put("SignOutCommand", false);
        accessGuestMap.put("TopicsPageCommand", false);
        accessGuestMap.put("NewTopicPageCommand", false);
        accessGuestMap.put("CreateTopicCommand", false);
        accessGuestMap.put("EditTopicPageCommand", false);
        accessGuestMap.put("EditTopicCommand", false);
        accessGuestMap.put("DeleteTopicCommand", false);
        accessGuestMap.put("PeriodicalsPageCommand", false);
        accessGuestMap.put("NewPeriodicalPageCommand", false);
        accessGuestMap.put("EditPeriodicalPageCommand", false);
        accessGuestMap.put("CreateEditPeriodicalCommand", false);
        accessGuestMap.put("DeletePeriodicalCommand", false);
        accessGuestMap.put("ShowPeriodicalCommand", true);
        accessGuestMap.put("SubscribeCommand", false);
        accessGuestMap.put("TopUpPageCommand", false);
        accessGuestMap.put("TopUpBalanceCommand", false);
        accessGuestMap.put("GetPaymentResultCommand", true);
        accessGuestMap.put("MySubscriptionsCommand", false);
        accessGuestMap.put("ErrorPageCommand", true);
    }

    public static boolean isAccessAllowed(FrontCommand command, HttpServletRequest request) {
        String cmdName = command.getClass().getSimpleName();
        User user = (User) request.getSession().getAttribute("user");
        UserRole role = UserRole.GUEST;
        Boolean result = null;

        if (user != null) {
            role = user.getRole();
        }

        if (role == UserRole.ADMIN) {
            result = accessAdminMap.get(cmdName);
        }
        if (role == UserRole.CUSTOMER) {
            result = accessCustomerMap.get(cmdName);
        }
        if (role == UserRole.GUEST) {
            result = accessGuestMap.get(cmdName);
        }

        if (result != null) {
            return result;
        }

        return false;
    }
}
