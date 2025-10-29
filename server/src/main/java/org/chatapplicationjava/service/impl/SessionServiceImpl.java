package org.chatapplicationjava.service.impl;

import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.Session;
import org.chatapplicationjava.service.inter.SessionService;
import org.chatapplicationjava.util.ServerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SessionServiceImpl implements SessionService {

    // avec utilisation du synchronized on peut generer une seul UUID en ajoutant la concurence de l'appel de la methode
    @Override
    public synchronized UUID  generateIdSession() {
        long mostSigBits = ThreadLocalRandom.current().nextLong();
        long leastSigBits = ThreadLocalRandom.current().nextLong();
        return new UUID(mostSigBits, leastSigBits);
    }

    @Override
    public UUID createSession(Session session) {
        this.getSessions().put(session.getIdSession(),session);
        return session.getIdSession();
    }

    @Override
    public boolean removeSession(UUID uuid) {
        // return true if the session exist and remove it and false if don't exist
        return this.getSessions().remove(uuid) != null;
    }

    @Override
    public HashMap<UUID, Session> getSessions() {
        HashMap<UUID,Session> sessions = ((HashMap<UUID,Session>) ServerContext.lookup("sessions") );
        return sessions;
    }

    @Override
    public ArrayList<Session> searchSession(String email) {
        ArrayList<Session> sessions = new ArrayList<>();
        for (Session session:this.getSessions().values()){
			if(session.getUser().getAccount().getEmail().equals(email)){
                sessions.add(session);
			}
		}
        return sessions;
    }
}
