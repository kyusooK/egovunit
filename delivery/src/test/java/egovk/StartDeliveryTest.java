package egovk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import egovk.config.kafka.KafkaProcessor;
import egovk.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StartDeliveryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        EventTest.class
    );

    @Autowired
    private KafkaProcessor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public DeliveryStartedRepository repository;

    @Test
    @SuppressWarnings("unchecked")
    public void test0() {
        //given:
        DeliveryStarted entity = new DeliveryStarted();

        entity.setDeliveryId("D123");
        entity.setOrderId("O123");
        entity.setProductId("P123");
        entity.setProductName("Product A");
        entity.setQty(10L);

        repository.save(entity);

        //when:

        OrderAccepted event = new OrderAccepted();

        event.setOrderId("O123");
        event.setProductId("P123");
        event.setProductName("Product A");
        event.setQty(10L);

        InventoryApplication.applicationContext = applicationContext;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String msg = objectMapper.writeValueAsString(event);

            processor
                .inboundTopic()
                .send(
                    MessageBuilder
                        .withPayload(msg)
                        .setHeader(
                            MessageHeaders.CONTENT_TYPE,
                            MimeTypeUtils.APPLICATION_JSON
                        )
                        .setHeader("type", event.getEventType())
                        .build()
                );

            //then:

            Message<String> received = (Message<String>) messageCollector
                .forChannel(processor.outboundTopic())
                .poll();

            assertNotNull("Resulted event must be published", received);

            DeliveryStarted outputEvent = objectMapper.readValue(
                received.getPayload(),
                DeliveryStarted.class
            );

            LOGGER.info("Response received: {}", received.getPayload());

            assertEquals(outputEvent.getDeliveryId(), "D123");
            assertEquals(outputEvent.getOrderId(), "O123");
            assertEquals(outputEvent.getProductId(), "P123");
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            assertTrue("exception", false);
        }
    }
}
