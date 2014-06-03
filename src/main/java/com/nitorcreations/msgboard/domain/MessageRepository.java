package com.nitorcreations.msgboard.domain;

import java.util.Collection;

public interface MessageRepository {
    Message get(Long id);

    long save(Message message);

    Collection<Message> findAll();
}
