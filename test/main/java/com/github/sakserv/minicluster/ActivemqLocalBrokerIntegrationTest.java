package com.github.sakserv.minicluster;

import com.github.sakserv.minicluster.config.ConfigVars;
import com.github.sakserv.minicluster.config.PropertyParser;
import com.github.sakserv.minicluster.impl.ActivemqLocalBroker;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ActivemqLocalBrokerIntegrationTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(ActivemqLocalBrokerIntegrationTest.class);

    // Setup the property parser
    private static PropertyParser propertyParser;
    static {
        try {
            propertyParser = new PropertyParser(ConfigVars.DEFAULT_PROPS_FILE);
        } catch(IOException e) {
            LOG.error("Unable to load property file: " + propertyParser.getProperty(ConfigVars.DEFAULT_PROPS_FILE));
        }
    }
    
    // Setup the activemq broker before running tests
    private static ActivemqLocalBroker amq;
    @BeforeClass
    public static void setUp() throws IOException {
        amq = new ActivemqLocalBroker.Builder()
                .setHostName(propertyParser.getProperty(ConfigVars.ACTIVEMQ_HOSTNAME_KEY))
                .setPort(Integer.parseInt(propertyParser.getProperty(ConfigVars.ACTIVEMQ_PORT_KEY)))
                .setQueueName(propertyParser.getProperty(ConfigVars.ACTIVEMQ_QUEUE_NAME_KEY))
                .setStoreDir(propertyParser.getProperty(ConfigVars.ACTIVEMQ_STORE_DIR_KEY))
                .setUriPrefix(propertyParser.getProperty(ConfigVars.ACTIVEMQ_URI_PREFIX_KEY))
                .setUriPostfix(propertyParser.getProperty(ConfigVars.ACTIVEMQ_URI_POSTFIX_KEY))
                .build();
        
        amq.start();
    }


    // Stop and cleanup when tests are finished
    @AfterClass
    public static void tearDown() {
        amq.stop();
    }

    @Test
    /*
    sends lots of short messages and one long one
     */
    public void testMessageProcessing() throws JMSException {
        int n = 10000;
        String msg;
        
        LOG.info("ACTIVEMQ: Sending " + n + " messages");

        //send a lot of messages
        for (int i = 0; i < n; i++) {
            msg = "hello from active mq. " + n;
            amq.sendTextMessage(msg);
            assertEquals(msg,amq.getTextMessage());
        }

        //send a really long message
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(n).append(" ");
        }
        msg = sb.toString();
        amq.sendTextMessage(msg);
        assertEquals(msg,amq.getTextMessage());

    }

}
