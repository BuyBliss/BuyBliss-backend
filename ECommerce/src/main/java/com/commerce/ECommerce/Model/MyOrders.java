package com.commerce.ECommerce.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MyOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myOrdersId;
    private List<Long> listOfOrdersId;

    public Long getMyOrdersId() {
        return myOrdersId;
    }

    public void setMyOrdersId(Long myOrdersId) {
        this.myOrdersId = myOrdersId;
    }

    public List<Long> getListOfOrdersId() {
        return listOfOrdersId;
    }

    public void setListOfOrdersId(List<Long> listOfOrdersId) {
        this.listOfOrdersId = listOfOrdersId;
    }

    @Override
    public String toString() {
        return "MyOrders{" +
                "myOrdersId=" + myOrdersId +
                ", listOfOrdersId=" + listOfOrdersId +
                '}';
    }
}
