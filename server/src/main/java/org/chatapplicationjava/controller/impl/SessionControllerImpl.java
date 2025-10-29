package org.chatapplicationjava.controller.impl;

import org.chatapplicationjava.controller.inter.SessionController;
import org.chatapplicationjava.model.Session;
import org.chatapplicationjava.service.impl.SessionServiceImpl;
import org.chatapplicationjava.service.inter.SessionService;

import java.util.HashMap;
import java.util.UUID;

public class SessionControllerImpl implements SessionController {
    private final SessionService sessionService;

    public SessionControllerImpl() {
        this.sessionService = new SessionServiceImpl();
    }


    @Override
    public Session getSession(UUID uuid) {
        return this.sessionService.getSessions().get(uuid);
    }

    @Override
    public HashMap<UUID, Session> getSessions() {
        return sessionService.getSessions();
    }
}
