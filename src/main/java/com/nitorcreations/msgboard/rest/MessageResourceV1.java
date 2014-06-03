package com.nitorcreations.msgboard.rest;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.nitorcreations.msgboard.domain.Message;
import com.nitorcreations.msgboard.domain.MessageRepository;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1List;
import com.nitorcreations.msgboard.rest.util.MessageFunctions;

@Path("v1/messages")
@Component
public class MessageResourceV1 {

    @Inject
    private MessageRepository messageRepository;

    @GET
    @Path("/list")
    @Produces(APPLICATION_JSON)
    public MessageBeanV1List listMessagesV1() {
        MessageBeanV1List listBean = new MessageBeanV1List();
        listBean.messages = convertToV1(messageRepository.findAll());
        return listBean;
    }

    private List<MessageBeanV1> convertToV1(Collection<Message> messages) {
        return newArrayList(transform(messages, MessageFunctions.toV1()));
    }
}
