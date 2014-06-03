package com.nitorcreations.msgboard.rest.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.nitorcreations.msgboard.domain.Message;
import com.nitorcreations.msgboard.domain.MessageBuilder;
import com.nitorcreations.msgboard.rest.bean.AbstractMessageBean;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV1;
import com.nitorcreations.msgboard.rest.bean.MessageBeanV2;

public class MessageConverterTest {
    private final String title = "title";
    private final String content = "content";
    private final String sender = "sender";

    private URL url;
    private Message message;
    private final Message messageWithoutURL = new MessageBuilder()
            .title(title)
            .content(content)
            .sender(sender).build();

    @Before
    public void setUp() throws MalformedURLException {
        url = new URL("http://test.com");
        message = new MessageBuilder()
                .title(title)
                .content(content)
                .sender(sender)
                .url(url).build();
    }

    @Test
    public void fromV2Bean() throws Exception {
        Message message = MessageConverter.fromV2Bean(createMessageBeanV2());
        assertThat(message.getTitle(), is(title));
        assertThat(message.getContent(), is(content));
        assertThat(message.getSender(), is(sender));
        assertThat(message.getUrl(), is(url));
    }

    @Test
    public void toV1Bean() throws Exception {
        MessageBeanV1 messageBeanV1 = MessageConverter.toV1Bean(message);
        assertBasicValues(messageBeanV1);
    }

    @Test
    public void toV2Bean() throws Exception {
        MessageBeanV2 messageBeanV2 = MessageConverter.toV2Bean(message);
        assertBasicValues(messageBeanV2);
        assertThat(messageBeanV2.url, is(url.toString()));
    }

    @Test
    public void toV2BeanWithoutURL() throws Exception {
        MessageBeanV2 messageBeanV2 = MessageConverter.toV2Bean(messageWithoutURL);
        assertBasicValues(messageBeanV2);
        assertThat(messageBeanV2.url, is(""));
    }

    @Test
    public void toV1BeanFromNullMessage() throws Exception {
        assertThat(MessageConverter.toV1Bean(null), is(nullValue()));
    }

    @Test
    public void toV2BeanFromNullMessage() throws Exception {
        assertThat(MessageConverter.toV2Bean(null), is(nullValue()));
    }

    private MessageBeanV2 createMessageBeanV2() {
        MessageBeanV2 messageBeanV2 = new MessageBeanV2();
        messageBeanV2.title = title;
        messageBeanV2.content = content;
        messageBeanV2.sender = sender;
        messageBeanV2.url = url.toString();
        return messageBeanV2;
    }

    private void assertBasicValues(AbstractMessageBean messageBean) {
        assertThat(messageBean.title, is(title));
        assertThat(messageBean.content, is(content));
        assertThat(messageBean.sender, is(sender));
    }
}