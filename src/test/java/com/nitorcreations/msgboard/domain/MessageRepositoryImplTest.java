package com.nitorcreations.msgboard.domain;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class MessageRepositoryImplTest {
    private final MessageRepository repository = new MessageRepositoryInMemoryImpl();

    private final String title = "title";
    private final String content = "content";
    private final String sender = "sender";

    private long id;
    private URL url;
    private Message message;

    @Before
    public void setUp() throws MalformedURLException {
        url = new URL("http://test.com");
        message = new MessageBuilder()
                .title(title)
                .content(content)
                .sender(sender)
                .url(url).build();
        id = repository.save(message);
    }

    @Test
    public void getsMessageById() {
        Message message = repository.get(id);
        assertThat(message.getTitle(), is(title));
        assertThat(message.getContent(), is(content));
        assertThat(message.getSender(), is(sender));
        assertThat(message.getUrl(), is(url));
    }

    @Test
    public void findsAll() {
        Collection<Message> messages = repository.findAll();
        assertThat(messages.size(), is(1));
        assertThat(messages, hasItem(message));
    }
}