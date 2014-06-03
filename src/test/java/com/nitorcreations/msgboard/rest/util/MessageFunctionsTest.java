package com.nitorcreations.msgboard.rest.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.nitorcreations.msgboard.domain.Message;
import com.nitorcreations.msgboard.domain.MessageBuilder;
import com.nitorcreations.msgboard.rest.bean.AbstractMessageBean;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV2;

public class MessageFunctionsTest {
    private final Message message = new MessageBuilder().build();

    @Test
    public void toV1() throws Exception {
        AbstractMessageBean messageBean = MessageFunctions.toV1().apply(message);
        assertTrue(messageBean instanceof MessageBeanV1);
    }

    @Test
    public void toV2() throws Exception {
        AbstractMessageBean messageBean = MessageFunctions.toV2().apply(message);
        assertTrue(messageBean instanceof MessageBeanV2);
    }
}