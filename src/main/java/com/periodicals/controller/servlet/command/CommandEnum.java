package com.periodicals.controller.servlet.command;

import com.periodicals.controller.servlet.command.impl.get.*;
import com.periodicals.controller.servlet.command.impl.post.CreateTopicCommand;
import com.periodicals.controller.servlet.command.impl.post.SignInCommand;
import com.periodicals.controller.servlet.command.impl.post.SignUpCommand;

public enum CommandEnum {

    HOME_PAGE(new HomePageCommand()),
    SET_LOCALE(new SetLocaleCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    SIGN_OUT(new SignOutCommand()),
    TOPICS_PAGE(new TopicsPageCommand()),
    NEW_TOPIC_PAGE(new NewTopicPageCommand()),
    CREATE_TOPIC(new CreateTopicCommand()),
    ERROR_PAGE(new ErrorPageCommand());

    private final FrontCommand frontCommand;

    CommandEnum(FrontCommand frontCommand) {
        this.frontCommand = frontCommand;
    }

    public FrontCommand getCommand() {
        return frontCommand;
    }

}
