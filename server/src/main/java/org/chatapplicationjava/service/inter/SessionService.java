package org.chatapplicationjava.service.inter;

import org.chatapplicationjava.model.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface SessionService {

    public UUID generateIdSession();
    public UUID createSession(Session session);
    public boolean removeSession(UUID uuid);
    public HashMap<UUID,Session> getSessions();
    public ArrayList<Session> searchSession(String email);
}
