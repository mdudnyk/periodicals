package com.periodicals.controller.servlet.command;

import com.periodicals.controller.servlet.command.impl.get.*;
import com.periodicals.controller.servlet.command.impl.post.*;

public enum CommandEnum {

    HOME_PAGE(new HomePageCommand()),
    SET_LOCALE(new SetLocaleCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    SIGN_OUT(new SignOutCommand()),
    TOPICS_PAGE(new TopicsPageCommand()),
    NEW_TOPIC_PAGE(new NewTopicPageCommand()),
    CREATE_TOPIC(new CreateTopicCommand()),
    EDIT_TOPIC_PAGE(new EditTopicPageCommand()),
    EDIT_TOPIC(new EditTopicCommand()),
    DELETE_TOPIC(new DeleteTopicCommand()),
    PERIODICALS_PAGE(new PeriodicalsPageCommand()),
    NEW_PERIODICAL_PAGE(new NewPeriodicalPageCommand()),
    EDIT_PERIODICAL_PAGE(new EditPeriodicalPageCommand()),
    CREATE_EDIT_PERIODICAL(new CreateEditPeriodicalCommand()),
    DELETE_PERIODICAL(new DeletePeriodicalCommand()),
    SHOW_PERIODICAL(new ShowPeriodicalCommand()),
    SUBSCRIBE(new SubscribeCommand()),
    TOP_UP_BALANCE(new TopUpBalanceCommand()),
    GET_RESULTS(new GetResultsCommand()),
    ERROR_PAGE(new ErrorPageCommand());

    private final FrontCommand frontCommand;

    CommandEnum(FrontCommand frontCommand) {
        this.frontCommand = frontCommand;
    }

    public FrontCommand getCommand() {
        return frontCommand;
    }

}
