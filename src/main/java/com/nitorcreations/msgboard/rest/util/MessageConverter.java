package com.nitorcreations.msgboard.rest.util;

import java.net.MalformedURLException;
import java.net.URL;

import com.nitorcreations.msgboard.domain.Message;
import com.nitorcreations.msgboard.rest.bean.AbstractMessageBean;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV2;

public class MessageConverter {
    public static Message fromV2Bean(final MessageBeanV2 bean) throws MalformedURLException {
        Message message = new Message();
        message.setTitle(bean.title);
        message.setContent(bean.content);
        message.setSender(bean.sender);
        message.setUrl(new URL(bean.url));
        return message;
    }

    public static MessageBeanV1 toV1Bean(final Message message) {
        if (message == null) {
            return null;
        }
        MessageBeanV1 bean = new MessageBeanV1();
        setValues(message, bean);
        return bean;
    }

    public static MessageBeanV2 toV2Bean(final Message message) {
        if (message == null) {
            return null;
        }
        MessageBeanV2 bean = new MessageBeanV2();
        setValues(message, bean);
        setURL(message.getUrl(), bean);
        return bean;
    }

    private static void setValues(Message message, AbstractMessageBean bean) {
        bean.title = message.getTitle();
        bean.content = message.getContent();
        bean.sender = message.getSender();
    }

    private static void setURL(URL url, MessageBeanV2 bean) {
        String value = "";
        if (url != null) {
            value = url.toString();
        }
        bean.url = value;
    }
}
