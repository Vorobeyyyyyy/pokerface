package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl implements WebSocketService {
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void notifyPlayers(Room room, AbstractGameEvent event) {
        this.notifyPlayers(room.getId(), event);
    }

    @Override
    public void notifySinglePlayer(Room room, int chairId, AbstractGameEvent event) {
        this.notifySinglePlayer(room.getId(), chairId, event);
    }

    @Override
    public void notifyPlayers(int roomId, AbstractGameEvent event) {
        simpMessagingTemplate.convertAndSend("/room/" + roomId + "/events", event);
    }

    @Override
    public void notifySinglePlayer(int roomId, int chairId, AbstractGameEvent event) {
        simpMessagingTemplate.convertAndSendToUser(roomId + "_" + chairId, "/events", event);
    }
}
