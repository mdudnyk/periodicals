package com.periodicals.controller.servlet.command;

import com.periodicals.controller.servlet.command.impl.get.ErrorPageCommand;
import com.periodicals.controller.servlet.command.impl.get.HomePageCommand;
import com.periodicals.controller.servlet.command.impl.get.SignOutCommand;
import com.periodicals.controller.servlet.command.impl.get.SetLocaleCommand;
import com.periodicals.controller.servlet.command.impl.post.SignInCommand;
import com.periodicals.controller.servlet.command.impl.post.SignUpCommand;

public enum CommandEnum {

    HOME_PAGE(new HomePageCommand()),
    SET_LOCALE(new SetLocaleCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    SIGN_OUT(new SignOutCommand()),
    ERROR_PAGE(new ErrorPageCommand());

    private final FrontCommand frontCommand;

    CommandEnum(FrontCommand frontCommand) {
        this.frontCommand = frontCommand;
    }

    public FrontCommand getCommand() {
        return frontCommand;
    }

}
