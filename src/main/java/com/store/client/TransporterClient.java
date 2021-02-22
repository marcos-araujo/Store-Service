package com.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.store.controller.dto.InfoDeliveryDTO;
import com.store.controller.dto.VoucherDTO;

@FeignClient("shipping")
public interface TransporterClient {

    @PostMapping(path="/delivery")
    public VoucherDTO book(@RequestBody InfoDeliveryDTO infoDeliveryDTO);

}