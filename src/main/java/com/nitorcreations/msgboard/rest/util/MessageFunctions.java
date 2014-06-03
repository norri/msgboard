package com.nitorcreations.msgboard.rest.util;

import com.google.common.base.Function;
import com.nitorcreations.msgboard.domain.Message;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV2;

public class MessageFunctions {

    public static Function<Message, MessageBeanV1> toV1() {
        return new Function<Message, MessageBeanV1>() {
            @Override
            public MessageBeanV1 apply(Message message) {
                return MessageConverter.toV1Bean(message);
            }
        };
    }

    public static Function<Message, MessageBeanV2> toV2() {
        return new Function<Message, MessageBeanV2>() {
            @Override
            public MessageBeanV2 apply(Message message) {
                return MessageConverter.toV2Bean(message);
            }
        };
    }
}
