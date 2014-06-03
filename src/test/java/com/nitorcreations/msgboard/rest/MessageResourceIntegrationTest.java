package com.nitorcreations.msgboard.rest;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.glassfish.jersey.CommonProperties.MOXY_JSON_FEATURE_DISABLE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import com.nitorcreations.msgboard.JettyStarter;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1List;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV2;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV2List;

@RunWith(MockitoJUnitRunner.class)
public class MessageResourceIntegrationTest {
    private static final String BASE_URL = "http://localhost:8080/api/";
    private static Client client;

    @BeforeClass
    public static void initJetty() throws Exception {
        new JettyStarter().start(8080, "dev");
        ClientConfig config = new ClientConfig();
        config.property(MOXY_JSON_FEATURE_DISABLE, true);
        config.register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
        config.register(JacksonJaxbXMLProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
        client = newClient(config);
    }

    @Test
    public void test() {
        assertThat(getJsonV1List("v1/messages/list").messages.size(), is(0));
        assertThat(getJsonV2List("v2/messages/list").messages.size(), is(0));

        createMessage();
        assertFindById(1);
        assertThat(getJsonV1List("v1/messages/list").messages.size(), is(1));
        assertThat(getJsonV2List("v2/messages/list").messages.size(), is(1));
    }

    @Test
    public void getsListMessagesV2XmlFormat() {
        Response response = getXmlList("v2/messages/list");
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.getMediaType().toString(), is("application/xml"));
    }

    @Test
    public void createMessageWithInvalidUrl() {
        Response response = post("v2/messages/create", new MessageBeanV2("title", "content", "sender", "invalidurl"));
        assertThat(response.getStatus(), is(BAD_REQUEST.getStatusCode()));
    }

    private void createMessage() {
        Response response = post("v2/messages/create", new MessageBeanV2("title", "content", "sender", "http://test.com"));
        assertThat(response.getStatus(), is(CREATED.getStatusCode()));
    }

    private void assertFindById(int i) {
        MessageBeanV2 findById = get("v2/messages/find/" + i);
        assertThat(findById.title, is("title"));
        assertThat(findById.content, is("content"));
        assertThat(findById.sender, is("sender"));
        assertThat(findById.url, is("http://test.com"));
    }

    private MessageBeanV2List getJsonV2List(String url) {
        return client.target(BASE_URL).path(url).request(APPLICATION_JSON).accept(APPLICATION_JSON).get(MessageBeanV2List.class);
    }

    private MessageBeanV1List getJsonV1List(String url) {
        return client.target(BASE_URL).path(url).request(APPLICATION_JSON).accept(APPLICATION_JSON).get(MessageBeanV1List.class);
    }

    private Response getXmlList(String url) {
        return client.target(BASE_URL).path(url).request(APPLICATION_XML).accept(APPLICATION_XML).get();
    }

    private MessageBeanV2 get(String url) {
        return client.target(BASE_URL).path(url).request(APPLICATION_JSON).accept(APPLICATION_JSON).get(MessageBeanV2.class);
    }

    private Response post(String url, MessageBeanV2 bean) {
        return client.target(BASE_URL).path(url).request(APPLICATION_JSON).accept(APPLICATION_JSON).post(Entity.entity(bean, APPLICATION_JSON));
    }
}
