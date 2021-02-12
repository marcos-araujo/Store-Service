package com.store.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class PurchaseDTO {

    @JsonIgnore
    private Long purchaseId;

    private List<PurchaseItemDTO> items;

    private AddressDTO address;


    public List<PurchaseItemDTO> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemDTO> items) {
        this.items = items;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

}