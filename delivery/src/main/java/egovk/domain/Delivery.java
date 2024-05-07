package egovk.domain;

import egovk.DeliveryApplication;
import egovk.domain.DeliveryCanceled;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Delivery_table")
@Data
//<<< DDD / Aggregate Root
public class Delivery {

    @Id
    private String deliveryId;

    private String orderId;

    private String productId;

    private String productName;

    private Integer qty;

    @PostPersist
    public void onPostPersist() {
        DeliveryCanceled deliveryCanceled = new DeliveryCanceled(this);
        deliveryCanceled.publishAfterCommit();
    }

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }

    //<<< Clean Arch / Port Method
    public void createDelivery(CreateDeliveryCommand createDeliveryCommand) {
        //implement business logic here:

        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void completeDelivery(
        CompleteDeliveryCommand completeDeliveryCommand
    ) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void cancelDelivery(OrderRejected orderRejected) {
        //implement business logic here:

        /** Example 1:  new item 
        Delivery delivery = new Delivery();
        repository().save(delivery);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderRejected.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
