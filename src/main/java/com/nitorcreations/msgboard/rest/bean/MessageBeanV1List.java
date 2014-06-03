package com.nitorcreations.msgboard.rest.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import jersey.repackaged.com.google.common.collect.Lists;

@XmlRootElement(name = "messages")
public class MessageBeanV1List {

    public List<MessageBeanV1> messages = Lists.newArrayList();
}
