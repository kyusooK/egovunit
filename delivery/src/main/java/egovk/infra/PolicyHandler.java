package egovk.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import egovk.config.kafka.KafkaProcessor;
import egovk.domain.*;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Resource(name = "deliveryService")
    private DeliveryService deliveryService;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderAccepted'"
    )
    public void wheneverOrderAccepted_StartDelivery(
        @Payload OrderAccepted orderAccepted
    ) {
        OrderAccepted event = orderAccepted;
        System.out.println(
            "\n\n##### listener StartDelivery : " + orderAccepted + "\n\n"
        );

        CreateDeliveryCommand createDeliveryCommand = new CreateDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of OrderAccepted event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.createDelivery(createDeliveryCommand);
            });
        // Comments //
        //OrderAccepted이벤트가 발행될 때 Delivery의 field값과 매칭하여 DeliveryStarted이벤트를 발행한다.

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderRejected'"
    )
    public void wheneverOrderRejected_CancelDelivery(
        @Payload OrderRejected orderRejected
    ) {
        OrderRejected event = orderRejected;
        System.out.println(
            "\n\n##### listener CancelDelivery : " + orderRejected + "\n\n"
        );

        Delivery.cancelDelivery(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
