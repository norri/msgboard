package com.nitorcreations.msgboard.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.URL;

@XmlRootElement(name = "message")
public class MessageBeanV2 extends AbstractMessageBean {
    @URL
    public String url;

    public MessageBeanV2() {
    }

    public MessageBeanV2(String title, String content, String sender, String url) {
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.url = url;
    }
}
