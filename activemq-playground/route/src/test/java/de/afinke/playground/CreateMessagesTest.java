package de.afinke.playground;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import javax.jms.ConnectionFactory;

import static org.apache.camel.component.jms.JmsComponent.jmsComponentAutoAcknowledge;

public class CreateMessagesTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext camelContext = super.createCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        camelContext.addComponent("activemq", jmsComponentAutoAcknowledge(connectionFactory));
        return camelContext;
    }


    @Test
    public void createMessages() {
        for (int i = 0; i < 10; i++) {
            template.sendBody("activemq:queue:TestQueue", "Test");
        }
    }

}
