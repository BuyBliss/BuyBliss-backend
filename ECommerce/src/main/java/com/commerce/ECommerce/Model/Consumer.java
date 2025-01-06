package com.commerce.ECommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String contactNo;
    private long myOrdersId;
    private long myCartId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public long getMyOrdersId() {
        return myOrdersId;
    }

    public void setMyOrdersId(long myOrdersId) {
        this.myOrdersId = myOrdersId;
    }

    public long getMyCartId() {
        return myCartId;
    }

    public void setMyCartId(long myCartId) {
        this.myCartId = myCartId;
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", myOrdersId=" + myOrdersId +
                ", myCartId=" + myCartId +
                '}';
    }
}
