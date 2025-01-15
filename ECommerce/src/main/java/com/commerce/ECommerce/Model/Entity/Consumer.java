package com.commerce.ECommerce.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumerId;
    private String name;
    private String email;
    private String contactNo;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany (mappedBy = "consumer", cascade = CascadeType.ALL)
    private List<Order> orders;
}
