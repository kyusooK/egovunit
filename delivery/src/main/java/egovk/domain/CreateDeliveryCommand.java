package egovk.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CreateDeliveryCommand {

    private String deliveryId;
    private Integer qty;
    private String productId;
    private String productName;
    private String orderId;
}
