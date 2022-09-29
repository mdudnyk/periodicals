package com.periodicals.controller.servlet.command;

import jakarta.servlet.http.HttpServletRequest;

public final class CommandFactory {

    private CommandFactory() {
    }

    public static FrontCommand getCommand(HttpServletRequest request) {
        String commandFromRequest = request.getParameter("cmd");
        FrontCommand frontCommand = null;

        if (commandFromRequest != null) {
            try {
                frontCommand = CommandEnum.valueOf(commandFromRequest).getCommand();
            } catch (IllegalArgumentException e) {
                frontCommand = CommandEnum.ERROR_PAGE.getCommand();
            }
        } else {
            frontCommand = CommandEnum.ERROR_PAGE.getCommand();
        }
        return frontCommand;
    }
}
