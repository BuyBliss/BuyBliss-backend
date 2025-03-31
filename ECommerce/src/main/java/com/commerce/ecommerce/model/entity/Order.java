package com.commerce.ecommerce.model.entity;

import com.commerce.ecommerce.model.enums.OrderStatus;
import com.commerce.ecommerce.model.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderCreated;
    @Temporal(value = TemporalType.DATE)
    private LocalDate billDate;
    private boolean isCartOrder;
    @Lob
    private byte[] receipt;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    @JsonBackReference
    private Consumer consumer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();
}
