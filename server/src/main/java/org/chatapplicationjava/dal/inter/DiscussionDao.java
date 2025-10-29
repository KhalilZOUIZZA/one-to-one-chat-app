package org.chatapplicationjava.dal.inter;

import org.chatapplicationjava.model.Discussion;
import org.chatapplicationjava.model.Message;

public interface DiscussionDao {
    public boolean saveDiscussion(Discussion discussion);
}
