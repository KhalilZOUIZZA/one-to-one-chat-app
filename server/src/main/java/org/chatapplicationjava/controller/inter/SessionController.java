package org.chatapplicationjava.controller.inter;

import org.chatapplicationjava.model.Session;

import java.util.HashMap;
import java.util.UUID;

public interface SessionController {
    public Session getSession(UUID uuid);
    public HashMap<UUID,Session> getSessions();
}
