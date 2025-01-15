package com.commerce.ECommerce.Model.Entity;

import com.commerce.ECommerce.Model.Enum.OrderStatus;
import com.commerce.ECommerce.Model.Enum.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private OrderStatus status;
    private PaymentType paymentType;
    private String deliveryAddress;
    private int quantity;
    private double totalPrice;
    @Temporal(value = TemporalType.DATE)
    private LocalDate billDate;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;
    
}
