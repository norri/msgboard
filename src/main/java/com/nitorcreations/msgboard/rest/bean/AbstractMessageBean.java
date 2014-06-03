package com.nitorcreations.msgboard.rest.bean;

import javax.validation.constraints.Size;

public class AbstractMessageBean {

    @Size(max = 100)
    public String title;

    @Size(max = 1000)
    public String content;

    @Size(max = 100)
    public String sender;
}
