package egovk.domain;

import egovk.domain.*;
import egovk.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class OrderRejected extends AbstractEvent {

    private String orderId;
    private String productId;
    private String productName;
    private Integer qty;

    public OrderRejected(Order aggregate) {
        super(aggregate);
    }

    public OrderRejected() {
        super();
    }
}
//>>> DDD / Domain Event
