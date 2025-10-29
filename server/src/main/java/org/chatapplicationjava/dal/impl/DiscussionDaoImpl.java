package org.chatapplicationjava.dal.impl;

import org.chatapplicationjava.dal.DataBase;
import org.chatapplicationjava.dal.inter.DiscussionDao;
import org.chatapplicationjava.model.Discussion;

import java.text.SimpleDateFormat;

public class DiscussionDaoImpl implements DiscussionDao {
    @Override
    public boolean saveDiscussion(Discussion discussion) {
       /* String query= "INSERT INTO Discussion(participant1, participant2) VALUES('" +
                discussion.getFirstUser().getAccount().getEmail() + "', '" +
                discussion.getSecondUser().getAccount().getEmail() + "');";*/
        String query = "INSERT INTO Discussion (participant1, participant2) VALUES (" +
                "LEAST('" + discussion.getFirstUser().getAccount().getEmail() + "', '" + discussion.getSecondUser().getAccount().getEmail() + "'), " +
                "GREATEST('" + discussion.getFirstUser().getAccount().getEmail() + "', '" + discussion.getSecondUser().getAccount().getEmail() + "'));";
        return DataBase.executeActionSQL(query);
    }
}
