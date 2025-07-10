package com.loadBalancer.app.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
// this dto class  is used to get list of inputs (order dtos) from the user or UI

public class OrderRequestMapper {

    @Valid
    @NotEmpty
    private List<OrderDto> orders;

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }


}
