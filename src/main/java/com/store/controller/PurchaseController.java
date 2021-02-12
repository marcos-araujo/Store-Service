package com.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.controller.dto.PurchaseDTO;
import com.store.model.Purchase;
import com.store.service.PurchaseService;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public Purchase doPurchase(@RequestBody PurchaseDTO purchaseDTO){
        return purchaseService.doPurchase(purchaseDTO);
    }

    @GetMapping("/{id}")
    public Purchase getById(@PathVariable("id") Long id) {
        return purchaseService.getById(id);
    }

}
