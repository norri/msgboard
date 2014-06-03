package com.nitorcreations.msgboard.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
public class MessageBeanV1 extends AbstractMessageBean {

    public MessageBeanV1() {
    }

    public MessageBeanV1(String title, String content, String sender) {
        this.title = title;
        this.content = content;
        this.sender = sender;
    }
}
