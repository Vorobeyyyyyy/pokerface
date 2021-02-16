package by.bsuir.pokerface.command;

import by.bsuir.pokerface.exception.ControllerException;
import by.bsuir.pokerface.responce.AbstractResponse;

import javax.servlet.http.HttpServletRequest;

public interface GameCommand {
    default AbstractResponse perform(HttpServletRequest request) throws ControllerException {return null;}
}
