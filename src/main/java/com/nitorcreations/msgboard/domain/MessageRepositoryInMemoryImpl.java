package com.nitorcreations.msgboard.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class MessageRepositoryInMemoryImpl implements MessageRepository {
    // Persistence not implemented
    private final Map<Long, Message> messages = Collections.synchronizedMap(new HashMap<Long, Message>());
    private final AtomicLong messageId = new AtomicLong();

    @Override
    public Message get(final Long id) {
        return messages.get(id);
    }

    @Override
    public long save(final Message message) {
        Long id = messageId.incrementAndGet();
        message.setId(id);
        messages.put(id, message);
        return id;
    }

    @Override
    public Collection<Message> findAll() {
        return messages.values();
    }
}
