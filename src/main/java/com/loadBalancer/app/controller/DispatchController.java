package com.loadBalancer.app.controller;


import com.loadBalancer.app.dto.DispatchResponseDto;
import com.loadBalancer.app.dto.OrderRequestMapper;
import com.loadBalancer.app.dto.ResponseMessage;
import com.loadBalancer.app.dto.VehicleRequestMapper;
import com.loadBalancer.app.service.DispatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dispatch")
public class DispatchController {

    @Autowired
    private DispatchService service;

    @PostMapping("/orders")
    public ResponseEntity<ResponseMessage> addOrders(@Valid @RequestBody OrderRequestMapper request){
        String message = service.addOrders(request.getOrders());

        ResponseMessage response = new ResponseMessage();
        response.setMessage(message);
        response.setStatus("success");

        return ResponseEntity.ok(response);

    }

    @PostMapping("/vehicles")
    public ResponseEntity<ResponseMessage> addVehicles(@Valid @RequestBody VehicleRequestMapper request){
        String message = service.addVehicles(request.getVehicles());

        ResponseMessage response = new ResponseMessage();
        response.setMessage(message);
        response.setStatus("success");

        return ResponseEntity.ok(response);

    }


    @GetMapping("/plan")
    public ResponseEntity<List<DispatchResponseDto>> getDispatchPlan(){
        List<DispatchResponseDto> plan  = service.getDispatchResponse();

        return ResponseEntity.ok(plan);
    }

}
