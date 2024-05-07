package egovk.domain;

import egovk.domain.*;
import egovk.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class DeliveryStarted extends AbstractEvent {

    private String deliveryId;
    private String orderId;
    private String productId;
    private String productName;
    private Integer qty;

    public DeliveryStarted(Delivery aggregate) {
        super(aggregate);
    }

    public DeliveryStarted() {
        super();
    }
}
//>>> DDD / Domain Event
