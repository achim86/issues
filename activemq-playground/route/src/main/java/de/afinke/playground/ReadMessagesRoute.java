package de.afinke.playground;

import org.apache.camel.builder.RouteBuilder;

public class ReadMessagesRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jms:queue:TestQueue").to("stream:out");
    }

}
