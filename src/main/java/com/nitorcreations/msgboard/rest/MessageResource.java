package com.nitorcreations.msgboard.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static jersey.repackaged.com.google.common.collect.Iterables.transform;
import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.nitorcreations.msgboard.domain.Message;
import com.nitorcreations.msgboard.domain.MessageRepository;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1List;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV2;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV2List;
import com.nitorcreations.msgboard.rest.util.MessageConverter;
import com.nitorcreations.msgboard.rest.util.MessageFunctions;

@Path("messages")
@Component
public class MessageResource {

    @Inject
    private MessageRepository messageRepository;

    @POST
    @Path("/create")
    @Produces(APPLICATION_JSON)
    public Response create(@Valid final MessageBeanV2 bean) throws MalformedURLException {
        long id = messageRepository.save(MessageConverter.fromV2Bean(bean));
        return Response.created(URI.create("messages/find/" + id)).build();
    }

    @GET
    @Path("/find/{id}")
    @Produces(APPLICATION_JSON)
    public MessageBeanV2 find(@PathParam("id") final Long id) {
        return MessageConverter.toV2Bean(messageRepository.get(id));
    }

    @GET
    @Path("/list/v1")
    @Produces(APPLICATION_JSON)
    public MessageBeanV1List listMessagesV1() {
        MessageBeanV1List listBean = new MessageBeanV1List();
        listBean.messages = convertToV1(messageRepository.findAll());
        return listBean;
    }

    @GET
    @Path("/list/v2")
    @Produces(value = { APPLICATION_JSON, APPLICATION_XML })
    public MessageBeanV2List listMessagesV2() {
        MessageBeanV2List listBean = new MessageBeanV2List();
        listBean.messages = convertToV2(messageRepository.findAll());
        return listBean;
    }

    private List<MessageBeanV1> convertToV1(Collection<Message> messages) {
        return newArrayList(transform(messages, MessageFunctions.toV1()));
    }

    private List<MessageBeanV2> convertToV2(Collection<Message> messages) {
        return newArrayList(transform(messages, MessageFunctions.toV2()));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> List<T> cast(List<?> collection) {
        return (List) collection;
    }
}
