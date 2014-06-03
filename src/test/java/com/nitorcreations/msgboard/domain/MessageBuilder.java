package com.nitorcreations.msgboard.domain;

import java.net.URL;

public class MessageBuilder {
    private String title;
    private String content;
    private String sender;
    private URL url;

    public MessageBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MessageBuilder content(String content) {
        this.content = content;
        return this;
    }

    public MessageBuilder sender(String sender) {
        this.sender = sender;
        return this;
    }

    public MessageBuilder url(URL url) {
        this.url = url;
        return this;
    }

    public Message build() {
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setSender(sender);
        message.setUrl(url);
        return message;
    }
}
