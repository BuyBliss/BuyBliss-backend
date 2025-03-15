package com.commerce.ecommerce.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "cart")  // 🔥 Prevent recursion
@ToString(exclude = "cart")  // 🔥 Prevent infinite loop
@Entity
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumerId;
    private String name;
    private String email;
    private String contactNo;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Cart cart = new Cart();

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> orders;
}
